package com.ccooy.gankio.module.home

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView
import com.ccooy.gankio.model.ResultsBean

interface IHomeView : BaseView {

    fun getTodayItemsFail(failMessage: String)

    fun setTodayItems(data: List<ResultsBean>)

    fun showLoading()

    fun hideLoading()
}

interface IHomePresenter : BasePresenter {

    fun getTodayItems()
}
