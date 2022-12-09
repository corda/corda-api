package net.corda.v5.ledger.utxo.transaction.filtered

import net.corda.v5.base.exceptions.CordaRuntimeException

class FilteredDataInconsistencyException(msg: String) : CordaRuntimeException(msg)