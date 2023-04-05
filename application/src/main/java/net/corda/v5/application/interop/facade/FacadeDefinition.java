package net.corda.v5.application.interop.facade;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class FacadeDefinition {

    @NotNull
    private final String id;

    @Nullable
    private final Map<String, String> aliases;

    @Nullable
    private final Map<String, FacadeMethodDefinition> queries;

    @Nullable
    private final Map<String, FacadeMethodDefinition> commands;

    public FacadeDefinition(@NotNull String id, @Nullable Map<String, String> aliases,
                            @Nullable Map<String, FacadeMethodDefinition> queries,
                            @Nullable Map<String, FacadeMethodDefinition> commands) {
        this.id = id;
        this.aliases = aliases;
        this.queries = queries;
        this.commands = commands;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @Nullable
    public Map<String, String> getAliases() {
        return aliases;
    }

    @Nullable
    public Map<String, FacadeMethodDefinition> getQueries() {
        return queries;
    }

    @Nullable
    public Map<String, FacadeMethodDefinition> getCommands() {
        return commands;
    }
}
