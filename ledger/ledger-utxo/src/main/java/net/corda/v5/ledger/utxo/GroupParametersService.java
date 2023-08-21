package net.corda.v5.ledger.utxo;

import net.corda.v5.membership.GroupParameters;
import org.jetbrains.annotations.NotNull;

/**
 * {@link GroupParametersService} allows flows to access {@link GroupParameters} for further verification,
 * for example, accessing to check if issued money is from authoritative party in flows.
 */
public interface GroupParametersService {

    /**
     * Returns the point in time {@link GroupParameters}
     */
    @NotNull
    GroupParameters getGroupParameters();
}
