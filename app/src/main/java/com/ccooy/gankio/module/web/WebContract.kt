package com.ccooy.gankio.module.web

import android.app.Activity

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView

/**
 * WebContract
 *
 * Author:
 *
 * Date: 2017-04-14  14:38
 */

interface WebContract {

    interface IWebView : BaseView {
        val webViewContext: Activity

        fun setGankTitle(title: String)

        fun loadGankUrl(url: String)

        fun initWebView()
    }

    interface IWebPresenter : BasePresenter {
        val gankUrl: String
    }
}
