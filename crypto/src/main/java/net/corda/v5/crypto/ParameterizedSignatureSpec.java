package net.corda.v5.crypto;

import java.security.spec.AlgorithmParameterSpec;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to define a digital signature scheme which has the additional algorithm parameters,
 * such as RSASSA-PSS
 *
 * @param signatureName A signature-scheme name as required to create [java.security.Signature]
 * objects (e.g. "SHA256withECDSA")
 * @param params Signature parameters. For example, if using RSASSA-PSS, to avoid
 * using the default SHA1, you must specify the signature parameters explicitly.
 *
 * When used for signing the [signatureName] must match the corresponding key scheme, e.g. you cannot use
 * "SHA256withECDSA" with "RSA" keys.
 */
public final class ParameterizedSignatureSpec extends SignatureSpec {
    @NotNull private final AlgorithmParameterSpec params;

    /**
     * Converts a [ParameterizedSignatureSpec] object to a string representation.
     */
    @NotNull public String toString() {
        return this.getSignatureName() + ':' + this.params.getClass().getSimpleName();
    }

    @NotNull
    public final AlgorithmParameterSpec getParams() {
        return this.params;
    }

    public ParameterizedSignatureSpec(@NotNull String signatureName, @NotNull AlgorithmParameterSpec params) {
        super(signatureName);
        this.params = params;
    }
}
