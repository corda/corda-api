package net.corda.schema.configuration

/**
 * Exception thrown when requested config schema is not available.
 */
class ConfigSchemaException(msg: String) : Exception(msg)