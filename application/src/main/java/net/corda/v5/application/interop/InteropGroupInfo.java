package net.corda.v5.application.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * <p>This interface represents the group details and alias members within that group.
 *
 */
@CordaSerializable
public interface InteropGroupInfo {
    /**
     * @return The {@link UUID} representing the group id.
     */
    @NotNull
    UUID getGroupId();

    /**
     * @return The {@link String} representing the group name.
     */
    @NotNull
    String getGroupName();

    /**
     * @return The {@link List<AliasMemberInfo>} representing the list of alias member within a group.
     */
    @NotNull
    List<AliasMemberInfo> getMembers();
}
