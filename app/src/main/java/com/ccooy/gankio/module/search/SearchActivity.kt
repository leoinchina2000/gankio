package com.ccooy.gankio.module.search

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import butterknife.OnClick
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.module.base.BaseActivity
import com.ccooy.gankio.widget.RecyclerViewWithFooter.OnLoadMoreListener
import com.ccooy.gankio.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), ISearchView, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private lateinit var mRecyclerView: RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override var searchWords = ""

    private val mAdapter: SearchRecyclerAdapter by lazy {
        SearchRecyclerAdapter(this)
    }
    private val mISearchPresenter: ISearchPresenter by lazy {
        SearchPresenter(this)
    }

    override val contentViewLayoutID: Int
        get() = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {

        mRecyclerView = recyclerView
        mSwipeRefreshLayout = swipe_refresh_layout

        mSwipeRefreshLayout.setOnRefreshListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
//        mRecyclerView.addItemDecoration(RecyclerViewDivider(activity!!, LinearLayoutManager.HORIZONTAL))
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setOnLoadMoreListener(this)
        mRecyclerView.setEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        mISearchPresenter.unSubscribe()
    }

    override fun onRefresh() {
        mISearchPresenter.getSearchItems(true)
    }

    override fun onLoadMore() {
        mISearchPresenter.getSearchItems(false)
    }

    override fun getSearchItemsFail(failMessage: String) {
        Toasty.error(App.instance, failMessage).show()
    }

    override fun setSearchItems(data: List<ResultsBean>) {
        mAdapter.setData(data)
    }

    override fun addSearchItems(data: List<ResultsBean>) {
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

    @OnClick(R.id.back_button)
    fun back() {
        finish()
    }
}