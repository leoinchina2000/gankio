package com.ccooy.gankio.module.home

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.View
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.module.base.BaseFragment
import com.ccooy.gankio.module.search.SearchActivity
import com.ccooy.gankio.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment(), IHomeView {
    private lateinit var mRecyclerView: RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private val mHomePresenter: IHomePresenter by lazy {
        HomePresenter(this)
    }
    override val contentViewLayoutID: Int
        get() = R.layout.fragment_home

    override fun init(view: View) {
        mRecyclerView = view.recyclerView
        mSwipeRefreshLayout = view.swipe_refresh_layout

        view.search_bar.setOnClickListener { toSearchActivity() }
        mHomePresenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomePresenter.unSubscribe()
    }

    companion object {

        fun newInstance(tag: String): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    /**
     * 跳转到搜索
     */
    private fun toSearchActivity() {
        val intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun getTodayItemsFail(failMessage: String) {
        if (userVisibleHint) {
            Toasty.error(App.instance, failMessage).show()
        }
    }

    override fun setTodayItems(data: List<ResultsBean>) {
        Log.d("HomeFragment", data.toString())
    }

    override fun showLoading() {
        spin_kit.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        spin_kit.visibility = View.GONE
    }
}