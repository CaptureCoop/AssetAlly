package org.capturecoop.assetally.utils

import kotlinx.serialization.json.Json

object JsonUtils {
    val serializer = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
}