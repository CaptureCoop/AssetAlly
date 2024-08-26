package org.capturecoop.assetally.database

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseItem (
    val project: String,
    val type: String,
    val version: Version,
    val file: String
)