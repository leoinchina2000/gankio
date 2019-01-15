package com.ccooy.gankio.module.article

import com.ccooy.gankio.module.base.BaseFragment
import android.view.View
import com.ccooy.gankio.R

class ArticleFragment : BaseFragment() {

    override val contentViewLayoutID: Int
        get() = R.layout.fragment_article

    override fun init(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        fun newInstance(tag: String): ArticleFragment {
            val fragment = ArticleFragment()
            return fragment
        }
    }
}