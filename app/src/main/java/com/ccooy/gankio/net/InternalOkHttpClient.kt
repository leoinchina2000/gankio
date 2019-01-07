package com.ccooy.gankio.net

import com.ccooy.gankio.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class InternalOkHttpClient {
    companion object {

        fun getOkhttpClient(): OkHttpClient {
            var okHttpClient: OkHttpClient? = null

            if (okHttpClient == null) {
                okHttpClient = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()

                if (BuildConfig.DEBUG) {//printf logs while  debug
                    okHttpClient = okHttpClient?.newBuilder()?.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))?.build()
                }
            }
            return okHttpClient!!
        }
    }
}