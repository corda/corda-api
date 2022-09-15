package net.corda.v5.base.versioning

/**
 * Representation of a version number
 *
 * @param major The major part of the version
 * @param minor The minor part of the version
 */
data class Version(val major: Int, val minor: Int) {

    /**
     * Print the version as a string in the form of <major>.<minor>.
     */
    override fun toString(): String {
        return "$major.$minor"
    }

    companion object {

        /**
         * Parses a version objet out of a string.
         *
         * @param versionString Version string to be parsed
         * @throws IllegalArgumentException An IllegalArgumentException is thrown if the string is not a well-formed
         * version string.
         */
        @JvmStatic
        fun fromString(versionString: String): Version {
            val regex = Regex("(\\d+)\\.(\\d+)")
            val match = regex.matchEntire(versionString)
            return if (match != null) {
                val (majorStr, minorStr) = match.destructured
                Version(majorStr.toInt(), minorStr.toInt())
            } else {
                throw IllegalArgumentException("$versionString is not a valid version string")
            }
        }
    }
}