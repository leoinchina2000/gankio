package com.ccooy.gankio.module.test

import com.ccooy.gankio.module.base.BaseFragment
import android.view.View
import com.ccooy.gankio.R

class TestFragment : BaseFragment() {


    override val contentViewLayoutID: Int
        get() = R.layout.fragment_test

    override fun init(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        fun newInstance(tag: String): TestFragment {
            val testFragment = TestFragment()
            return testFragment
        }
    }
}