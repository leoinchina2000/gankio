package com.ccooy.gankio.net.api

import com.ccooy.gankio.model.CategoryResult

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 *
 * 代码家的gank.io接口
 *
 * Author:
 *
 * Date: 2017-04-14  11:23
 */

interface GankApi {

    /**
     * 根据category获取Android、iOS等干货数据
     * @param category  类别
     * @param count     条目数目
     * @param page      页数
     */
    @GET("data/{category}/{count}/{page}")
    fun getCategoryData(@Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Observable<CategoryResult>
}
