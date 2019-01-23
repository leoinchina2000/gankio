package com.ccooy.gankio.net

import com.ccooy.gankio.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class InternalOkHttpClient {
    companion object {

        fun getOkhttpClient(): OkHttpClient {
            var okHttpClient: OkHttpClient? = null

            var xtm: X509TrustManager = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance("SSL")

                sslContext.init(null, arrayOf<TrustManager>(xtm), SecureRandom())

            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }

            if (okHttpClient == null) {
                okHttpClient = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .hostnameVerifier { hostname, session ->
                        true
                    }
                    .sslSocketFactory(sslContext!!.socketFactory, xtm)
                    .build()

                if (BuildConfig.DEBUG) {//printf logs while  debug
                    okHttpClient = okHttpClient?.newBuilder()
                        ?.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))?.build()
                }
            }
            return okHttpClient!!
        }

    }
}