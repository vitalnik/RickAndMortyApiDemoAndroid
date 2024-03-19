package com.example.rickandmorty.app.utils

sealed class ViewState<out T : Any> {

    data object Initial : ViewState<Nothing>()

    data object Loading : ViewState<Nothing>()

    data object Empty : ViewState<Nothing>()

    data class Error(val errorCode: String) : ViewState<Nothing>()

    data class Populated<T : Any>(private val value: T) : ViewState<T>() {
        operator fun invoke(): T = value
    }
}

fun <T : Any, R : Any?> ViewState<T>?.withState(block: (T) -> R?) =
    this?.let {
        when (it) {
            is ViewState.Populated -> {
                block(it())
            }
            else -> null
        }
    }
