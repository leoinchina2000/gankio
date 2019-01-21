package com.ccooy.gankio.module.article

import com.ccooy.gankio.base.BasePresenter
import com.ccooy.gankio.base.BaseView
import com.ccooy.gankio.model.XianDuCategoryBean
import com.ccooy.gankio.model.XianDuDataBean
import com.ccooy.gankio.model.XianDuSubCategoryBean

interface IArticleView : BaseView {

    fun getXianDuCategoryItemsFail(failMessage: String)

    fun getXianDuSubCategoryItemsFail(failMessage: String)

    fun getXianDuCategoryDataItemsFail(failMessage: String)

    fun setXianDuCategoryItems(data: List<XianDuCategoryBean>)

    fun setXianDuSubCategoryItems(data: List<XianDuSubCategoryBean>)

    fun setXianDuDataItems(data: List<XianDuDataBean>)

    fun addXianDuDataItems(data: List<XianDuDataBean>)

    fun showSwipeLoading()

    fun hideSwipeLoading()

    fun setLoading()

    fun setNoMore()
}

interface IArticlePresenter : BasePresenter {

    fun getCategories()

    fun getSubCategory(category: String)

    fun getData(isRefresh: Boolean, subCategoryId: String)
}