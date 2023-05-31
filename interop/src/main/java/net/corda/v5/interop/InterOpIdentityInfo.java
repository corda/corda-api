package net.corda.v5.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * This interface represents the InterOp Identity Info that have been registered with the User.
 * <p> InterOpIdentityInfo details like Application Name, implemented facadeIds and X500 name.
 *
 *
 */
@CordaSerializable
public interface InterOpIdentityInfo {
    /**
     * @return The {@link String} identifier for the InterOp Identity. This is the Common Name (CN) from the X500 and is set during Interop Registration.
     */
    @NotNull
    String applicationName();

    /**
     * @return The {@link String} of the Identity's X500.
     */
    @NotNull
    String getX500Name();

    /**
     * @return The {@link List<String>} representing the list of facade ids.
     */
    @NotNull
    List<String> getFacadeIds();
}
