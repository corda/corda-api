package net.corda.v5.application.interop;

import net.corda.v5.application.interop.facade.FacadeId;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.interop.InterOpIdentityInfo;
import net.corda.v5.membership.MemberInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@link InteropIdentityLookUp} allows flows to retrieve the {@link InterOpIdentityInfo} for any registered InterOp Alias Identity, including it's own Alias Identity.
 * <p>
 * The platform will provide an instance of {@link InteropIdentityLookUp} to flows via property injection.
 */
@DoNotImplement
public interface InteropIdentityLookUp {

    /**
     * @param applicationName {@link String} The application name for InterOp Identity which is set during InterOp Alias Identity Registration.
     * @return The {@link InterOpIdentityInfo} representing the alias member info for given alias x500name and host network.
     */
    @Suspendable
    @NotNull
    InterOpIdentityInfo lookup(String applicationName);

    /**
     * @param facadeId The facadeId of the facade.
     * @return The {@link List<InterOpIdentityInfo>}  for any registered InterOp Alias Identity that implement the given FacadeId.
     */
    @Suspendable
    @NotNull
    List<InterOpIdentityInfo> lookup(FacadeId facadeId);
}
