package com.ccooy.gankio.module.web

import android.app.Activity
import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView

/**
 * WebContract
 *
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
