package net.corda.v5.ledger.crypto

import net.corda.v5.application.injection.CordaFlowInjectable

interface TransactionDigestAlgorithmNamesFactory : CordaFlowInjectable {
    fun create() : TransactionDigestAlgorithmNames
}
