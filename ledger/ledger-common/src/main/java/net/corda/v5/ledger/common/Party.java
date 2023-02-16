package net.corda.v5.ledger.common;

import net.corda.v5.base.annotations.CordaSerializable;
import net.corda.v5.base.types.MemberX500Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Represents a well-known party identity.
 */
// TODO : Rename owningKey to signingKey
@CordaSerializable
public final class Party {

    /**
     * The well-known {@link MemberX500Name} that represents the current party identity.
     */
    @NotNull
    private final MemberX500Name name;

    /**
     * The {@link PublicKey} that represents the current party identity.
     */
    @NotNull
    private final PublicKey owningKey;

    /**
     * Creates a new instance of the {@link Party} class.
     *
     * @param name The well-known {@link MemberX500Name} that represents the current identity.
     * @param owningKey The public key that represents the current party identity.
     */
    public Party(@NotNull final MemberX500Name name, @NotNull final PublicKey owningKey) {
        this.name = name;
        this.owningKey = owningKey;
    }

    /**
     * Gets the well-known {@link MemberX500Name} that represents the current identity.
     *
     * @return Returns the well-known {@link MemberX500Name} that represents the current identity.
     */
    @NotNull
    public MemberX500Name getName() {
        return name;
    }

    /**
     * Gets the {@link PublicKey} that represents the current party identity.
     *
     * @return Returns the {@link PublicKey} that represents the current party identity.
     */
    @NotNull
    public PublicKey getOwningKey() {
        return owningKey;
    }

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param obj The object to compare with the current object.
     * @return Returns true if the specified object is equal to the current object; otherwise, false.
     */
    @Override
    public boolean equals(@Nullable final Object obj) {
        return this == obj || obj instanceof Party && equals((Party) obj);
    }

    /**
     * Determines whether the specified object is equal to the current object.
     *
     * @param other The Party to compare with the current object.
     * @return Returns true if the specified Party is equal to the current object; otherwise, false.
     */
    public boolean equals(@NotNull final Party other) {
        return Objects.equals(other.name, name)
                && Objects.equals(other.owningKey, owningKey);
    }

    /**
     * Serves as the default hash function.
     *
     * @return Returns a hash code for the current object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, owningKey);
    }

    /**
     * Returns a string that represents the current object.
     *
     * @return Returns a string that represents the current object.
     */
    @Override
    public String toString() {
        return MessageFormat.format("Party(name={0}, owningKey={1})", name, owningKey);
    }
}
