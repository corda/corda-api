package net.corda.v5.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@CordaSerializable
public interface AliasMemberInfo {
    @NotNull
    String getX500Name();
    @NotNull
    String getCpiName();
    String getIdentifier(); // account + "@" + x500Name + "@" + cpiName;
    @NotNull
    List<String> getFacadeIds();
}
