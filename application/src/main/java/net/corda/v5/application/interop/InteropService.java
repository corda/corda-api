package net.corda.v5.application.interop;

import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.base.types.MemberX500Name;
import org.jetbrains.annotations.NotNull;

@DoNotImplement
public interface InteropService {
    /**
     * Call an Interop facade.
     *
     * @param memberName The MemberX500name of the alter-ego.
     * @param facadeName The name of the facade to invoke.
     * @param methodName The name of the function within the facade to invoke.
     * @param payload The payload for the facade call.
     * @return The response string from the facade.
     */
    @Suspendable
    @NotNull String callFacade(@NotNull MemberX500Name memberName, @NotNull String facadeName, @NotNull String methodName, @NotNull String payload);
}
