package net.corda.pocjpmorgan.common

import net.corda.core.identity.CordaX500Name
import net.corda.pocjpmorgan.state.CompetitorState
import net.corda.testing.core.TestIdentity

abstract class UtilsTests {

    /** Create identities with the given name and a fresh keyPair. */
    protected val josepAlvarez = TestIdentity(CordaX500Name(organisation = "EverisUK-Josep-Office", locality = "London", country = "GB"))
    protected val jorgeLesmes = TestIdentity(CordaX500Name(organisation = "EverisUK-Jorge-Office", locality = "London", country = "GB"))
    protected val joseLirio = TestIdentity(CordaX500Name(organisation = "EverisUK-Staff", locality = "London", country = "GB"))

    /**
     * A dummy CompetitorState used for Testing purpose.
     */
    protected val competitorState = CompetitorState("JP Morgan Corporate Challenge",
                                                    "2017",
                                                    "CH-00001",
                                                     josepAlvarez.party,
                                                     jorgeLesmes.party,
                                                    "Jose",
                                                    "Lirio",
                                                    "M",
                                                    122136,
                                                    239,
                                                    235,
                                                    20313,
                                                    25.60)

}