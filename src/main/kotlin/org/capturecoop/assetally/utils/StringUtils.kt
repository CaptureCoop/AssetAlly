package org.capturecoop.assetally.utils

object StringUtils {
    private val lettersLowercase = ('a'..'z').toList()
    private val lettersUppercase = ('A'..'Z').toList()
    private val numbers = ('1'..'9').toList()
    private val all = ArrayList<Char>().apply {
        addAll(lettersLowercase)
        addAll(lettersUppercase)
        addAll(numbers)
    }

    fun randomString(length: Int = 40) = (1..length).map { all.random() }.joinToString("")
}