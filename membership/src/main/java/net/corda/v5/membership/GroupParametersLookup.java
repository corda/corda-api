package net.corda.v5.membership;

import net.corda.v5.base.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;

/**
 * {@link GroupParametersLookup} allows flows to access {@link GroupParameters} for further verification,
 * for example, accessing to check if issued money is from authoritative party in flows.
 */
@DoNotImplement
public interface GroupParametersLookup {

    /**
     * Returns the point in time {@link GroupParameters}
     */
    @NotNull
    GroupParameters getCurrentGroupParameters();
}
