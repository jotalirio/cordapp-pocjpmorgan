package net.corda.pocjpmorgan.flow

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.contracts.Command
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import net.corda.pocjpmorgan.common.Utils
import net.corda.pocjpmorgan.contract.CompetitorContract
import net.corda.pocjpmorgan.flow.CompetitorSubmitFlow.Acceptor
import net.corda.pocjpmorgan.flow.CompetitorSubmitFlow.Initiator
import net.corda.pocjpmorgan.state.CompetitorState

/**
 * This flow allows two parties (the [Initiator] and the [Acceptor]) to come to an agreement about the Competitor encapsulated
 * within an [CompetitorState].
 *
 * The [Acceptor] always accepts a valid Competitor.
 *
 * This flow has been implemented by using only the call() method but in practice is recommend splitting up
 * the various stages of the flow into sub-routines annotated with the @Suspendable annotation. All methods called within
 * the [FlowLogic] sub-class need to be annotated with the @Suspendable annotation.
 *
 * e.g: Sub-routine 'private fun createAnonymousCompetitorState()' example commented down below
 */
object CompetitorSubmitFlow {
    @InitiatingFlow
    @StartableByRPC
    class Initiator(private val competitorState: CompetitorState) : CompetitorBaseFlow() {
        /**
         * The progress tracker checkpoints each stage of the flow and outputs the specified messages when each
         * checkpoint is reached in the code. See the 'progressTracker.currentStep' expressions within the call() function.
         */
        companion object {
            /**
             * Transaction's Steps
             */
            object INITIALISING : ProgressTracker.Step("Performing initial steps.")
            object BUILDING : ProgressTracker.Step("Generating a transaction based on new Competitor.")
            object VERIFYING : ProgressTracker.Step("Verifying contract constraints.")
            object SIGNING : ProgressTracker.Step("Signing transaction with our private key.")
            object COLLECTING : ProgressTracker.Step("Collecting the counterparty's signature.") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }
            object FINALISING : ProgressTracker.Step("Finalising transaction. Obtaining notary signature and recording transaction.") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }
            fun tracker() = ProgressTracker(INITIALISING, BUILDING, VERIFYING, SIGNING, COLLECTING, FINALISING)
        }
        /**
         * Providing the custom ProgressTracker to track the transaction
         */
        override val progressTracker = tracker()
        /**
         * The flow logic is encapsulated within the call() method.
         */
        @Suspendable
        override fun call(): SignedTransaction {
            // Step 1. Initialising
            progressTracker.currentStep = INITIALISING
            val notary = firstNotary

            // Step 2. Building the Transaction:
            progressTracker.currentStep = BUILDING
            // Creating a new Submit command.
            val submitCommand = Command(CompetitorContract.Commands.Submit(), Utils.getPublicKeysFromParticipants(competitorState))
            // Creating a new TransactionBuilder object: It generates an unsigned transaction.
            // And adding the CompetitorState as an output state, as well as the Submit command to the transaction builder.
            val txBuilder = TransactionBuilder(notary)
                            .addOutputState(competitorState, CompetitorContract.COMPETITOR_CONTRACT_ID)
                            .addCommand(submitCommand)

            // Step 3. Verifying contract constraints.
            progressTracker.currentStep = VERIFYING
            txBuilder.verify(serviceHub)

            // Step 4. Signing the transaction with our private keys.
            progressTracker.currentStep = SIGNING
            val partialSignedTx = serviceHub.signInitialTransaction(txBuilder)

            // Step 5. Get the counter-party signature. Sending the state to the counterparty, and receiving it back
            // with their signature.
            progressTracker.currentStep = COLLECTING
            // Creating a session with the other party, in this case the Assistant Party
            val otherParty = (competitorState.participants - ourIdentity).first() as Party
//            val otherParty = competitorState.assistant
            val flowSession = initiateFlow(otherParty)
            val fullySignedTx = subFlow(CollectSignaturesFlow(partialSignedTx, setOf(flowSession), COLLECTING.childProgressTracker()))

            // Step 6. Finalise the transaction. Notarise and record the transaction in both parties' vaults.
            progressTracker.currentStep = FINALISING
            return subFlow(FinalityFlow(fullySignedTx, FINALISING.childProgressTracker()))
        }

//        @Suspendable
//        private fun createAnonymousCompetitorState(): CompetitorState {
//            Some code here
//            return CompetitorState(....)
//        }
    }

    /**
     * This is the flow which signs Competitors submits.
     * The signing is handled by the [SignTransactionFlow].
     *
     * On the Manager’s side, we using CollectSignaturesFlow to automate the collection of signatures.
     * To allow the Assistant to respond, we need to write a response flow as well
     */
    @InitiatedBy(Initiator::class)
    class Acceptor(private val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signedTransactionBasicCheckedFlow = SignTransactionBasicCheckedFlow(flowSession)
            val signedTransactionFlow = subFlow(signedTransactionBasicCheckedFlow)
            return waitForLedgerCommit(signedTransactionFlow.id)
        }
    }
}

