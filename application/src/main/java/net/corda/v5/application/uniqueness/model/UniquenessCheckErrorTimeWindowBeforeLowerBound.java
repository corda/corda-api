package net.corda.v5.application.uniqueness.model;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/** Occurs when the specified time is before the lower bound. Only relevant for checking existing uniqueness results */
public interface UniquenessCheckErrorTimeWindowBeforeLowerBound extends UniquenessCheckError {
    @NotNull
    Instant getEvaluationTimestamp();

    @NotNull
    Instant getTimeWindowLowerBound();
}
