package net.corda.v5.cipher.suite.handlers

import net.corda.v5.cipher.suite.CipherSuite

/**
 * Registers key scheme and handlers for in-process cipher suite.
 */
interface CipherSuiteRegistrar {
    /**
     * Will be called by the platform, the implementation has to call [CipherSuite.register], in response it will have
     * to invoke the [CipherSuite.register] methods to register handlers which it provides.
     */
    fun registerWith(suite: CipherSuite)
}

