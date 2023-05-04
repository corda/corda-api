package net.corda.v5.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@CordaSerializable
public interface InteropGroupInfo {
    @NotNull
    UUID getGroupId();
    @NotNull
    String getGroupName();
    @NotNull
    List<AliasMemberInfo> getMembers();
}
