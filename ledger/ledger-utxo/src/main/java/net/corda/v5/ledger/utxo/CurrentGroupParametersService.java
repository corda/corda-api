package net.corda.v5.ledger.utxo;

import net.corda.v5.membership.GroupParameters;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

/**
 * {@link CurrentGroupParametersService} allows flows to access {@link GroupParameters} for further verification,
 * for example, accessing to check if issued money is from authoritative party in flows.
 */
public interface CurrentGroupParametersService {

    /**
     * Returns the point in time {@link GroupParameters}
     */
    @NotNull
    GroupParameters getCurrentGroupParameters();

    /**
     * Returns the list of {@link PublicKey} of MGM.
     */
    @NotNull
    List<PublicKey> getMgmKeys();
}
