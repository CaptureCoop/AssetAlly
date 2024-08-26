package org.capturecoop.assetally.utils

import io.ktor.http.content.*

class ReadMultiPart(private val list: List<PartData>) {
    fun getFormItem(name: String) = list.firstOrNull { it.name == name } as PartData.FormItem?
    fun getFileItem(name: String) = list.firstOrNull { it.name == name } as PartData.FileItem?
}

suspend fun MultiPartData.read() = ReadMultiPart(readAllParts())