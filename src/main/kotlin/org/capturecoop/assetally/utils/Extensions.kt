package org.capturecoop.assetally.utils

import io.ktor.http.content.*

class ReadMultiPart(private val list: List<PartData>) {
    fun getItem(name: String) = list.firstOrNull { it.name == name }
    fun getFormItem(name: String) = getItem(name) as PartData.FormItem?
    fun getFileItem(name: String) = getItem(name) as PartData.FileItem?
}

suspend fun MultiPartData.read() = ReadMultiPart(readAllParts())