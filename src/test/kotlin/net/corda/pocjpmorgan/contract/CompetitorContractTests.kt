package net.corda.pocjpmorgan.contract

import net.corda.pocjpmorgan.common.Constants
import net.corda.pocjpmorgan.common.UtilsContractTests
import net.corda.testing.node.ledger
import org.junit.Test

class CompetitorContractTests : UtilsContractTests() {

    @Test
    fun `The only one output state must be of type CompetitorState`() {
        ledgerServices.ledger {
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, dummyState) // Wrong output type.
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this `fails with` Constants.SINGLE_METHOD_TEXT_EXCEPTION //Exception thrown by .single() function
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState) // Correct output type.
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `Transaction must include Submit command`() {
        ledgerServices.ledger {
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), DummyCommand()) // Wrong type.
                this.fails()
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit()) // Correct type.
                this.verifies()
            }
        }
    }

    @Test
    fun `Transaction must not have inputs states`() {
        ledgerServices.ledger {
            transaction {
                input(CompetitorContract.COMPETITOR_CONTRACT_ID, dummyState) // With Input and Output State
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this `fails with`(Constants.TRANSACTION_NOT_INPUTS_STATES)
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState) // Only Output State
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `Transaction must have only one output state`() {
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState) // Two outputs fails.
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.TRANSACTION_ONLY_ONE_OUTPUT_STATE
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState) // Only Output State
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `Supervisor and Assistant cannot be the same`() {
        val supervisorIsAssistantCompetitor = competitorState.copy(supervisor = jorgeLesmes.party)
        val assistantIsSupervisorCompetitor = competitorState.copy(assistant = josepAlvarez.party)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, supervisorIsAssistantCompetitor)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_NOT_SAME
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, assistantIsSupervisorCompetitor)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_NOT_SAME
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The challengeName property in the output CompetitorState must not be empty`() {
        val challengeNameEmptyInCompetitor = competitorState.copy(challengeName = Constants.EMPTY)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, challengeNameEmptyInCompetitor)
                this `fails with` Constants.CHALLENGE_NAME_MUST_NOT_BE_EMPTY
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The challengeYear property in the output CompetitorState must not be empty`() {
        val challengeYearEmptyInCompetitor = competitorState.copy(challengeYear = Constants.EMPTY)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, challengeYearEmptyInCompetitor)
                this `fails with` Constants.CHALLENGE_YEAR_MUST_NOT_BE_EMPTY
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The challengeRefCode property in the output CompetitorState must not be empty`() {
        val challengeRefCodeEmptyInCompetitor = competitorState.copy(challengeRefCode = Constants.EMPTY)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, challengeRefCodeEmptyInCompetitor)
                this `fails with` Constants.CHALLENGE_REFCODE_MUST_NOT_BE_EMPTY
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The name property in the output CompetitorState must not be empty`() {
        val nameEmployeeEmptyInCompetitor = competitorState.copy(name = Constants.EMPTY)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, nameEmployeeEmptyInCompetitor)
                this `fails with` Constants.COMPETITOR_NAME_MUST_NOT_BE_EMPTY
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The surname property in the output CompetitorState must not be empty`() {
        val surnameEmployeeEmptyInCompetitor = competitorState.copy(surname = Constants.EMPTY)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, surnameEmployeeEmptyInCompetitor)
                this `fails with` Constants.COMPETITOR_SURNAME_MUST_NOT_BE_EMPTY
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The employee property in the output CompetitorState must not be empty`() {
        val employeeNumberEmptyInCompetitor = competitorState.copy(employee = Constants.EMPTY)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, employeeNumberEmptyInCompetitor)
                this `fails with` Constants.COMPETITOR_EMPLOYEE_NUMBER_MUST_NOT_BE_EMPTY
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `The result property in the output CompetitorState must not be empty`() {
        val resultEmptyInCompetitor = competitorState.copy(result = 0.0)
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, resultEmptyInCompetitor)
                this `fails with` Constants.COMPETITOR_RESULT_MUST_BE_POSITIVE_VALUE
            }
            transaction {
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                this.verifies()
            }
        }
    }

    @Test
    fun `Supervisor and Assistant must sign submit competitor`() {
        ledgerServices.ledger {
            transaction {
                command(getPartyPublicKey(dummyIdentity), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, josepAlvarez), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(jorgeLesmes, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, josepAlvarez, josepAlvarez), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(jorgeLesmes, jorgeLesmes, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, josepAlvarez, dummyIdentity, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, dummyIdentity, jorgeLesmes, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this `fails with` Constants.SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS
            }
            /***
             * command receives a Set<PublicKey> so not allows repeated keys. It converts a List<PublicKey> to Set<PublicKey> and
             *           the transaction is valid
             */
            transaction {
                command(getPartyPublicKey(josepAlvarez, josepAlvarez, josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this.verifies()
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes, jorgeLesmes, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this.verifies()
            }
            transaction {
                command(getPartyPublicKey(josepAlvarez, jorgeLesmes), CompetitorContract.Commands.Submit())
                output(CompetitorContract.COMPETITOR_CONTRACT_ID, competitorState)
                this.verifies()
            }
        }
    }
}