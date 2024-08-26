package org.capturecoop.assetally

import kotlinx.serialization.json.Json

object JsonUtils {
    val serializer = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
}