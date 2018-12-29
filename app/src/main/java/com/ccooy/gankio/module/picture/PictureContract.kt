package com.ccooy.gankio.module.picture

import android.graphics.Bitmap

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView

/**
 * Author:
 *
 * Date: 2017-04-24  14:30
 */

interface PictureContract {

    interface PictureView : BaseView {
        fun hideProgress()
        fun showProgress()
    }

    interface Presenter : BasePresenter {
        fun saveGirl(url: String, bitmap: Bitmap, title: String)
    }
}
