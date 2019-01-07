package com.ccooy.gankio.module.picture

import android.graphics.Bitmap
import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView

interface PictureContract {

    interface PictureView : BaseView {
        fun hideProgress()
        fun showProgress()
    }

    interface Presenter : BasePresenter {
        fun saveGirl(url: String, bitmap: Bitmap, title: String)
    }
}
