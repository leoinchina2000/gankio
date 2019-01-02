package com.ccooy.gankio.module.home

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView

interface HomeContract {
    interface IHomeView : BaseView {
        fun showBannerFail(failMessage: String)

        fun setBanner(imgUrls: List<String>)

    }

    interface IHomePresenter : BasePresenter {
        /**
         * 获取Banner轮播图数据
         */
        fun getBannerData()

    }
}
