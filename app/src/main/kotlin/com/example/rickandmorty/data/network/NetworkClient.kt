package com.example.rickandmorty.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor() {
    var client: HttpClient = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = CustomAndroidHttpLogger
            level = LogLevel.ALL
        }

        install(HttpCache)

        install(Logging) {
            logger = CustomAndroidHttpLogger
            level = LogLevel.INFO
        }

        // uncomment when using proxy, ex. Charles
//        engine {
//            proxy = ProxyBuilder.http("http://192.168.171.20:8888/")
//        }
    }

    companion object {
        const val BASE_URL: String = "https://rickandmortyapi.com/api"
    }

}

private object CustomAndroidHttpLogger : Logger {
    private const val maxLogStrLength = 200

    override fun log(message: String) {
        longLog(message)
    }

    fun longLog(str: String) {
        if (str.length > maxLogStrLength) {
            Timber.i(str.substring(0, maxLogStrLength))
            longLog(str.substring(maxLogStrLength))
        } else Timber.i(str)
    }
}
