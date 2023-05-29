package net.corda.v5.application.interop;

import net.corda.v5.application.interop.facade.FacadeId;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.interop.InterOpIdentityInfo;
import java.util.List;

/**
 * <p>This interface represents the injectable service for client corDapp to look up the remote alias identity.
 *
 */
@DoNotImplement
public interface InteropIdentityLookUp {

    /**
     * @param x500Name The x500Name of alias identity.
     * @param hostNetwork The host network of alias identity.
     * @return The {@link InterOpIdentityInfo} representing the alias member info for given alias x500name and host network.
     */
    InterOpIdentityInfo lookup(String applicationName);

    /**
     * @param facadeId The facadeId of the facade.
     * @return The {@link List< InterOpIdentityInfo >} representing the alias member info for given facadeId.
     */
    List<InterOpIdentityInfo> lookup(FacadeId facadeId);
}
