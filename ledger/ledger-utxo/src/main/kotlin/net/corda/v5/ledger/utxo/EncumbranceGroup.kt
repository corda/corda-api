package net.corda.v5.ledger.utxo

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

@DoNotImplement
@CordaSerializable
interface EncumbranceGroup {
    val tag: String
    val size: Int
}