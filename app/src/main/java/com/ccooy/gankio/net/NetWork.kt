package com.ccooy.gankio.net

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.net.api.GankApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络操作类
 *
 */

object NetWork {

    private var gankApi: GankApi? = null
    private val okHttpClient = OkHttpClient()

    fun getGankApi(): GankApi {
        if (gankApi == null) {
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(GlobalConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            gankApi = retrofit.create(GankApi::class.java)
        }
        return gankApi!!
    }
}
