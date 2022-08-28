package net.corda.v5.ledger.consensual;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.types.MemberX500Name;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;

/**
 * Representation of a Party.
 *
 *
 * Since used in {@link ConsensualState} interface, their implementors will depend on it.
 * To make those implementations serializable, we use a custom serializer in the background, so
 * the normal @CordaSerializable cannot be used here.
 */
@DoNotImplement
public interface Party {
    /**
     * @return name X500 Name of the party.
     */
    @NotNull
    MemberX500Name getName();

    /**
     * @return owningKey The {@link PublicKey} owned by the party.
     */
    @NotNull
    PublicKey getOwningKey();
}
