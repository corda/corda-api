package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class FacadeMethodDefinition {
    @Nullable
    private final Map<String, String> in;

    @Nullable
    private final Map<String, String> out;

    public FacadeMethodDefinition(@Nullable Map<String, String> in, @Nullable Map<String, String> out) {
        this.in = in;
        this.out = out;
    }

    @Nullable
    public Map<String, String> getIn() {
        return in;
    }

    @Nullable
    public Map<String, String> getOut() {
        return out;
    }
}
