package org.capturecoop.assetally

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.ok(text: String = "") = respondText(status = HttpStatusCode.OK, text = text)
suspend fun ApplicationCall.badRequest(text: String = "") = respondText(status = HttpStatusCode.BadRequest, text = text)
suspend fun ApplicationCall.notFound(text: String = "") = respondText(status = HttpStatusCode.NotFound, text = text)

suspend inline fun <reified T : Any> ApplicationCall.receiveNullable(): T? {
    return try {
        receive<T>()
    } catch (e: Exception) {
        null
    }
}