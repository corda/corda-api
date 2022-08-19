package net.corda.v5.ledger.utxo

/**
 * Specifies UTXO transaction component groups.
 *
 * @property TRANSACTION_PARAMETERS The transaction parameters component group. Ordinal = 0.
 * @property MEMBERSHIP_PARAMETERS The membership parameters component group. Ordinal = 1.
 * @property PACKAGES The packages component group. Ordinal = 2.
 * @property SIGNATORIES The signatories component group. Ordinal = 3.
 * @property ATTACHMENTS The attachments component group. Ordinal = 4.
 * @property NOTARY The notary component group. Ordinal = 5.
 * @property TIME_WINDOW The time window component group. Ordinal = 6.
 * @property COMMANDS The commands component group. Ordinal = 7.
 * @property COMMANDS_METADATA The commands metadata component group. Ordinal = 8.
 * @property INPUTS The inputs component group. Ordinal = 9.
 * @property INPUTS_METADATA The inputs metadata component group. Ordinal = 10.
 * @property OUTPUTS The outputs component group. Ordinal = 11.
 * @property OUTPUTS_METADATA The outputs metadata component group. Ordinal = 12.
 * @property REFERENCES The references component group. Ordinal = 13.
 * @property REFERENCES_METADATA The references metadata component group. Ordinal = 14.
 */
enum class ComponentGroup {
    TRANSACTION_PARAMETERS,
    MEMBERSHIP_PARAMETERS,
    PACKAGES,
    SIGNATORIES,
    ATTACHMENTS,
    NOTARY,
    TIME_WINDOW,
    COMMANDS,
    COMMANDS_METADATA,
    INPUTS,
    INPUTS_METADATA,
    OUTPUTS,
    OUTPUTS_METADATA,
    REFERENCES,
    REFERENCES_METADATA
}
