package net.corda.v5.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * <p>This interface represents the alias member details like x500name, facadeIds etc.
 *
 */
@CordaSerializable
public interface AliasMemberInfo {
    /**
     * @return The {@link String} representing the X500Name of the alias member.
     */
    @NotNull
    String getX500Name();

    /**
     * @return The {@link String} representing the cpi name.
     */
    @NotNull
    String getCpiName();

    /**
     * @return The {@link String} representing the unique identifier for the alias member.
     */
    String getIdentifier();

    /**
     * @return The {@link List<String>} representing the list of facade ids.
     */
    @NotNull
    List<String> getFacadeIds();
}
