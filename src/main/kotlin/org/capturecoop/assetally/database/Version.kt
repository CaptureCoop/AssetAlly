package org.capturecoop.assetally.database

import kotlinx.serialization.Serializable

@Serializable
data class Version (
    val major: Int,
    val minor: Int,
    val patch: Int
): Comparable<Version> {

    override fun hashCode(): Int {
        var result = major
        result = 31 * result + minor
        result = 31 * result + patch
        return result
    }

    override fun equals(other: Any?) = other is Version && other.hashCode() == hashCode()

    override fun compareTo(other: Version): Int {
        if(this == other) return 0

        major.compareTo(other.major).also { if(it != 0) return it }
        minor.compareTo(other.minor).also { if(it != 0) return it }
        patch.compareTo(other.patch).also { if(it != 0) return it }

        return 0
    }

    override fun toString() = "$major.$minor.$patch"

    companion object {
        fun fromString(version: String): Version {
            val (major, minor, patch) = version.split(".").map { it.toInt() }
            return Version(major, minor, patch)
        }
    }
}