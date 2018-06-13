package net.corda.pocjpmorgan.common

import net.corda.core.contracts.CommandData
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.testing.contracts.DummyState
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import net.corda.testing.node.makeTestIdentityService

/**
 * A base class to reduce the boilerplate code when writing contract tests.
 */
abstract class UtilsContractTests : UtilsTests() {

    /**
     * Create a mock [ServiceHub] that looks for app code in the given package names, uses the provided identity service
     * (you can get one from [makeTestIdentityService]) and represents the given identity.
     */
    protected val ledgerServices = MockServices(listOf("net.corda.pocjpmorgan", "net.corda.testing.contracts"),
                                                identityService = makeTestIdentityService(),
                                                initialIdentity = TestIdentity(CordaX500Name("TestIdentity",
                                                                                             Constants.EMPTY,
                                                                                             "GB")))
    /**
     * A dummy identity for testing purpose.
     */
    val dummyIdentity = TestIdentity(CordaX500Name("Dummy", Constants.EMPTY, "GB"))

    /**
     * A dummy state inheriting of ContractState intended to have no data items: it's only their presence that matters.
     */
    val dummyState = DummyState()

    /**
     * A dummy Command inheriting of CommandData intended to have no data items: it's only their presence that matters.
     */
    protected class DummyCommand : CommandData

    /**
     * This function return a List<PublicKey> with the public keys from a variable number of arguments of type TestIdentity
     */
    protected fun getPartyPublicKey(vararg testingIdentities: TestIdentity) = testingIdentities.map { it.party.owningKey }

    /**
     * A dummies CompetitorState used for empty fields checking purpose in testing cases
     */


}