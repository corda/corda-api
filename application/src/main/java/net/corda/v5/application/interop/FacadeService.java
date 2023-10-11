package net.corda.v5.application.interop;

import net.corda.v5.application.interop.facade.Facade;
import net.corda.v5.application.interop.facade.FacadeId;
import net.corda.v5.application.interop.facade.FacadeRequest;
import net.corda.v5.application.interop.facade.FacadeResponse;
import net.corda.v5.base.annotations.Suspendable;

/**
 * Service to perform Corda to Corda interoperability calls using Facades.
 */
public interface FacadeService {

    /**
     * Returns a concrete class implementing interface <code>T</code> allowing to call a facade (@see {@link Facade})
     * on the peers from other Membership groups.
     * @param facadeId The Facade ID denoting a facade specification used as communication with a peer.
     * @param expectedType The interface implementing (all or parts) of the facade specification.
     * @param interopIdentity InterOpIdentityInfo of the peer to be called.
     * @param <T> The interface class which the returned object implements.
     * @return An instance of Java Dynamic Proxy implementing a given interface. Any method invocation on this
     *    instance invokes a Facade method on a peer Identity denoted by an <code>alias</code> from an <code>interopGroup</code>.
     */
    @Suspendable
    <T> T getProxy(FacadeId facadeId, Class<T> expectedType, InterOpIdentityInfo interopIdentity);

    /**
     * Interprets a Facade request and invokes a matching method on the target class.
     * @param target The class implementing a Facade.
     * @param request The string with the request conforming {@link FacadeRequest} format.
     * @return The result of processing a Facade Request, the returned string conforms {@link FacadeResponse} format.
     */
    @Suspendable
    String dispatchFacadeRequest(Object target, String request);
}
