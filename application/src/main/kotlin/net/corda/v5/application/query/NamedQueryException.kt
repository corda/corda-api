package net.corda.v5.application.query

import net.corda.v5.base.exceptions.CordaRuntimeException

class NamedQueryException(message: String, cause: Throwable?) : CordaRuntimeException(message, cause) {
    constructor(msg: String) : this(msg, null)
}