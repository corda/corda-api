package net.corda.v5.cipher.suite.handlers

import net.corda.v5.cipher.suite.CryptoWorkerCipherSuite

/**
 * Registers key scheme and handlers for in-process cipher suite.
 */
interface CryptoWorkerCipherSuiteRegistrar {
    /**
     * Will be called by the platform, the implementation has to call [CryptoWorkerCipherSuite.register]
     */
    fun registerWith(suite: CryptoWorkerCipherSuite)
}