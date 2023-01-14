package com.example.rickandmorty.app.extensions

fun List<String>.getIds(): String = this.joinToString(separator = ",") {
    it.substring(it.lastIndexOf("/") + 1)
}

fun String.getIdFromUrl(): Int = substring(lastIndexOf("/") + 1).toIntOrNull() ?: 0
