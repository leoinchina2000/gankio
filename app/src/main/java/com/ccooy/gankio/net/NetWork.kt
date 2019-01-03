package com.ccooy.gankio.net

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.net.api.GankApi

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络操作类
 *
 */

object NetWork {

    private var gankApi: GankApi? = null
    private val okHttpClient = InternalOkHttpClient.getOkhttpClient()

    fun getGankApi(): GankApi {
        if (gankApi == null) {
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(GlobalConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            gankApi = retrofit.create(GankApi::class.java)
        }
        return gankApi!!
    }
}
