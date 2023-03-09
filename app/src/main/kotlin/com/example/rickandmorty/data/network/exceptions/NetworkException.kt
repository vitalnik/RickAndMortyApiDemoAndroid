package com.example.rickandmorty.data.network.exceptions

class NetworkException(val errorCode: Int, val description: String) : Throwable()