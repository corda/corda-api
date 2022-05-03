package net.corda.v5.base.devMode

import net.corda.v5.base.devMode.DevFeatures.DEV_FEATURE_SANDBOX_LIST_ENABLED
import net.corda.v5.base.devMode.DevFeatures.DEV_FEATURE_VN_RESET_ENABLED
import net.corda.v5.base.devMode.DevFeatures.allDevFeaturesKeys
import net.corda.v5.base.devMode.DevFeatures.isSet
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DevFeaturesTest {

    companion object {

        private lateinit var properties: Properties

        @BeforeAll
        fun setup() {
            properties = System.getProperties()
        }

        @AfterAll
        fun tearDown() {
            System.setProperties(properties)
        }
    }

    @Test
    fun testSunnyDay() {
        assertThat(allDevFeaturesKeys).contains(DEV_FEATURE_VN_RESET_ENABLED, DEV_FEATURE_SANDBOX_LIST_ENABLED)

        System.setProperty(DEV_FEATURE_VN_RESET_ENABLED, "")
        assertFalse(DEV_FEATURE_VN_RESET_ENABLED.isSet())

        System.setProperty(DEV_FEATURE_VN_RESET_ENABLED, "TRUE")
        assertTrue(DEV_FEATURE_VN_RESET_ENABLED.isSet())

        System.setProperty(DEV_FEATURE_VN_RESET_ENABLED, "")
        assertFalse(DEV_FEATURE_VN_RESET_ENABLED.isSet())

        System.setProperty(DEV_FEATURE_VN_RESET_ENABLED, "1")
        assertTrue(DEV_FEATURE_VN_RESET_ENABLED.isSet())
    }

    @Test
    fun testUnknownKey() {
        assertThatThrownBy { "foo.bar".isSet() }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("register()")
    }
}