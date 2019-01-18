package com.ccooy.gankio.module.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseFragment : Fragment() {

    private lateinit var unbinder: Unbinder

    /**
     * 获取布局ID
     */
    protected abstract val contentViewLayoutID: Int

    /**
     * 界面初始化
     */
    protected abstract fun init(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (contentViewLayoutID != 0) {
            inflater.inflate(contentViewLayoutID, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        init(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
