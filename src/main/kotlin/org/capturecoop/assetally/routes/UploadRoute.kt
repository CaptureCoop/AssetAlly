package org.capturecoop.assetally.routes

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.capturecoop.assetally.AssetDatabase
import org.capturecoop.assetally.ConfigManager
import org.capturecoop.assetally.badRequest
import org.capturecoop.assetally.ok
import java.io.File

fun Routing.uploadRoute() {
    post("/upload") {
        val multiParts = call.receiveMultipart().readAllParts()

        val (project, type, version) = multiParts.filterIsInstance<PartData.FormItem>()
            .associate { Pair(it.name, it.value) }
            .let { keys -> listOf(keys["project"]!!, keys["type"]!!, keys["version"]!!) }

        val file = multiParts.filterIsInstance<PartData.FileItem>().first { it.name == "file" }
        AssetDatabase.addItem(
            project = project,
            type = type,
            version = version,
            file = file.streamProvider().readAllBytes(),
            extension = File(file.originalFileName!!).extension
        )
        call.ok()
    }
}