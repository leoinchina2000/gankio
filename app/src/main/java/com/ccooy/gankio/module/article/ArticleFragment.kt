package com.ccooy.gankio.module.article

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.ccooy.gankio.module.base.BaseFragment
import android.view.View
import android.widget.TextView
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.model.XianDuCategoryBean
import com.ccooy.gankio.model.XianDuDataBean
import com.ccooy.gankio.model.XianDuSubCategoryBean
import com.ccooy.gankio.module.base.adapter.ListenerWithPosition
import com.ccooy.gankio.widget.RecyclerViewDivider
import com.ccooy.gankio.widget.RecyclerViewWithFooter.OnLoadMoreListener
import com.ccooy.gankio.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_article.view.*

class ArticleFragment : BaseFragment(), IArticleView, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private lateinit var mRecyclerViewMain: RecyclerViewWithFooter
    private lateinit var mRecyclerViewSub: RecyclerViewWithFooter
    private lateinit var mRecyclerView: RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mMainTab: TextView
    private lateinit var mSubTab: TextView
    private lateinit var mArrowTab: TextView

    enum class XianDuMode {
        GETMAIN, GETSUB, GETDATA
    }

    private var mode: XianDuMode = XianDuMode.GETMAIN

    var categoryEnName: String = ""

    var subCategoryId: String = ""

    private val mAdapter: CategoriesRecyclerAdapter by lazy {
        CategoriesRecyclerAdapter(context!!)
    }
    private val mSubAdapter: SubCategoryRecyclerAdapter by lazy {
        SubCategoryRecyclerAdapter(context!!)
    }
    private val mICategoryPresenter: IArticlePresenter by lazy {
        ArticlePresenter(this)
    }

    override val contentViewLayoutID: Int
        get() = R.layout.fragment_article

    override fun init(view: View) {

        mRecyclerViewMain = view.recyclerViewMain
        mRecyclerViewSub = view.recyclerViewSub
        mRecyclerView = view.recyclerView
        mSwipeRefreshLayout = view.swipe_refresh_layout
        mArrowTab = view.arrow
        mMainTab = view.main_category_tab
        mSubTab = view.sub_category_tab
        mMainTab.setOnClickListener { switchMode(XianDuMode.GETMAIN) }
        mSubTab.setOnClickListener { switchMode(XianDuMode.GETSUB) }

        initSwipeRefreshLayout()
        initMainRecyclerView()
        initSubRecyclerView()

        mICategoryPresenter.subscribe()
    }

    fun initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    fun initMainRecyclerView(){
        mAdapter.setOnItemClickListener(ListenerWithPosition.OnClickWithPositionListener { v, position, holder ->
            categoryEnName = mAdapter.getItem(position).en_name
            mMainTab.text = mAdapter.getItem(position).name
            switchMode(XianDuMode.GETSUB)
        })
        mRecyclerViewMain.layoutManager = LinearLayoutManager(activity)
        mRecyclerViewMain.setOnLoadMoreListener(this)
        mRecyclerViewMain.setEmpty()
        mRecyclerViewMain.adapter=mAdapter
    }

    fun initSubRecyclerView(){
        mSubAdapter.setOnItemClickListener(ListenerWithPosition.OnClickWithPositionListener { v, position, holder ->
            subCategoryId = mSubAdapter.getItem(position).id
            mSubTab.text = mSubAdapter.getItem(position).title
        })
        mRecyclerViewSub.layoutManager = LinearLayoutManager(activity)
        mRecyclerViewSub.setOnLoadMoreListener(this)
        mRecyclerViewSub.setEmpty()
        mRecyclerViewSub.adapter=mSubAdapter
    }

    fun initDataRecyclerView(){
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
//        mRecyclerView.addItemDecoration(RecyclerViewDivider(activity!!, LinearLayoutManager.HORIZONTAL))
        mRecyclerView.setOnLoadMoreListener(this)
        mRecyclerView.setEmpty()
//        mRecyclerView.adapter = mAdapter
    }

    fun switchMode(toMode: XianDuMode) {
        when (toMode) {
            XianDuMode.GETMAIN -> {
                mRecyclerViewMain.visibility=View.VISIBLE
                mRecyclerViewSub.visibility=View.GONE
                mSwipeRefreshLayout.visibility=View.GONE
                mSubTab.visibility = View.INVISIBLE
                mArrowTab.visibility = View.INVISIBLE
                mainTabRefresh()
            }
            XianDuMode.GETSUB -> {
                mRecyclerViewMain.visibility=View.GONE
                mRecyclerViewSub.visibility=View.VISIBLE
                mSwipeRefreshLayout.visibility=View.GONE
                mSubTab.visibility = View.VISIBLE
                mArrowTab.visibility = View.VISIBLE
                subTabRefresh()
            }
            XianDuMode.GETDATA -> mRecyclerView.setEnd("没有更多数据")
            else -> Log.d("闲读", "模式错误")
        }
        mode = toMode
    }

    override fun onDestroy() {
        super.onDestroy()
        mICategoryPresenter.unSubscribe()
    }

    override fun onRefresh() {
        when (mode) {
            XianDuMode.GETMAIN -> switchMode(XianDuMode.GETMAIN)
            XianDuMode.GETSUB -> switchMode(XianDuMode.GETSUB)
            XianDuMode.GETDATA -> mRecyclerView.setEnd("数据刷新没有更多数据")
        }
    }

    override fun onLoadMore() {

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
        when (mode) {
            XianDuMode.GETMAIN -> Log.d("闲读","主分类没有更多数据")
            XianDuMode.GETSUB -> Log.d("闲读","子分类没有更多数据")
            XianDuMode.GETDATA -> mRecyclerView.setEnd("没有更多数据")
        }
    }

    override fun getXianDuCategoryItemsFail(failMessage: String) {
        if (userVisibleHint) {
            Toasty.error(App.instance, failMessage).show()
        }
    }

    override fun getXianDuSubCategoryItemsFail(failMessage: String) {
        if (userVisibleHint) {
            Toasty.error(App.instance, failMessage).show()
        }
    }

    override fun getXianDuCategoryDataItemsFail(failMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setXianDuCategoryItems(data: List<XianDuCategoryBean>) {
        mAdapter.setData(data)
        mAdapter.notifyDataSetChanged()
    }

    override fun setXianDuSubCategoryItems(data: List<XianDuSubCategoryBean>) {
        mSubAdapter.setData(data)
        mSubAdapter.notifyDataSetChanged()
    }

    override fun setXianDuDataItems(data: List<XianDuDataBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addXianDuDataItems(data: List<XianDuDataBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun mainTabRefresh() {
        mMainTab.text = resources.getString(R.string.xiandu_main_tab)
        categoryEnName = ""
        mICategoryPresenter.getCategories()
    }

    fun subTabRefresh() {
        mSubTab.text = resources.getString(R.string.xiandu_sub_tab)
        subCategoryId = ""
        if (categoryEnName.isNotEmpty()) {
            mICategoryPresenter.getSubCategory(categoryEnName)
        }
    }

    companion object {
        fun newInstance(tag: String): ArticleFragment {
            return ArticleFragment()
        }
    }
}