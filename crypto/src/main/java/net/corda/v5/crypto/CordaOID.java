package net.corda.v5.crypto;

import org.jetbrains.annotations.NotNull;

/**
 * OIDs used for the Corda platform. All entries MUST be defined in this file only and they MUST NOT be removed.
 * If an OID is incorrectly assigned, it should be marked deprecated and NEVER be reused again.
 */

public final class CordaOID {
    private CordaOID() {
        // this class is never constructed; it exists for the static strings only
    }

    /**
     * Assigned to R3, see http://www.oid-info.com/cgi-bin/display?oid=1.3.6.1.4.1.50530&action=display
     */
    @NotNull
    public static final String OID_R3_ROOT = "1.3.6.1.4.1.50530";

    /**
     * OIDs issued for the Corda platform.
     */
    @NotNull
    public static final String OID_CORDA_PLATFORM = OID_R3_ROOT + ".1";

    /**
     * Identifier for the X.509 certificate extension specifying the Corda role. See
     * https://r3-cev.atlassian.net/wiki/spaces/AWG/pages/156860572/Certificate+identity+type+extension for details.
     */

    @NotNull
    public static final String OID_X509_EXTENSION_CORDA_ROLE = OID_CORDA_PLATFORM + ".1";

    /**
     * OID for AliasPrivateKey.
     */

    @NotNull
    public static final String OID_ALIAS_PRIVATE_KEY = OID_CORDA_PLATFORM + ".2";

    /**
     * OID for CompositeKey.
     */

    @NotNull
    public static final String OID_COMPOSITE_KEY = OID_CORDA_PLATFORM + ".3";

    /**
     * OID for CompositeSignature.
     */

    @NotNull
    public static final String OID_COMPOSITE_SIGNATURE = OID_CORDA_PLATFORM + ".4";
}
