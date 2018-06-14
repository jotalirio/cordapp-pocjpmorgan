package net.corda.pocjpmorgan.api

import net.corda.core.identity.CordaX500Name
import net.corda.core.internal.x500Name
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.NodeInfo
import net.corda.core.utilities.getOrThrow
import net.corda.core.utilities.loggerFor
import net.corda.pocjpmorgan.flow.CompetitorSubmitFlow
import net.corda.pocjpmorgan.flow.CompetitorSubmitFlow.Initiator
import net.corda.pocjpmorgan.state.CompetitorState
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x500.style.BCStyle
import org.slf4j.Logger
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * This API is accessible from /api/pocjpmorgan. The endpoint paths specified below are relative to it.
 */
@Path("pocjpmorgan")
class PocjpmorganApi(val rpcOps: CordaRPCOps) {
    /** Info about the Node invoques the REST API **/
    private val me = rpcOps.nodeInfo().legalIdentities.first().name
    private val myLegalName = me.x500Name

    /** Logger for PocjpmorganApi class **/
    companion object {
        private val logger: Logger = loggerFor<PocjpmorganApi>()
    }

    /** Style to display a Node when we want a String representation **/
    fun X500Name.toDisplayString() : String  = BCStyle.INSTANCE.toString(this)

    /** Helpers for filtering the network map cache. */
    private fun isNotary(nodeInfo: NodeInfo) = rpcOps.notaryIdentities().any { nodeInfo.isLegalIdentity(it) }
    private fun isMe(nodeInfo: NodeInfo) = nodeInfo.legalIdentities.first().name == me
    private fun isNetworkMap(nodeInfo : NodeInfo) = nodeInfo.legalIdentities.single().name.organisation == "Network Map Service"

    /**
     * Returns the node's name.
     */
    @GET
    @Path("me")
    @Produces(MediaType.APPLICATION_JSON)
    fun whoami() = mapOf("me" to myLegalName.toDisplayString())

    /**
     * Returns all parties registered with the [NetworkMapService]. These names can be used to look up identities
     * using the [IdentityService].
     */
    @GET
    @Path("nodes")
    @Produces(MediaType.APPLICATION_JSON)
    fun getNodes(): Map<String, List<String>> {
        return mapOf("nodes" to rpcOps.networkMapSnapshot()
                //filter out myself, notary and eventual network map started by driver
                .filter { isNotary(it).not() && isMe(it).not() && isNetworkMap(it).not() }
                .map { it.legalIdentities.first().name.x500Name.toDisplayString() })
    }

    /**
     * Displays all Competitor states that exist in the node's vault.
     * We use [rpcOps] to query the vault all unconsumed [CompetitorState]s
     */
    @GET
    @Path("competitors")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCompetitors() = rpcOps.vaultQueryBy<CompetitorState>().states

    /**
     * Initiates a flow to agree a Competitor submit between two parties, Supervisor and Assistant.
     */
    @PUT
    @Path("submit-competitor")
    fun submitCompetitor(@QueryParam(value = "challengeName") challengeName: String,
                         @QueryParam(value = "challengeYear") challengeYear: String,
                         @QueryParam(value = "party") party: String,
                         @QueryParam(value = "name") name: String,
                         @QueryParam(value = "surname") surname: String,
                         @QueryParam(value = "gender") gender: String,
                         @QueryParam(value = "employee") employee: Int,
                         @QueryParam(value = "place") place: Int,
                         @QueryParam(value = "genderPlace") genderPlace: Int,
                         @QueryParam(value = "bib") bib: Int,
                         @QueryParam(value = "result") result: Double): Response {

        // Previous validations
//        if (party == null or party == Constants.EMPTY) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'Assistant' missing or has wrong format.\n").build()
//        }
//        if (employee < 0 ) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'Employee' must be non-negative or zero.\n").build()
//        }
//        if (place < 0 ) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'Place' must be non-negative or zero.\n").build()
//        }
//        if (genderPlace < 0 ) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'Gender Place' must be non-negative or zero.\n").build()
//        }
//        if (bib < 0 ) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'Bib' must be non-negative or zero.\n").build()
//        }
//        if (result < 0.0 ) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Query parameter 'Time' must be non-negative or zero.\n").build()
//        }

        // 1. Get party objects: Supervisor (the Initiator of a Transaction) and Assistant (the counterparty in a Transaction)
        val supervisor = rpcOps.nodeInfo().legalIdentities.first()
        val assistant = rpcOps.wellKnownPartyFromX500Name(CordaX500Name.parse(party))
                        ?: throw IllegalArgumentException("Couldn't lookup node identity for $party.")

        // 2. Create a CompetitorState object.
        val challengeRefCode = "$challengeName-$challengeYear"
        val competitorState = CompetitorState(challengeName,
                                              challengeYear,
                                              challengeRefCode,
                                              supervisor,
                                              assistant,
                                              name,
                                              surname,
                                              gender,
                                              employee,
                                              place,
                                              genderPlace,
                                              bib,
                                              result)

        // 3. Start the CompetitorSubmitFlow. We block and wait for the flow to return.
        val (status, message) = try {
            val flowHandle = rpcOps.startFlowDynamic(CompetitorSubmitFlow.Initiator::class.java, competitorState)
            val result = flowHandle.use { it.returnValue.getOrThrow() }
            Response.Status.CREATED to "Transaction id ${result.id} committed to ledger.\n${result.tx.outputs.single().data}"
        }
        catch (ex: Exception) {
            Response.Status.BAD_REQUEST to ex.message
        }

        // 4. Return the result.
        return Response.status(status).entity(message).build()
    }
 }