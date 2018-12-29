package com.ccooy.gankio.module.web

import android.app.Activity
import com.ccooy.gankio.module.web.WebContract.IWebPresenter
import com.ccooy.gankio.module.web.WebContract.IWebView

/**
 * Author:
 *
 * Date: 2017-04-14  14:41
 */

class WebPresenter(private val mWebView: IWebView) : IWebPresenter {
    override lateinit var gankUrl: String
    private val mActivity: Activity by lazy {
        mWebView.webViewContext
    }


    override fun subscribe() {
        val intent = mActivity.intent
        mWebView.setGankTitle(intent.getStringExtra(WebViewActivity.GANK_TITLE))
        mWebView.initWebView()
        gankUrl = intent.getStringExtra(WebViewActivity.GANK_URL)
        mWebView.loadGankUrl(gankUrl)
    }

    override fun unSubscribe() {

    }
}
