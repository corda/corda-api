package net.corda.v5.application.crypto

import net.corda.v5.crypto.CompositeKeyNodeAndWeight
import java.security.PublicKey

interface CompositeKeyGenerator {

    /* Return a composite key from a weighted list of keys, and an overall threshold (which can be null, in
    which case the threshold is the sum of the key weights) */
    fun create(keys: List<CompositeKeyNodeAndWeight>, threshold: Int?): PublicKey
}