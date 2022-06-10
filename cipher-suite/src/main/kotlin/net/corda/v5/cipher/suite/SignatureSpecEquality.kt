@file:JvmName("SignatureSpecEquality")

package net.corda.v5.cipher.suite

import net.corda.v5.crypto.ParameterizedSignatureSpec
import net.corda.v5.crypto.SignatureSpec
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec
import java.util.function.BiPredicate

private val comparators = mapOf<Class<*>, BiPredicate<Any, Any>>(
    PSSParameterSpec::class.java to BiPredicate { left, right ->
        left as PSSParameterSpec
        right as PSSParameterSpec
        if (left.mgfParameters !is MGF1ParameterSpec || right.mgfParameters !is MGF1ParameterSpec) {
            false
        } else {
            val leftMgf = left.mgfParameters as MGF1ParameterSpec
            val rightMgf = right.mgfParameters as MGF1ParameterSpec
            left.digestAlgorithm == right.digestAlgorithm &&
                    left.mgfAlgorithm == right.mgfAlgorithm &&
                    left.saltLength == right.saltLength &&
                    left.trailerField == right.trailerField &&
                    leftMgf.digestAlgorithm == rightMgf.digestAlgorithm
        }
    }
)

@Suppress("ComplexMethod")
fun SignatureSpec?.equal(right: SignatureSpec?): Boolean =
    if (this == null) {
        right == null
    } else if(right == null) {
        false
    } else if( this === right) {
        true
    } else if (this::class.java != right::class.java ) {
        false
    } else if(signatureName != right.signatureName) {
        false
    } else {
        when(this) {
            is CustomSignatureSpec -> {
                right as CustomSignatureSpec
                customDigestName == right.customDigestName && params.paramsAreEqual(right.params)
            }
            is ParameterizedSignatureSpec -> {
                right as ParameterizedSignatureSpec
                params.paramsAreEqual(right.params)
            }
            else -> true
        }
    }

private fun Any?.paramsAreEqual(right: Any?): Boolean =
    if (this == null) {
        right == null
    } else if(right == null) {
        false
    } else if (this::class.java != right::class.java) {
        false
    } else {
        comparators[this::class.java]?.test(this, right) ?: false
    }
