package com.ccooy.gankio.module.category

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView
import com.ccooy.gankio.model.ResultsBean

interface ICategoryView : BaseView {

    val categoryName: String

    fun getCategoryItemsFail(failMessage: String)

    fun setCategoryItems(data: List<ResultsBean>)

    fun addCategoryItems(data: List<ResultsBean>)

    fun showSwipeLoading()

    fun hideSwipeLoading()

    fun setLoading()

    fun setNoMore()
}

interface ICategoryPresenter : BasePresenter {

    fun getCategoryItems(isRefresh: Boolean)
}
