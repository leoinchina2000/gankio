package com.ccooy.gankio.module.splash

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ccooy.gankio.R
import com.ccooy.gankio.config.ConstantsImageUrl
import com.ccooy.gankio.module.base.BaseActivity
import com.ccooy.gankio.module.home.MainActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.Random
import java.util.concurrent.TimeUnit

/**
 * 闪屏页面
 *
 */

class SplashActivity : BaseActivity() {
    private var isIn: Boolean = false

    override val contentViewLayoutID: Int
        get() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {


        val i = Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.size)
        // 先显示默认图

        Glide.with(this)
            .load(ConstantsImageUrl.TRANSITION_URLS[i])
            .apply(RequestOptions().placeholder(R.drawable.img_transition_default).error(R.drawable.img_transition_default))
            .into(splash_iv_pic)

        val observer: Observer<Long> = object : Observer<Long> {
            override fun onComplete() {
                toMainActivity()
            }

            override fun onNext(aLong: Long) {
                splash_tv_jump.text = getString(R.string.jump) + "(" + (2 - aLong) + "s)"
            }

            override fun onError(e: Throwable) {
            }

            override fun onSubscribe(d: Disposable) {
            }
        }
        Observable.interval(1, TimeUnit.SECONDS).take(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe(observer)

        splash_tv_jump.setOnClickListener { toMainActivity() }
    }

    /**
     * 跳转到主页面
     */
    private fun toMainActivity() {
        if (isIn) {
            return
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
        isIn = true
    }

}
