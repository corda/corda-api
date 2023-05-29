package net.corda.v5.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * <p>This interface represents the alias member details like Application Name, facadeIds etc.
 *
 */
@CordaSerializable
public interface InterOpIdentityInfo {
    /**
     * @return The {@link String} identifier for the InterOp Identity.
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
