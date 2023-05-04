package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.TypedParameter;
import net.corda.v5.base.types.MemberX500Name;

public interface FacadeService {

    <T> T getClientProxy(String facadeId, Class<T> expectedType, MemberX500Name alias, String interopGroup);

    String dispatch(Object target, String request);
}
