package net.corda.v5.crypto;

/**
 * OIDs used for the Corda platform. All entries MUST be defined in this file only and they MUST NOT be removed.
 * If an OID is incorrectly assigned, it should be marked deprecated and NEVER be reused again.
 */

public final class CordaOID {
    private CordaOID() {
        // this class is never constructed; it exists for the static strings only
    }
    
    /** Assigned to R3, see http://www.oid-info.com/cgi-bin/display?oid=1.3.6.1.4.1.50530&action=display */
    public static final String OID_R3_ROOT = "1.3.6.1.4.1.50530";

    /** OIDs issued for the Corda platform. */
    public static final String OID_CORDA_PLATFORM = "1.3.6.1.4.1.50530.1";

    /**
     * Identifier for the X.509 certificate extension specifying the Corda role. See
     * https://r3-cev.atlassian.net/wiki/spaces/AWG/pages/156860572/Certificate+identity+type+extension for details.
     */
    
    public static final String OID_X509_EXTENSION_CORDA_ROLE = "1.3.6.1.4.1.50530.1.1";

    /** OID for AliasPrivateKey. */

    public static final String OID_ALIAS_PRIVATE_KEY = "1.3.6.1.4.1.50530.1.2";

    /** OID for CompositeKey. */

    public static final String OID_COMPOSITE_KEY = "1.3.6.1.4.1.50530.1.3";

    /** OID for CompositeSignature. */

    public static final String OID_COMPOSITE_SIGNATURE = "1.3.6.1.4.1.50530.1.4";
}
