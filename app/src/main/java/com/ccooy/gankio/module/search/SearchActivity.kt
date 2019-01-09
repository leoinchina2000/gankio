package com.ccooy.gankio.module.search

import android.os.Bundle
import butterknife.OnClick
import com.ccooy.gankio.R
import com.ccooy.gankio.module.base.BaseActivity

class SearchActivity: BaseActivity() {

    override val contentViewLayoutID: Int
        get() = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {


    }

    @OnClick(R.id.back_button)
    fun back(){
        finish()
    }
}