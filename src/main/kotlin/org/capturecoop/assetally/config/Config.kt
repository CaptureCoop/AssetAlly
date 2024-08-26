package org.capturecoop.assetally.config

import kotlinx.serialization.Serializable
import org.capturecoop.assetally.utils.StringUtils

@Serializable
data class Config(
    val password: String = StringUtils.randomString(length = 50)
)