package com.example.rickandmorty.data.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.cache.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor() {
    var client: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
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
    private const val logTag = "KTOR"

    override fun log(message: String) {
        longLog(message)
    }

    fun longLog(str: String) {
        if (str.length > 200) {
            Timber.i(str.substring(0, 200))
            longLog(str.substring(200))
        } else Timber.i(str)
    }
}