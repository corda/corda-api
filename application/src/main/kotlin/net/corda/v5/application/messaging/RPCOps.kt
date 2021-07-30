package net.corda.v5.application.messaging

import net.corda.v5.base.annotations.DoNotImplement

/**
 * Base interface that all RPC servers must implement. Note: in Corda there's only one RPC interface. This base
 * interface is here in case we split the RPC system out into a separate library one day.
 */
@DoNotImplement
interface RPCOps {
    /** Returns the RPC protocol version. Exists since version 0 so guaranteed to be present. */
    val protocolVersion: Int
}