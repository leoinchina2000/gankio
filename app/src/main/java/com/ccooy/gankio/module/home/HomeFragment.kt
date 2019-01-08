package com.ccooy.gankio.module.home

import android.content.Intent
import android.view.View
import butterknife.OnClick
import com.ccooy.gankio.R
import com.ccooy.gankio.module.base.BaseFragment
import com.ccooy.gankio.module.search.SearchActivity

class HomeFragment : BaseFragment() {

    @OnClick(R.id.search_bar)
    fun click(){
        toSearchActivity()
    }

    override val contentViewLayoutID: Int
        get() = R.layout.fragment_home

    override fun init(view: View) {

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
     * 跳转到主页面
     */
    private fun toSearchActivity() {
        val intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
//        activity?.overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
    }
}