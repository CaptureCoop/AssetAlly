package org.capturecoop.assetally.routes

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.capturecoop.assetally.database.AssetDatabase
import org.capturecoop.assetally.config.ConfigManager
import org.capturecoop.assetally.utils.read
import java.io.File

fun Routing.uploadRoute() {
    post("/upload") {
        if(!ConfigManager.authenticate(call.request.authorization() ?: ""))
            return@post call.response.status(HttpStatusCode.Unauthorized)

        val multiPart = call.receiveMultipart().read()
        val file = multiPart.getFileItem("file")!!

        AssetDatabase.addItem(
            project = multiPart.getFormItem("project")!!.value,
            type = multiPart.getFormItem("type")!!.value,
            version = multiPart.getFormItem("version")!!.value,
            file = file.streamProvider().readAllBytes(),
            extension = File(file.originalFileName!!).extension
        )
        call.response.status(HttpStatusCode.Created)
    }
}