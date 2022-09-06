package net.corda.v5.base.versioning;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representation of a version number
 */
public final class Version {
    private static final Pattern VERSION = Pattern.compile("(\\d+)\\.(\\d+)");

    /**
     * Parses a version object from a string.
     *
     * @param versionString A string containing the version to be parsed.
     * @throws IllegalArgumentException An IllegalArgumentException is thrown if the string is not a well-formed
     * version string.
     */
    @NotNull
    public static Version fromString(@NotNull String versionString) {
        Matcher matcher = VERSION.matcher(versionString);
        if (matcher.matches()) {
            return new Version(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        } else {
            throw new IllegalArgumentException(versionString + " is not a valid version string");
        }
    }

    private final int major;
    private final int minor;

    /**
     * @param major The major part of the version
     * @param minor The minor part of the version
     */
    public Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        final Version other = (Version) obj;
        return major == other.major && minor == other.minor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor);
    }

    /**
     * @return the version as a string in the form of &#61;major&#62;.&#61;minor&#62;.
     */
    @Override
    @NotNull
    public String toString() {
        return String.format("%d.%d", major, minor);
    }
}
