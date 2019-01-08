package com.ccooy.gankio.module.fuli

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView
import com.ccooy.gankio.model.ResultsBean

interface FuliContract {

    interface IFuliView : BaseView {
        val fuliName: String

        fun getFuliItemsFail(failMessage: String)

        fun setFuliItems(data: List<ResultsBean>)

        fun addFuliItems(data: List<ResultsBean>)

        fun showSwipeLoading()

        fun hideSwipeLoading()

        fun setLoading()

        fun setNoMore()

    }

    interface IFuliPresenter : BasePresenter {
        fun getFuliData(isRefresh: Boolean)
    }
}