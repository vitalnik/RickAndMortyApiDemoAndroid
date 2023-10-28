package com.example.rickandmorty.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.ProxyConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.Url
import io.ktor.network.tls.TLSConfigBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.ProxySelector
import java.net.URI
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.X509TrustManager

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
        engine {
            proxy = getSystemProxy()
//            https {
//                trustManager = MyTrustManager(this)
//            }
        }
    }

    private fun getSystemProxy(): ProxyConfig? {

        val proxySelector = ProxySelector.getDefault()
        val url = URI(BASE_URL)
        val proxyList = proxySelector.select(url)

        var proxyHost: String? = null
        var proxyPort: Int? = null
        for (proxy in proxyList) {
            val type = proxy.type()
            val address = proxy.address()
            if (type == Proxy.Type.DIRECT) {
                // direct connection
            } else if (type == Proxy.Type.HTTP) {
                proxyHost = (address as InetSocketAddress).hostName
                proxyPort = address.port
            }
        }

        if (proxyHost != null) {
            return ProxyBuilder.http(url = Url("http://$proxyHost:$proxyPort"))
        } else {
            return null
        }
    }

    companion object {
        const val BASE_URL: String = "https://rickandmortyapi.com/api"
    }

}

//class MyTrustManager(private val config: TLSConfigBuilder) : X509TrustManager {
//    private val delegate = config.build().trustManager
//    private val extensions = X509TrustManagerExtensions(delegate)
//
//    override fun checkClientTrusted(
//        p0: Array<out java.security.cert.X509Certificate>?,
//        p1: String?
//    ) {
//        extensions.checkServerTrusted(p0, p1, config.serverName)
//    }
//
//    override fun checkServerTrusted(
//        p0: Array<out java.security.cert.X509Certificate>?,
//        p1: String?
//    ) {
//        delegate.checkServerTrusted(p0, p1)
//
//    }
//
//    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
//        return delegate.acceptedIssuers
//    }
//
//}

class MyTrustManager(private val config: TLSConfigBuilder) : X509TrustManager {
    private val delegate = config.build().trustManager

    override fun checkClientTrusted(certificates: Array<out X509Certificate>?, authType: String?) {}

    override fun checkServerTrusted(certificates: Array<out X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = delegate.acceptedIssuers
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
