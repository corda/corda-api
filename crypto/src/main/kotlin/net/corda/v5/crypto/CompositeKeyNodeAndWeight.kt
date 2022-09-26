package net.corda.v5.crypto

import java.security.PublicKey

data class CompositeKeyNodeAndWeight(val node: PublicKey, val weight: Int = 1) {
    init {
        // We don't allow zero or negative weights. Minimum weight = 1.
        require(weight > 0) { "A non-positive weight was detected. Member info: $this" }
    }
}
