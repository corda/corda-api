package net.corda.v5.ledger.utxo.token.selection;

import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.crypto.SecureHash;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TokenCacheJavaAPITest {

    @Test
    public void callStringFlow() {
        final TokenCacheJavaAPITest.TokenCacheTestImpl tokenCache = new TokenCacheJavaAPITest.TokenCacheTestImpl();

        TokenClaimCriteria criteria = new TokenClaimCriteria(
                "tt",
                new SecureHash("SHA-256", new byte[1]),
                MemberX500Name.parse("CN=Alice, O=Alice Corp, L=LDN, C=GB"),
                "s",
                new BigDecimal(1)
        );

        TokenClaim result = tokenCache.tryClaim(criteria);
        result.useAndRelease(new ArrayList<>());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getClaimId()).isEqualTo("id");
        Assertions.assertThat(result.getClaimedTokens()).isEmpty();
    }

    public class TokenCacheTestImpl implements TokenSelection {

        @Nullable
        @Override
        public TokenClaim tryClaim(@NotNull TokenClaimCriteria criteria) {
            return new TokenClaimTestImpl();
        }
    }

    public class TokenClaimTestImpl implements TokenClaim{

        @NotNull
        @Override
        public String getClaimId() {
            return "id";
        }

        @NotNull
        @Override
        public List<ClaimedToken> getClaimedTokens() {
            return new ArrayList<ClaimedToken>();
        }

        @Override
        public void useAndRelease(@NotNull List<String> usedTokensRefs) {
        }
    }
}
