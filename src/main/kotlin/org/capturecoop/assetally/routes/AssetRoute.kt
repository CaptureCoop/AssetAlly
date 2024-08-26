package org.capturecoop.assetally.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.capturecoop.assetally.database.AssetDatabase

fun Routing.assetRoute() {
    route("/assets") {
        get("/{project}") {
            val project = call.parameters["project"]!!
            call.respond(AssetDatabase.get(project))
        }
        get("/{project}/{type}") {
            val project = call.parameters["project"]!!
            val type = call.parameters["type"]!!
            call.respond(AssetDatabase.get(project, type))
        }
        get("/{project}/{type}/{version}") {
            val project = call.parameters["project"]!!
            val type = call.parameters["type"]!!
            val version = call.parameters["version"]!!

            val item = if(version == "latest") AssetDatabase.getLatest(project, type)!!
            else AssetDatabase.get(project, type, version)!!

            call.respond(item)
        }
        get("/{project}/{type}/{version}/dl") {
            val project = call.parameters["project"]!!
            val type = call.parameters["type"]!!
            val version = call.parameters["version"]!!

            val (item, bytes) = if(version == "latest") {
                Pair(
                    AssetDatabase.getLatest(project, type)!!,
                    AssetDatabase.getLatestBytes(project, type)!!
                )
            } else {
                Pair(
                    AssetDatabase.get(project, type, version)!!,
                    AssetDatabase.getBytes(project, type, version)!!
                )
            }

            call.response.header(
                name = HttpHeaders.ContentDisposition,
                value = ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, item.file).toString()
            )
            call.respondBytes(bytes)
        }
    }
}