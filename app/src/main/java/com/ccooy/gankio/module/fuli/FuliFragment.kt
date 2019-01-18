package com.ccooy.gankio.module.fuli

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.ccooy.gankio.module.base.BaseFragment
import android.view.View
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.widget.RecyclerViewDivider
import com.ccooy.gankio.widget.RecyclerViewWithFooter.OnLoadMoreListener
import com.ccooy.gankio.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_fuli.view.*

class FuliFragment : BaseFragment() , FuliContract.IFuliView, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private lateinit var mRecyclerView: RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override val fuliName: String = GlobalConfig.CATEGORY_NAME_FULI

    private val mAdapter: FuliRecyclerAdapter by lazy {
        FuliRecyclerAdapter(context!!)
    }
    private val mICategoryPresenter: FuliPresenter by lazy {
        FuliPresenter(this)
    }


    override val contentViewLayoutID: Int
        get() = R.layout.fragment_category

    override fun init(view: View) {
        mRecyclerView = view.recyclerView
        mSwipeRefreshLayout = view.swipe_refresh_layout

        mSwipeRefreshLayout.setOnRefreshListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
//        mRecyclerView.addItemDecoration(RecyclerViewDivider(activity!!, LinearLayoutManager.HORIZONTAL))
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
        mICategoryPresenter.getFuliData(true)
    }

    override fun onLoadMore() {
        mICategoryPresenter.getFuliData(false)
    }

    override fun getFuliItemsFail(failMessage: String) {
        if (userVisibleHint) {
            Toasty.error(App.instance, failMessage).show()
        }
    }

    override fun setFuliItems(data: List<ResultsBean>) {
        mAdapter.setData(data)
    }

    override fun addFuliItems(data: List<ResultsBean>) {
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

        fun newInstance(tag: String): FuliFragment {
            val fuliFragment = FuliFragment()
            return fuliFragment
        }
    }
}