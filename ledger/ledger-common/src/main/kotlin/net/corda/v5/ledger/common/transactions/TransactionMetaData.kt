package net.corda.v5.ledger.common.transactions

import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement

/**
 * TODO(Design question: Maybe just a simple map to make future changes easier?, Or just adding an options map to it?)
 */

@DoNotImplement
@CordaSerializable
interface TransactionMetaData {
    val ledgerModel: String
    val ledgerVersion: String
    val CpkIdentifiers: List<CpkIdentifier>
}