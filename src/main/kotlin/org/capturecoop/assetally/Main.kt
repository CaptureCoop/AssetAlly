package org.capturecoop.assetally

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.capturecoop.assetally.config.ConfigManager
import org.capturecoop.assetally.database.AssetDatabase
import org.capturecoop.assetally.routes.assetRoute
import org.capturecoop.assetally.routes.uploadRoute
import org.capturecoop.assetally.utils.JsonUtils

fun main() {
    ConfigManager
    AssetDatabase

    embeddedServer(
        factory = Netty,
        port = 80,
        host = "127.0.0.1",
        module = Application::module
    ).start(wait = true)
}

private fun Application.module() {
    install(ContentNegotiation) {
        json(JsonUtils.serializer)
    }

    routing {
        uploadRoute()
        assetRoute()
        get("/") {
            call.respondText("AssetAlly is running!")
        }
    }
}
