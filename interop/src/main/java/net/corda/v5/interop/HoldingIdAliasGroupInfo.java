package net.corda.v5.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import java.util.List;

@CordaSerializable
public interface HoldingIdAliasGroupInfo {
    String getShortHash();
    List<InteropGroupInfo> getGroups();
}
