package net.corda.v5.crypto;


import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

/**
 * This class is used to define a digital signature scheme.
 */
@CordaSerializable
public class SignatureSpec {
    /**
     * The signature-scheme name as required to create [java.security.Signature] objects (e.g. "SHA256withECDSA").
     */
    @NotNull
    private final String signatureName;

    /**
     * Construct a signature spec
     *
     * @param signatureName The signature-scheme name as required to create [java.security.Signature]
     *                      objects (e.g. "SHA256withECDSA").
     *                      <p>
     *                      When used for signing the [signatureName] must match the corresponding key scheme, e.g. you cannot use
     *                      "SHA256withECDSA" with "RSA" keys.
     */
    public SignatureSpec(@NotNull String signatureName) {
        super();
        this.signatureName = signatureName;
        if (signatureName == null) {
            throw new IllegalArgumentException("The signatureName must not be null.");
        }
        if (signatureName.isBlank()) {
            throw new IllegalArgumentException("The signatureName must not be blank.");
        }
    }


    /**
     * SHA256withRSA [SignatureSpec].
     */
    public static final SignatureSpec RSA_SHA256 = new SignatureSpec("SHA256withRSA");

    /**
     * SHA384withRSA [SignatureSpec].
     */
    public static final SignatureSpec RSA_SHA384 = new SignatureSpec("SHA384withRSA");

    /**
     * SHA512withRSA [SignatureSpec].
     */
    public static final SignatureSpec RSA_SHA512 = new SignatureSpec("SHA512withRSA");

    /**
     * RSASSA-PSS with SHA256 [SignatureSpec].
     */
    public static final ParameterizedSignatureSpec RSASSA_PSS_SHA256 = new ParameterizedSignatureSpec(
            "RSASSA-PSS",
            new PSSParameterSpec(
                    "SHA-256",
                    "MGF1",
                    MGF1ParameterSpec.SHA256,
                    32,
                    1
            )
    );

    /**
     * RSASSA-PSS with SHA384 [SignatureSpec].
     */
    public static final ParameterizedSignatureSpec RSASSA_PSS_SHA384 = new ParameterizedSignatureSpec(
            "RSASSA-PSS",
            new PSSParameterSpec(
                    "SHA-384",
                    "MGF1",
                    MGF1ParameterSpec.SHA384,
                    48,
                    1
            )
    );

    /**
     * RSASSA-PSS with SHA512 [SignatureSpec].
     */
    public static final ParameterizedSignatureSpec RSASSA_PSS_SHA512 = new ParameterizedSignatureSpec(
            "RSASSA-PSS",
            new PSSParameterSpec(
                    "SHA-512",
                    "MGF1",
                    MGF1ParameterSpec.SHA512,
                    64,
                    1
            )
    );

    /**
     * RSASSA-PSS with SHA256 and MGF1 [SignatureSpec].
     */
    public static final SignatureSpec RSA_SHA256_WITH_MGF1 = new SignatureSpec("SHA256withRSAandMGF1");

    /**
     * RSASSA-PSS with SHA384 and MGF1 [SignatureSpec].
     */
    public static final SignatureSpec RSA_SHA384_WITH_MGF1 = new SignatureSpec("SHA384withRSAandMGF1");

    /**
     * RSASSA-PSS with SHA512 and MGF1 [SignatureSpec].
     */
    public static final SignatureSpec RSA_SHA512_WITH_MGF1 = new SignatureSpec("SHA512withRSAandMGF1");

    /**
     * SHA256withECDSA [SignatureSpec].
     */
    public static final SignatureSpec ECDSA_SHA256 = new SignatureSpec("SHA256withECDSA");

    /**
     * SHA384withECDSA [SignatureSpec].
     */
    public static final SignatureSpec ECDSA_SHA384 = new SignatureSpec("SHA384withECDSA");

    /**
     * SHA512withECDSA [SignatureSpec].
     */
    public static final SignatureSpec ECDSA_SHA512 = new SignatureSpec("SHA512withECDSA");

    /**
     * EdDSA [SignatureSpec].
     */
    public static final SignatureSpec EDDSA_ED25519 = new SignatureSpec("EdDSA");

    /**
     * SHA512withSPHINCS256 [SignatureSpec].
     */
    public static final SignatureSpec SPHINCS256_SHA512 = new SignatureSpec("SHA512withSPHINCS256");

    /**
     * SM3withSM2 [SignatureSpec].
     */
    public static final SignatureSpec SM2_SM3 = new SignatureSpec("SM3withSM2");

    /**
     * SHA256withSM2 [SignatureSpec].
     */
    public static final SignatureSpec SM2_SHA256 = new SignatureSpec("SHA256withSM2");

    /**
     * GOST3411withGOST3410 [SignatureSpec].
     */
    public static final SignatureSpec GOST3410_GOST3411 = new SignatureSpec("GOST3411withGOST3410");

    public boolean equals(@Nullable Object other) {
        return this == other || (other != null && (other instanceof SignatureSpec) && ((SignatureSpec) other).signatureName.equals(this.signatureName));
    }

    /**
     * Converts a [SignatureSpec] object to a string representation containing the [signatureName].
     *
     * @return a string containing the signature name
     */
    public @NotNull String toString() {
        return this.signatureName;
    }

    /**
     * Alternative access getter for the signature name, for completeness; same result as [toString].
     *
     * @return a string containing the signature name.
     */
    public final @NotNull String getSignatureName() {
        return this.signatureName;
    }

    /**
     * Obtain a hashc code for the signature name.
     *
     * @return the hash code
     */
    public int hashCode() {
        return this.signatureName.hashCode();
    }
    
}