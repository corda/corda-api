package net.corda.schema.configuration;

/**
 * Membership configuration keys.
 */
public final class MembershipConfig {
    private MembershipConfig() {
    }

    /**
     * The configuration key to get the maximal duration between two synchronization requests.
     */
    public static final String MAX_DURATION_BETWEEN_SYNC_REQUESTS_MINUTES = "maxDurationBetweenSyncRequestsMinutes";

    /**
     * The configuration key to get the maximum duration in minutes between polling for expired registration requests.
     */
    public static final String MAX_DURATION_BETWEEN_EXPIRED_REGISTRATION_REQUESTS_POLLS = "frequencyOfExpirationPoll";

    /**
     * The configuration key to get the time frame in minutes after which a registration request is considered as expired and gets force declined.
     */
    public static final String EXPIRATION_DATE_FOR_REGISTRATION_REQUESTS = "expirationTimeFrame";

    public static final class TtlsConfig {
        private TtlsConfig() {
        }

        /**
         * The configuration key to get the TTLs durations.
         */
        public static final String TTLS = "TTLs";

        /**
         * Maximum waiting time in minutes for a member's package update message.
         */
        public static final String MEMBERS_PACKAGE_UPDATE = "membersPackageUpdate";

        /**
         * Maximum waiting time in minutes for a decline registration message.
         */
        public static final String DECLINE_REGISTRATION = "declineRegistration";

        /**
         * Maximum waiting time in minutes for an update registration status to pending auto-approval.
         */
        public static final String UPDATE_TO_PENDING_AUTO_APPROVAL = "updateToPendingAutoApproval";

        /**
         * Maximum waiting time in minutes for verification of a member request.
         */
        public static final String VERIFY_MEMBER_REQUEST = "verifyMemberRequest";

        /**
         * Maximum waiting time for a registration request to be received by the MGM.
         */
        public static final String WAIT_FOR_MGM_SESSION = "waitForMgmSession";
    }
}
