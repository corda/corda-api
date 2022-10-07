package net.corda.v5.cipher.suite

import net.corda.v5.crypto.ParameterizedSignatureSpec
import net.corda.v5.crypto.SignatureSpec
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec

class SignatureSpecUtilsTests {
    @Test
    fun `getParamsSafely should return null for SignatureSpec`() {
        val spec = SignatureSpec("SHA256withECDSA")
        assertNull(spec.getParamsSafely())
    }

    @Test
    fun `getParamsSafely should return params for ParameterizedSignatureSpec`() {
        val spec = ParameterizedSignatureSpec(
            "RSASSA-PSS",
            PSSParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                32,
                1
            )
        )
        assertSame(spec.params, spec.getParamsSafely())
    }
}