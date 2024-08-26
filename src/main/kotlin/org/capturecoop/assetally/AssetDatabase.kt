package org.capturecoop.assetally

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class DatabaseItem (
    val project: String,
    val type: String,
    val version: Version,
    val file: String
)

@Serializable
data class Version (
    val major: Int,
    val minor: Int,
    val patch: Int
) {

    override fun toString() = "$major.$minor.$patch"

    companion object {
        fun fromString(version: String): Version {
            val (major, minor, patch) = version.split(".").map { it.toInt() }
            return Version(major, minor, patch)
        }
    }
}

object AssetDatabase {
    private val data = HashMap<String, DatabaseItem>()

    init {
        ConfigManager.assetsFolder.walk().filter {
            it.extension == "json"
        }.map {
            val decodedItem = JsonUtils.serializer.decodeFromString<DatabaseItem>(it.readText())
            val name = it.nameWithoutExtension
            Pair(name, decodedItem)
        }.forEach { item -> data[item.first] = item.second }
    }

    fun addItem(project: String, type: String, version: String, file: ByteArray, extension: String) {
        val name = toName(project, type, version)
        val item = DatabaseItem(
            project = project,
            type = type,
            version = Version.fromString(version),
            file = "$name.$extension"
        )
        File(ConfigManager.assetsFolder, "$name.$extension").writeBytes(file)
        File(ConfigManager.assetsFolder, "$name.json").writeText(JsonUtils.serializer.encodeToString(item))

        data[name] = item
    }

    fun get(project: String): List<DatabaseItem> {
        return data.values.filter { it.project == project }
    }

    fun get(project: String, type: String): List<DatabaseItem> {
        return data.values.filter { it.project == project && it.type == type }
    }

    fun get(project: String, type: String, version: String): DatabaseItem? {
        return data.values.firstOrNull { it.project == project && it.type == type && it.version == Version.fromString(version) }
    }

    fun getBytes(project: String, type: String, version: String): ByteArray? {
        val found = get(project, type, version) ?: return null
        return File(ConfigManager.assetsFolder, found.file).readBytes()
    }

    private fun toName(project: String, type: String, version: String) = "$project-$type-$version"
}