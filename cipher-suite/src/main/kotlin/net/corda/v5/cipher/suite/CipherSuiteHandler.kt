package net.corda.v5.cipher.suite

/**
 * Base interface for the handlers.
 */
interface CipherSuiteHandler {
    /**
     * The implementation rank, when registering the handler if there is already a handler with the equal or higher rank
     * then this handler will not be registered.
     */
    val rank: Int
}