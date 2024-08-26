package org.capturecoop.assetally.database

import kotlinx.serialization.encodeToString
import org.capturecoop.assetally.config.ConfigManager
import org.capturecoop.assetally.utils.JsonUtils
import java.io.File

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

    fun getLatest(project: String, type: String): DatabaseItem? {
        return get(project, type).maxByOrNull { it.version }
    }

    fun getLatestBytes(project: String, type: String): ByteArray? {
        return File(ConfigManager.assetsFolder, getLatest(project, type)!!.file).readBytes()
    }

    fun get(project: String): List<DatabaseItem> {
        return data.values.filter { it.project == project }
    }

    fun get(project: String, type: String): List<DatabaseItem> {
        return data.values.filter { it.project == project && it.type == type }
    }

    fun get(project: String, type: String, version: String): DatabaseItem? {
        return data.values.firstOrNull { it.project == project && it.type == type && it.version == Version.fromString(
            version
        )
        }
    }

    fun getBytes(project: String, type: String, version: String): ByteArray? {
        val found = get(project, type, version) ?: return null
        return File(ConfigManager.assetsFolder, found.file).readBytes()
    }

    private fun toName(project: String, type: String, version: String) = "$project-$type-$version"
}