package net.corda.v5.application.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.interop.AliasMemberInfo;

@CordaSerializable
public interface RemoteAliasLookUpService {

    /**
     * This will return the alias info for specific identifier.
     */
    AliasMemberInfo get(String identifier);
}
