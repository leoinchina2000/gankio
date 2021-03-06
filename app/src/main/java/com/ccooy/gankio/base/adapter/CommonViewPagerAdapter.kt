package com.ccooy.gankio.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * ViewPager通用
 *
 */

class CommonViewPagerAdapter(fm: FragmentManager, private val title: Array<String>) : FragmentPagerAdapter(fm) {
    private val mFragments = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}
