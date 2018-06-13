package net.corda.pocjpmorgan.flow

import net.corda.core.contracts.TransactionVerificationException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.services.queryBy
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.pocjpmorgan.common.Constants
import net.corda.pocjpmorgan.contract.CompetitorContract
import net.corda.pocjpmorgan.state.CompetitorState
import net.corda.testing.core.singleIdentity
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkNotarySpec
import net.corda.testing.node.StartedMockNode
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CompetitorSubmitFlowTests {

    lateinit var mockNetwork: MockNetwork
    lateinit var josepAlvarez: StartedMockNode
    lateinit var jorgeLesmes: StartedMockNode
    lateinit var competitorState: CompetitorState

    @Before
    fun setup() {
        // Initialising MockNetwork
        mockNetwork = MockNetwork(listOf("net.corda.pocjpmorgan.contract", "net.corda.training"),
                                  notarySpecs = listOf(MockNetworkNotarySpec(CordaX500Name("Notary",
                                                                                           "London",
                                                                                           "GB"))))
        // Initialising StartedMockNodes
        josepAlvarez = mockNetwork.createPartyNode()
        jorgeLesmes = mockNetwork.createPartyNode()

        // For real nodes this happens automatically, but we have to manually register the flow for tests.
        listOf(josepAlvarez, jorgeLesmes).forEach { it.registerInitiatedFlow(CompetitorSubmitFlow.Acceptor::class.java) }

        // Create a CompetitorState using the StartedMockNodes for Testing purpose
        competitorState = CompetitorState("JP Morgan Corporate Challenge",
                                          "2017",
                                          "CH-00001",
                                           josepAlvarez.info.singleIdentity(),
                                           jorgeLesmes.info.singleIdentity(),
                                          "Jose",
                                          "Lirio",
                                          "122136",
                                          25.60)

        // Running the Network
        mockNetwork.runNetwork()
    }

    @After
    fun tearDown() {
        mockNetwork.stopNodes()
    }

    @Test
    fun `CompetitorSubmitFlow rejects invalid Competitor`() {
        // Check that transaction with an empty challengeName field in CompetitorState fails.
        val challengeNameEmptyInCompetitor = competitorState.copy(challengeName = Constants.EMPTY)
        var submitFlow = CompetitorSubmitFlow.Initiator(challengeNameEmptyInCompetitor)
        var future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with an empty challengeYear field in CompetitorState fails.
        val challengeYearEmptyInCompetitor = competitorState.copy(challengeYear = Constants.EMPTY)
        submitFlow = CompetitorSubmitFlow.Initiator(challengeYearEmptyInCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with an empty challengeRefCode field in CompetitorState fails.
        val challengeRefCodeEmptyInCompetitor = competitorState.copy(challengeRefCode = Constants.EMPTY)
        submitFlow = CompetitorSubmitFlow.Initiator(challengeRefCodeEmptyInCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with a same supervisor and assistant in CompetitorState fails.
        val supervisorIsAssistantCompetitor = competitorState.copy(supervisor = jorgeLesmes.info.singleIdentity())
        submitFlow = CompetitorSubmitFlow.Initiator(supervisorIsAssistantCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }
        val assistantIsSupervisorCompetitor = competitorState.copy(assistant = josepAlvarez.info.singleIdentity())
        submitFlow = CompetitorSubmitFlow.Initiator(assistantIsSupervisorCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with a zero result field in CompetitorState fails.
        val resultEmptyInCompetitor = competitorState.copy(result = 0.0)
        submitFlow = CompetitorSubmitFlow.Initiator(resultEmptyInCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with an empty name field in CompetitorState fails.
        val nameEmptyInCompetitor = competitorState.copy(name = Constants.EMPTY)
        submitFlow = CompetitorSubmitFlow.Initiator(nameEmptyInCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with an empty surname field in CompetitorState fails.
        val surnameEmptyInCompetitor = competitorState.copy(surname = Constants.EMPTY)
        submitFlow = CompetitorSubmitFlow.Initiator(surnameEmptyInCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }

        // Check that transaction with an empty employee field in CompetitorState fails.
        val employeeEmptyInCompetitor = competitorState.copy(employee = Constants.EMPTY)
        submitFlow = CompetitorSubmitFlow.Initiator(employeeEmptyInCompetitor)
        future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }
    }

    @Test
    fun `SignedTransaction returned by the CompetitorSubmitFlow is signed by the initiator`() {
        val submitFlow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        // Return the unsigned(!) SignedTransaction object from the CompetitorSubmitFlow.
        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(jorgeLesmes.info.singleIdentity().owningKey,
                                        mockNetwork.defaultNotaryNode.info.legalIdentitiesAndCerts.first().owningKey)
    }

    @Test
    fun `SignedTransaction returned by the CompetitorSubmitFlow is signed by the acceptor`() {
        val submitFlow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(josepAlvarez.info.singleIdentity().owningKey,
                                        mockNetwork.defaultNotaryNode.info.legalIdentitiesAndCerts.first().owningKey)
    }

    @Test
    fun `SignedTransaction returned by the CompetitorSubmitFlow is signed by both parties`() {
        val submitFlow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        val signedTx = future.getOrThrow()
        signedTx.verifyRequiredSignatures()
    }

    @Test
    fun `CompetitorSubmitFlow records a well formed Transaction`() {
        val submitFlow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        val signedTx = future.getOrThrow()
        // Print the transaction for debugging purposes.
        println(signedTx.tx)
        // No inputs, one output CompetitorState and a command with the right properties.
        assert(signedTx.tx.inputs.isEmpty())
        assert(signedTx.tx.outputs.single().data is CompetitorState)
        val command = signedTx.tx.commands.single()
        assert(command.value is CompetitorContract.Commands.Submit)
        assert(command.signers.toSet() == competitorState.participants.map { it.owningKey }.toSet())
    }

    @Test
    fun `CompetitorSubmitFlow records a Transaction in both parties' transaction storages`() {
        val submitFlow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(submitFlow)
        mockNetwork.runNetwork()
        val signedTx = future.getOrThrow()

        // We check the recorded transaction in both transaction storage.
        for (node in listOf(josepAlvarez, jorgeLesmes)) {
            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
        }
    }

    @Test
    fun `Recorded Transaction has no inputs and a single output, the output CompetitorState`() {
        val flow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(flow)
        mockNetwork.runNetwork()
        val signedTx = future.getOrThrow()

        // We check the recorded transaction in both vaults.
        for (node in listOf(josepAlvarez, jorgeLesmes)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id)
            val txOutputs = recordedTx!!.tx.outputs
            assert(txOutputs.size == 1)
            val recordedState = txOutputs.single().data as CompetitorState
            assertEquals(recordedState.challengeName, competitorState.challengeName)
            assertEquals(recordedState.challengeYear, competitorState.challengeYear)
            assertEquals(recordedState.challengeRefCode, competitorState.challengeRefCode)
            assertEquals(recordedState.supervisor, josepAlvarez.info.singleIdentity())
            assertEquals(recordedState.assistant, jorgeLesmes.info.singleIdentity())
            assertEquals(recordedState.name, competitorState.name)
            assertEquals(recordedState.surname, competitorState.surname)
            assertEquals(recordedState.employee, competitorState.employee)
            assertEquals(recordedState.result, competitorState.result)

        }
    }

    @Test
    fun `CompetitorSubmitFlow records the correct Competitor in both parties's vaults`() {
        val flow = CompetitorSubmitFlow.Initiator(competitorState)
        val future = josepAlvarez.startFlow(flow)
        mockNetwork.runNetwork()
        val signedTx = future.getOrThrow()
        println("Signed transaction hash: ${signedTx.id}")
        // We check the recorded transaction in both vaults.
        // Option 1.
        for (node in listOf(josepAlvarez, jorgeLesmes)) {
            node.transaction {
                val competitors = node.services.vaultService.queryBy<CompetitorState>().states
                assertEquals(1, competitors.size)
                val recordedState = competitors.single().state.data
                assertEquals(recordedState.challengeName, competitorState.challengeName)
                assertEquals(recordedState.challengeYear, competitorState.challengeYear)
                assertEquals(recordedState.challengeRefCode, competitorState.challengeRefCode)
                assertEquals(recordedState.supervisor, josepAlvarez.info.singleIdentity())
                assertEquals(recordedState.assistant, jorgeLesmes.info.singleIdentity())
                assertEquals(recordedState.name, competitorState.name)
                assertEquals(recordedState.surname, competitorState.surname)
                assertEquals(recordedState.employee, competitorState.employee)
                assertEquals(recordedState.result, competitorState.result)
            }
        }

        // Option 2.
        listOf(josepAlvarez, jorgeLesmes).map {
            it.services.validatedTransactions.getTransaction(signedTx.id)
        }.forEach {
            val txHash = (it as SignedTransaction).id
            println("$txHash == ${signedTx.id}")
            assertEquals(signedTx.id, txHash)
        }
    }
}