package net.corda.pocjpmorgan.flow

import net.corda.core.contracts.requireThat
import net.corda.core.flows.FlowException
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.SignTransactionFlow
import net.corda.core.transactions.SignedTransaction
import net.corda.pocjpmorgan.common.Constants
import net.corda.pocjpmorgan.state.CompetitorState

/**
 * An abstract FlowLogic class that is subclassed by the Competitor flows to provide helper methods and classes.
 */
abstract class CompetitorBaseFlow : FlowLogic<SignedTransaction>() {

    /**
     * Getting a reference to the notary service we want to use on our network and our key pair.
     *
     * Note: For multiple Notary identities:
     * - val notary = serviceHub.networkMapCache.notaryIdentities[nNotary]
     *
     * Note: For only one Notary service:
     * - val notary = serviceHub.networkMapCache.notaryIdentities[0]
     * - val notary = serviceHub.networkMapCache.notaryIdentities.first()
     * - val notary = serviceHub.networkMapCache.notaryIdentities.firstOrNull()
     */
    val firstNotary
        get() = serviceHub.networkMapCache.notaryIdentities.firstOrNull() ?: throw FlowException("No available notary.")
}

/**
 * A class inheriting SignTransactionFlow(FlowSession flowSession) which checks the transaction structure validating
 * the transaction involves an CompetitorState - this ensures that CompetitorContract will be run to verify the transaction
 */
internal class SignTransactionBasicCheckedFlow(flowSession: FlowSession) : SignTransactionFlow(flowSession) {
    override fun checkTransaction(signedTx: SignedTransaction) = requireThat {
        val output = signedTx.tx.outputs.single().data
        "This must be a Competitor submit transaction." using (output is CompetitorState)
    }
}


