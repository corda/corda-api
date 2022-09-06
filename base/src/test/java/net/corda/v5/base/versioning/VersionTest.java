package net.corda.v5.base.versioning;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VersionTest {

    @Test
    void parseVersionFromValidVersionStrings() {
        Version version = Version.fromString("1.0");
        assertEquals(1, version.getMajor());
        assertEquals(0, version.getMinor());
    }

    @ParameterizedTest(name = "parse a version from invalid version strings: {0}")
    @ValueSource(strings = { "foo", "1.1.3", "abc.cdf", "1", "-1.-3", "1.a", "b.6", "1a.b5", "1."})
    void parseVersionFromInvalidVersionStrings(String versionString) {
        assertThrows(IllegalArgumentException.class, () ->
            Version.fromString(versionString)
        );
    }
}
