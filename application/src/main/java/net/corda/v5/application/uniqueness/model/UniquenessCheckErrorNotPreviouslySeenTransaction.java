package net.corda.v5.application.uniqueness.model;

/**
 * Occurs when a transaction has not been seen before when checking for existing uniqueness check results.
 */
public interface UniquenessCheckErrorNotPreviouslySeenTransaction extends UniquenessCheckError {
}
