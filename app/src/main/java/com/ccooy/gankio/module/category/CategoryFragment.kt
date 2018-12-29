package com.ccooy.gankio.module.category

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.base.BaseFragment
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.widget.RecyclerViewDivider
import com.ccooy.gankio.widget.RecyclerViewWithFooter.OnLoadMoreListener
import com.ccooy.gankio.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_category.view.*

/**
 * 主页轮播下面的Fragment
 *
 *
 * Author:
 *
 * Date: 2017-04-14  9:46
 */

class CategoryFragment : BaseFragment(), ICategoryView, OnRefreshListener, OnLoadMoreListener {
    private lateinit var mRecyclerView: RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override val categoryName: String by lazy {
        arguments?.getString(CATEGORY_NAME) ?: ""
    }
    private val mAdapter: CategoryRecyclerAdapter by lazy {
        CategoryRecyclerAdapter(context!!)
    }
    private val mICategoryPresenter: ICategoryPresenter by lazy {
        CategoryPresenter(this)
    }


    override val contentViewLayoutID: Int
        get() = R.layout.fragment_category

    override fun init(view: View) {
        mRecyclerView = view.recyclerView
        mSwipeRefreshLayout = view.swipe_refresh_layout

        mSwipeRefreshLayout.setOnRefreshListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.addItemDecoration(RecyclerViewDivider(activity!!, LinearLayoutManager.HORIZONTAL))
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setOnLoadMoreListener(this)
        mRecyclerView.setEmpty()

        mICategoryPresenter.subscribe()

    }

    override fun onDestroy() {
        super.onDestroy()
        mICategoryPresenter.unSubscribe()
    }

    override fun onRefresh() {
        mICategoryPresenter.getCategoryItems(true)
    }

    override fun onLoadMore() {
        mICategoryPresenter.getCategoryItems(false)
    }

    override fun getCategoryItemsFail(failMessage: String) {
        if (userVisibleHint) {
            Toasty.error(App.instance, failMessage).show()
        }
    }

    override fun setCategoryItems(data: List<ResultsBean>) {
        mAdapter.setData(data)
    }

    override fun addCategoryItems(data: List<ResultsBean>) {
        mAdapter.addData(data)

    }

    override fun showSwipeLoading() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun hideSwipeLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun setLoading() {
        mRecyclerView.setLoading()
    }

    override fun setNoMore() {
        mRecyclerView.setEnd("没有更多数据")
    }

    companion object {

        const val CATEGORY_NAME = "com.ccooy.gankio.module.category.CategoryFragment.CATEGORY_NAME"

        fun newInstance(mCategoryName: String): CategoryFragment {
            val categoryFragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString(CATEGORY_NAME, mCategoryName)
            categoryFragment.arguments = bundle
            return categoryFragment
        }
    }

}
