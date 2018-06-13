package net.corda.pocjpmorgan.plugin

import net.corda.core.messaging.CordaRPCOps
import net.corda.pocjpmorgan.api.PocjpmorganApi
import net.corda.webserver.services.WebServerPluginRegistry
import java.util.function.Function

class PocjpmorganPlugin : WebServerPluginRegistry {
    /**
     * A list of classes that expose web APIs.
     */
    override val webApis: List<Function<CordaRPCOps, out Any>> = listOf(Function(::PocjpmorganApi))

    /**
     * A list of directories in the resources directory that will be served by Jetty under /web.
     */
    override val staticServeDirs: Map<String, String> = mapOf(
            // This will serve the pocjpmorganWeb directory in resources to /web/pocjpmorgan
            "pocjpmorgan" to javaClass.classLoader.getResource("pocjpmorganWeb").toExternalForm())

}