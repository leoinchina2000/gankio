package com.ccooy.gankio.module.search

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView
import com.ccooy.gankio.model.ResultsBean

interface ISearchView : BaseView {

    var searchWords: String

    fun getSearchItemsFail(failMessage: String)

    fun setSearchItems(data: List<ResultsBean>)

    fun addSearchItems(data: List<ResultsBean>)

    fun showSwipeLoading()

    fun hideSwipeLoading()

    fun setLoading()

    fun setNoMore()
}

interface ISearchPresenter : BasePresenter {

    fun getSearchItems(isRefresh: Boolean)
}