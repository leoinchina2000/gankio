package com.ccooy.gankio.module.fuli

import com.ccooy.gankio.module.base.BaseFragment
import android.view.View
import com.ccooy.gankio.R

class FuliFragment : BaseFragment() {


    override val contentViewLayoutID: Int
        get() = R.layout.fragment_fuli

    override fun init(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        fun newInstance(tag: String): FuliFragment {
            val fuliFragment = FuliFragment()
            return fuliFragment
        }
    }
}