package com.ccooy.gankio.module.home

import android.content.Intent
import android.view.View
import com.ccooy.gankio.R
import com.ccooy.gankio.module.base.BaseFragment
import com.ccooy.gankio.module.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment() {

    override val contentViewLayoutID: Int
        get() = R.layout.fragment_home

    override fun init(view: View) {
        view.search_bar.setOnClickListener { toSearchActivity() }
    }

    override fun onDestroy() {
        super.onDestroy()
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
//        activity?.overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
    }
}