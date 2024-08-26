package org.capturecoop.assetally.config

import kotlinx.serialization.encodeToString
import org.capturecoop.assetally.utils.JsonUtils
import java.io.File

object ConfigManager {
    val rootFolder = File(".AssetAlly").apply { mkdirs() }
    val assetsFolder = File(rootFolder, "assets").apply { mkdirs() }
    private val config = File(rootFolder, "config.json").let {
        if(it.exists()) JsonUtils.serializer.decodeFromString<Config>(it.readText())
        else Config().apply { it.writeText(JsonUtils.serializer.encodeToString(this)) }
    }

    fun authenticate(password: String) = config.password == password
}