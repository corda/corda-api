package net.corda.v5.application.interop;

import net.corda.v5.base.annotations.CordaSerializable;
import java.util.List;

/**
 * <p>This interface represents a set of alias groups created for specific holding identity across the network.
 *
 */
@CordaSerializable
public interface HoldingIdAliasGroupInfo {
    /**
     * @return The {@link String} representing the short hash of the holding identity.
     */
    String getShortHash();

    /**
     * @return The {@link List<InteropGroupInfo>} representing list of interop groups for the holding identity.
     */
    List<InteropGroupInfo> getGroups();
}