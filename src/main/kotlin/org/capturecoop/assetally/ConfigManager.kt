package org.capturecoop.assetally

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class Config(
    val password: String = StringUtils.randomString(length = 50)
)

object ConfigManager {
    val rootFolder = File(".AssetAlly").apply { mkdirs() }
    val assetsFolder = File(rootFolder, "assets").apply { mkdirs() }
    private val config = File(rootFolder, "config.json").let {
        if(it.exists()) JsonUtils.serializer.decodeFromString<Config>(it.readText())
        else Config().apply { it.writeText(JsonUtils.serializer.encodeToString(this)) }
    }

    fun authenticate(password: String) = config.password == password
}