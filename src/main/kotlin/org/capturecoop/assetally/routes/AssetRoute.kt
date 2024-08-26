package org.capturecoop.assetally.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.capturecoop.assetally.AssetDatabase

//http://127.0.0.1/asset/SnipSniper/dev/1.0.0
fun Routing.assetRoute() {
    get("/asset/{project}") {
        val project = call.parameters["project"]!!
        call.respond(AssetDatabase.get(project))
    }
    get("/asset/{project}/{type}") {
        val project = call.parameters["project"]!!
        val type = call.parameters["type"]!!
        call.respond(AssetDatabase.get(project, type))
    }
    get("/asset/{project}/{type}/{version}") {
        val project = call.parameters["project"]!!
        val type = call.parameters["type"]!!
        val version = call.parameters["version"]!!
        val found = AssetDatabase.get(project, type, version)!!
        call.response.header(HttpHeaders.ContentDisposition, """
            attachment; filename="${found.file}"
        """.trimIndent())
        call.respondBytes(bytes = AssetDatabase.getBytes(project, type, version)!!)
    }
}