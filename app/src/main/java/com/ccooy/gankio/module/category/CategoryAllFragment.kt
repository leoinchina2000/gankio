package com.ccooy.gankio.module.category

import android.support.v4.view.ViewPager
import com.ccooy.gankio.R
import android.view.View
import com.ccooy.gankio.module.base.adapter.CommonViewPagerAdapter
import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.module.base.BaseFragment
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import kotlinx.android.synthetic.main.fragment_home.view.*

class CategoryAllFragment : BaseFragment() {

    private lateinit var mTabLayout: DachshundTabLayout
    private lateinit var mViewPager: ViewPager

    override val contentViewLayoutID: Int
        get() = R.layout.fragment_home

    override fun init(view: View) {
        mTabLayout = view.main_tab
        mViewPager = view.main_vp

        val titles = arrayOf(
            GlobalConfig.CATEGORY_NAME_ALL,
            GlobalConfig.CATEGORY_NAME_ANDROID,
            GlobalConfig.CATEGORY_NAME_IOS,
            GlobalConfig.CATEGORY_NAME_APP,
            GlobalConfig.CATEGORY_NAME_FRONT_END,
            GlobalConfig.CATEGORY_NAME_RECOMMEND,
            GlobalConfig.CATEGORY_NAME_RESOURCE,
            GlobalConfig.CATEGORY_NAME_FRONT_VIDEO
        )

        val infoPagerAdapter = CommonViewPagerAdapter(childFragmentManager, titles)
        // All
        val allFragment = CategoryFragment.newInstance(titles[0])
        // Android
        val androidFragment = CategoryFragment.newInstance(titles[1])
        // iOS
        val iOSFragment = CategoryFragment.newInstance(titles[2])
        // App
        val appFragment = CategoryFragment.newInstance(titles[3])
        // 前端
        val frontFragment = CategoryFragment.newInstance(titles[4])
        // 瞎推荐
        val referenceFragment = CategoryFragment.newInstance(titles[5])
        // 拓展资源
        val resFragment = CategoryFragment.newInstance(titles[6])
        // 休息视频
        val videoFragment = CategoryFragment.newInstance(titles[7])

        infoPagerAdapter.addFragment(allFragment)
        infoPagerAdapter.addFragment(androidFragment)
        infoPagerAdapter.addFragment(iOSFragment)
        infoPagerAdapter.addFragment(appFragment)
        infoPagerAdapter.addFragment(frontFragment)
        infoPagerAdapter.addFragment(referenceFragment)
        infoPagerAdapter.addFragment(resFragment)
        infoPagerAdapter.addFragment(videoFragment)

        mViewPager.adapter = infoPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = 1
        mViewPager.offscreenPageLimit = 1
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        fun newInstance(tag: String): CategoryAllFragment {
            val homeFragment = CategoryAllFragment()
            return homeFragment
        }
    }
}