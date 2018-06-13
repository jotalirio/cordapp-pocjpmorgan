package net.corda.pocjpmorgan.contract

import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction
import net.corda.pocjpmorgan.common.Constants
import net.corda.pocjpmorgan.common.Utils
import net.corda.pocjpmorgan.state.CompetitorState
import java.security.PublicKey


/**
 * All contracts must sub-class the [Contract] interface. This contract enforces rules regarding the submit of a valid
 * [CompetitorState], which in turn encapsulates an Competitor.
 *
 * The CompetitorContract handle one transaction types involving [CompetitorState]s.
 * - Submit: Submitting a new [CompetitorState] on the ledger, which is a bilateral agreement between two parties,
 *           Supervisor and Assistant
 *
 * LegalProseReference: this is just a dummy string for the time being.
 */
@LegalProseReference(uri = "<prose_contract_uri>")
class CompetitorContract : Contract {
    /** Static variable */
    companion object {
        @JvmStatic
        val COMPETITOR_CONTRACT_ID = "net.corda.pocjpmorgan.contract.CompetitorContract"
    }

    /**
     * Adding any commands required for this contract as classes within this interface is useful to encapsulate
     * our commands inside an interface, so we can use the [requireSingleCommand] function to check for a number
     * of commands which implement this interface.
     *
     * In this moment, this contract only implements one command, Submit.
     */
    interface Commands : CommandData {
        class Submit : TypeOnlyCommandData(), Commands
    }

    /**
     * The contract code for the [CompetitorContract]. The verify() function of all the states' contracts must not throw
     * an exception for a transaction to be considered valid.
     *
     * @param [tx] The Transaction of type [LedgerTransaction]
     */
    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        val setOfSigners = command.signers.toSet()
        when (command.value) {
            is Commands.Submit -> verifySubmit(tx, setOfSigners)
            else -> throw IllegalArgumentException("Unrecognised command.")
        }
    }

    /**
     * This function validates some constraints over the Transaction. These constraints make possible
     * the fact that only one Competitor will be allowed to be Submited per Transaction.
     *
     * @param [tx] The Transaction of type [LedgerTransaction]
     * @param [signers] The public keys of the identities signing the Transaction
     */
    private fun verifySubmit(tx: LedgerTransaction, signers: Set<PublicKey>) = requireThat {
        Constants.TRANSACTION_NOT_INPUTS_STATES using (tx.inputs.isEmpty())
        Constants.TRANSACTION_ONLY_ONE_OUTPUT_STATE using (tx.outputStates.size == 1)
        // val outputState = tx.outputStates.single() as CompetitorState
        val outputState = tx.outputsOfType<CompetitorState>().single()
        Constants.SUPERVISOR_AND_ASSISTANT_NOT_SAME using (outputState.supervisor != outputState.assistant)
        Constants.CHALLENGE_NAME_MUST_NOT_BE_EMPTY using (outputState.challengeName != Constants.EMPTY)
        Constants.CHALLENGE_YEAR_MUST_NOT_BE_EMPTY using (outputState.challengeYear != Constants.EMPTY)
        Constants.CHALLENGE_REFCODE_MUST_NOT_BE_EMPTY using (outputState.challengeRefCode != Constants.EMPTY)
        Constants.COMPETITOR_NAME_MUST_NOT_BE_EMPTY using (outputState.name != Constants.EMPTY)
        Constants.COMPETITOR_SURNAME_MUST_NOT_BE_EMPTY using (outputState.surname != Constants.EMPTY)
        Constants.COMPETITOR_EMPLOYEE_NUMBER_MUST_NOT_BE_EMPTY using (outputState.employee != Constants.EMPTY)
        Constants.COMPETITOR_RESULT_MUST_BE_POSITIVE_VALUE using (outputState.result != 0.0)
        Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS using (signers == Utils.getPublicKeysFromParticipants(outputState).toSet())
    }
}