package com.ccooy.gankio.module.search

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView
import com.ccooy.gankio.model.QueryBean
import com.ccooy.gankio.model.ResultsBean

interface ISearchView : BaseView {

    var searchWords: String

    var searchCategory: String

    fun getSearchItemsFail(failMessage: String)

    fun setSearchItems(data: List<QueryBean>)

    fun addSearchItems(data: List<QueryBean>)

    fun showSwipeLoading()

    fun hideSwipeLoading()

    fun setLoading()

    fun setNoMore()
}

interface ISearchPresenter : BasePresenter {

    fun getSearchItems(isRefresh: Boolean)
}