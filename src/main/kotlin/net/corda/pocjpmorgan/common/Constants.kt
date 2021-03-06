package net.corda.pocjpmorgan.common

class Constants {
    companion object {
        val EMPTY = ""
        val TRANSACTION_NOT_INPUTS_STATES = "No inputs should be consumed when submitting a Competitor."
        val TRANSACTION_ONLY_ONE_OUTPUT_STATE = "Only one output CompetitorState should be created when submitting a Competitor."
        val SUPERVISOR_AND_ASSISTANT_NOT_SAME = "The supervisor and the assistant cannot be the same entity."
        val CHALLENGE_NAME_MUST_NOT_BE_EMPTY = "There must be a challenge name inside of CompetitorState."
        val CHALLENGE_YEAR_MUST_NOT_BE_EMPTY = "There must be a challenge year inside of CompetitorState."
        val CHALLENGE_REFCODE_MUST_NOT_BE_EMPTY = "There must be a challenge reference code inside of CompetitorState."
        val COMPETITOR_NAME_MUST_NOT_BE_EMPTY = "There must be a competitor's name inside of CompetitorState."
        val COMPETITOR_SURNAME_MUST_NOT_BE_EMPTY = "There must be a competitor's surname inside of CompetitorState."
        val COMPETITOR_GENDER_MUST_NOT_BE_EMPTY = "There must be a competitor's gender inside of CompetitorState."
        val COMPETITOR_EMPLOYEE_NUMBER_MUST_POSITIVE_VALUE = "There must be a competitor's employee number inside of CompetitorState."
        val COMPETITOR_PLACE_NUMBER_MUST_BE_POSITIVE_VALUE = "There must be a place of the competitor in the challenge inside of CompetitorState."
        val COMPETITOR_GENDER_PLACE_NUMBER_MUST_BE_POSITIVE_VALUE = "There must be a gender place of the competitor in the challenge inside of CompetitorState."
        val COMPETITOR_BIB_NUMBER_MUST_BE_POSITIVE_VALUE = "There must be a bib for the competitor in the challenge inside of CompetitorState."
        val COMPETITOR_RESULT_MUST_BE_POSITIVE_VALUE = "The result field in the CompetitorState must have a positive value."
        val SUPERVISOR_AND_ASSISTANT_MUST_BE_THE_SIGNERS = "Supervisor and Assistant are the participants and must be signers."
        val POCJPMORGAN_PATH_REST_API = "pocjpmorgan"
    }
}