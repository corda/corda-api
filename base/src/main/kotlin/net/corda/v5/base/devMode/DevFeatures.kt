package net.corda.v5.base.devMode

import net.corda.v5.base.util.debug
import org.slf4j.LoggerFactory

object DevFeatures {

    // Below a list of constants defined that represent optional developer features which can be enabled either by
    // setting a system property or setting an environment variable.
    // Keys of dev features are defined as string constants such that it would be possible to use them at compile time, e.g. in annotations.
    // Values for Dev Features are booleans and by default they are all set to "false".
    // Below a dedicated method is provided to check if a particular developer feature is set.

    // Enables to perform Virtual Nodes reset in the running cluster
    const val DEV_FEATURE_VN_RESET_ENABLED = "dev.feature.vn.reset.enabled"

    // Enables retrieval via HTTP RPC a list of running sandboxes on Flow Workers
    const val DEV_FEATURE_SANDBOX_LIST_ENABLED = "dev.feature.sandbox.list.enabled"

    val allDevFeaturesKeys: Set<String>

    private val log = LoggerFactory.getLogger(DevFeatures::class.java)

    init {
        val mutableSet = mutableSetOf<String>()
        register(DEV_FEATURE_VN_RESET_ENABLED, mutableSet)
        register(DEV_FEATURE_SANDBOX_LIST_ENABLED, mutableSet)

        // Seal by assigning to immutable set
        allDevFeaturesKeys = mutableSet
    }

    private fun register(featureKey: String, registerSoFar: MutableSet<String>) {
        if (registerSoFar.contains(featureKey)) {
            throw IllegalStateException("Dev feature key: '$featureKey' has already been added once.")
        }
        registerSoFar.add(featureKey.toLowerCase())
    }

    fun String.isSet(): Boolean {
        if (!allDevFeaturesKeys.contains(this.toLowerCase())) {
            throw IllegalStateException(
                "Dev feature key: '$this' has not been properly registered. " +
                        "Please make sure that method `register()` been called on it."
            )
        }

        val systemPropertyValue: String? = System.getProperty(this)
        if(systemPropertyValue != null) {
            return parseBoolean(systemPropertyValue)
        }

        val osEnvValue: String? = System.getenv(this)
        if(osEnvValue != null) {
            return parseBoolean(osEnvValue)
        }

        log.debug { "Neither system property nor environment variable is set for '$this' dev feature. Returning false." }
        return false
    }

    private fun parseBoolean(value: String): Boolean {
        return "true".equals(value, true) || "1" == value
    }
}