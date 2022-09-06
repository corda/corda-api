package net.corda.v5.application.messaging;

import net.corda.v5.application.flows.InitiatingFlow;
import net.corda.v5.base.annotations.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Version and name of the CorDapp hosting the other side of the flow.
 */
@CordaSerializable
public final class FlowInfo {
    private final int flowVersion;
    private final String appName;

    /**
     * @param flowVersion The integer flow version the other side is using.
     *
     * @see InitiatingFlow
     *
     * @param appName Name of the CorDapp jar hosting the flow, without the .jar extension. It will include a unique identifier
     * to deduplicate it from other releases of the same CorDapp, typically a version string. See the
     * <a href="https://docs.corda.net/cordapp-build-systems.html#cordapp-jar-format">CorDapp JAR format</a> for more details.
     */
    public FlowInfo(int flowVersion, @NotNull String appName) {
        Objects.requireNonNull(appName, "appName cannot be null");
        this.flowVersion = flowVersion;
        this.appName = appName;
    }

    public int getFlowVersion() {
        return flowVersion;
    }

    @NotNull
    public String getAppName() {
        return appName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FlowInfo)) {
            return false;
        }
        final FlowInfo other = (FlowInfo) obj;
        return flowVersion == other.flowVersion && appName.equals(other.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flowVersion, appName);
    }

    @Override
    @NotNull
    public String toString() {
        return "FlowInfo[flowVersion=" + flowVersion + ", appName=" + appName + ']';
    }
}
