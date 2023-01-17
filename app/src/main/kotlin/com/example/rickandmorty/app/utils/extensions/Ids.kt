package com.example.rickandmorty.app.utils.extensions

fun List<String>.getIds(): String = this.joinToString(separator = ",") {
    it.substring(it.lastIndexOf("/") + 1)
}

fun List<String>.idsList(): List<Int> = this.map {
    it.substring(it.lastIndexOf("/") + 1).toInt()
}.toList()

fun String.getIdFromUrl(): Int = substring(lastIndexOf("/") + 1).toIntOrNull() ?: 0

fun List<Int>.idsQuery(): String = this.joinToString(separator = ",")