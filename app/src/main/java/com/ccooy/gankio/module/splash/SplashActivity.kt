package com.ccooy.gankio.module.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.ccooy.gankio.R
import com.ccooy.gankio.base.BaseActivity
import com.ccooy.gankio.config.ConstantsImageUrl
import com.ccooy.gankio.module.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

/**
 * 闪屏页面
 *
 */

class SplashActivity : BaseActivity() {
    private var isIn: Boolean = false

    @BindView(R.id.splash_tv_jump)
    private lateinit var mTvJump: TextView

    override val contentViewLayoutID: Int
        get() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {


        val i = Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.size)
        // 先显示默认图

        splash_iv_defult_pic.setImageDrawable(resources.getDrawable(R.drawable.img_transition_default))
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(splash_iv_pic)
        Handler().postDelayed({ splash_iv_defult_pic.visibility = View.GONE }, 1500)

        Handler().postDelayed({ toMainActivity() }, 3500)
        splash_tv_jump.setOnClickListener { toMainActivity() }
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    /**
     * 跳转到主页面
     */
    private fun toMainActivity() {
        if (isIn) {
            return
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
        isIn = true
    }

}
