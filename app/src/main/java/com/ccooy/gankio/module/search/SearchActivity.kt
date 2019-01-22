package com.ccooy.gankio.module.search

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.model.QueryBean
import com.ccooy.gankio.module.base.BaseActivity
import com.ccooy.gankio.widget.RecyclerViewWithFooter.OnLoadMoreListener
import com.ccooy.gankio.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), ISearchView, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private lateinit var mRecyclerView: RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override var searchWords = ""
    override var searchCategory = ""

    private val mAdapter: SearchRecyclerAdapter by lazy {
        SearchRecyclerAdapter(this)
    }
    private val mISearchPresenter: ISearchPresenter by lazy {
        SearchPresenter(this)
    }

    override val contentViewLayoutID: Int
        get() = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {

        back_button.setOnClickListener { finish() }
        search_button.setOnClickListener {
            searchWords = search_edit.text.toString()
            if (searchWords.isNotEmpty()) {
                mISearchPresenter.getSearchItems(true)
            } else {
                Toasty.error(App.instance, "请输入搜索关键字").show()
            }
        }

        mRecyclerView = recyclerView
        mSwipeRefreshLayout = swipe_refresh_layout

        mSwipeRefreshLayout.setOnRefreshListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
//        mRecyclerView.addItemDecoration(RecyclerViewDivider(activity!!, LinearLayoutManager.HORIZONTAL))
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setOnLoadMoreListener(this)
        mRecyclerView.setEmpty()

        search_edit.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                searchWords = search_edit.text.toString()
                mISearchPresenter.getSearchItems(true)
            }
            false
        }

        category_radio_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.search_all -> { searchCategory = resources.getString(R.string.search_category_all) }
                R.id.search_android -> { searchCategory = resources.getString(R.string.search_category_android) }
                R.id.search_ios -> { searchCategory = resources.getString(R.string.search_category_ios) }
                R.id.search_front -> { searchCategory = resources.getString(R.string.search_category_front) }
                R.id.search_app -> { searchCategory = resources.getString(R.string.search_category_app) }
                R.id.search_video -> { searchCategory = resources.getString(R.string.search_category_video) }
                R.id.search_fuli -> { searchCategory = resources.getString(R.string.search_category_fuli) }
                R.id.search_recommend -> { searchCategory = resources.getString(R.string.search_category_recommend) }
                R.id.search_resource -> { searchCategory = resources.getString(R.string.search_category_resource) }
            }
        }

        search_all.isChecked = true
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

    override fun setSearchItems(data: List<QueryBean>) {
        mAdapter.setData(data)
    }

    override fun addSearchItems(data: List<QueryBean>) {
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
}