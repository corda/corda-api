package net.corda.v5.application.interop;

import net.corda.v5.application.interop.facade.FacadeId;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.interop.InterOpIdentityInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <p>This interface represents the injectable service for client corDapp to look up the remote alias identity.
 *
 */
@DoNotImplement
public interface InteropIdentityLookUp {

    /**
     * @return The {@link InterOpIdentityInfo} representing the alias member info for given alias x500name and host network.
     */
    @Suspendable
    @NotNull
    InterOpIdentityInfo lookup(String applicationName);

    /**
     * @param facadeId The facadeId of the facade.
     * @return The {@link List< InterOpIdentityInfo >} representing the alias member info for given facadeId.
     */
    @Suspendable
    @NotNull
    List<InterOpIdentityInfo> lookup(FacadeId facadeId);
}
