package net.corda.pocjpmorgan.common

import net.corda.pocjpmorgan.state.CompetitorState
import java.security.PublicKey

abstract class Utils {
    companion object {
        /**
         * This function gets the Public Keys of the Participants in the CompetitorState
         *
         * @param [competitorState] The CompetitorState to get the public keys of his Participants
         * @return List<PublicKey>
         */
        fun getPublicKeysFromParticipants(competitorState: CompetitorState): List<PublicKey> {
            return competitorState.participants.map { it.owningKey }
        }
    }
}