package net.corda.v5.application.interop;

import net.corda.v5.application.interop.facade.FacadeId;
import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * This interface represents the InterOp Identity Info that have been registered with the User.
 * <p> InterOpIdentityInfo details like Application Name and implemented facadeIds.
 *
 *
 */
@CordaSerializable
public interface InterOpIdentityInfo {
    /**
     * @return The {@link String} identifier for the InterOp Identity.
     * This is the Application name for the Identity is set during Interop Registration.
     */
    @NotNull
    String getApplicationName();

    /**
     * @return The {@link List<String>} representing the list of facade ids.
     */
    @NotNull
    List<FacadeId> getFacadeIds();

    @NotNull
    UUID getGroupId();

    @NotNull
    String getX500Name();
}
