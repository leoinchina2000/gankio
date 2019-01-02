package com.ccooy.gankio.base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import butterknife.ButterKnife
import com.ccooy.gankio.R

/**
 * Dialog父类
 *
 */

abstract class BaseDialog
@JvmOverloads constructor(
        private val mContext: Context,
        layoutId: Int, styleId: Int = R.style.MyDialog)
    : Dialog(mContext, styleId) {

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layoutId, null)
        this.setContentView(view)
        ButterKnife.bind(this)
    }
}
