package net.corda.v5.application.flows

/**
 * A wrapper around the request data for RPC started flows.
 *
 * An RPC started flow will receive an instance of this interface, which can be used to retrieve the request body.
 */
interface RPCRequestData {

    /**
     * Get the request body for this RPC started flow.
     *
     * @return The request body.
     */
    fun getRequestBody() : String
}