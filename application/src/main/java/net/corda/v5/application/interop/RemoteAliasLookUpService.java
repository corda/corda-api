package net.corda.v5.application.interop;

import net.corda.v5.base.annotations.DoNotImplement;

/**
 * <p>This interface represents the injectable service for client corDapp to look up the remote alias identity.
 *
 */
@DoNotImplement
public interface RemoteAliasLookUpService {

    /**
     * @return The {@link AliasMemberInfo} representing the alias member info for given alias member id.
     */
    AliasMemberInfo get(String identifier);
}
