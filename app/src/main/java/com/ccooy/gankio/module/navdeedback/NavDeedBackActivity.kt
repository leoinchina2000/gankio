package com.ccooy.gankio.module.navdeedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.OnClick
import com.ccooy.gankio.R
import com.ccooy.gankio.base.BaseActivity
import com.ccooy.gankio.module.web.WebViewActivity
import com.ccooy.gankio.utils.Utils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_nav_deed_back.*


/**
 * 问题反馈页面
 *
 *
 * Author:
 *
 * Date: 2017-04-20  10:10
 */
class NavDeedBackActivity : BaseActivity() {

    lateinit var mToolbar: Toolbar

    override val contentViewLayoutID: Int
        get() = R.layout.activity_nav_deed_back

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar = nav_deed_back_toolbar
        
        mToolbar.setNavigationOnClickListener { finish() }
    }


    @OnClick(R.id.tv_issues, R.id.tv_other, R.id.tv_qq, R.id.tv_email, R.id.tv_blog)
    fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.tv_issues -> {
                intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.GANK_TITLE, "Gank")
                intent.putExtra(WebViewActivity.GANK_URL, "https://github.com")
                startActivity(intent)
            }
            R.id.tv_other -> {
                intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.GANK_TITLE, "mcflag")
                intent.putExtra(WebViewActivity.GANK_URL, "https://github.com/mcflag")
                startActivity(intent)
            }
            R.id.tv_qq -> if (isQQClientAvailable) {
                val url = "mqqwpa://im/chat?chat_type=wpa&uin=503233512"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else
                Toasty.error(this, "当前设备未安装QQ").show()
            R.id.tv_email -> {
                intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:whu_cc@foxmail.com")
                startActivity(intent)
            }
            R.id.tv_blog -> {
                intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.GANK_TITLE, "Blog")
                intent.putExtra(WebViewActivity.GANK_URL, "http://mcflag.github.io")
                startActivity(intent)
            }
        }
    }

    companion object {
        /**
         * 判断qq是否可用
         */
        val isQQClientAvailable: Boolean
            get() {
                val packageManager = Utils.getContext().packageManager
                val packageInfo = packageManager.getInstalledPackages(0)
                if (packageInfo != null) {
                    for (i in packageInfo.indices) {
                        val pn = packageInfo[i].packageName
                        if (pn == "com.tencent.mobileqq") {
                            return true
                        }
                    }
                }
                return false
            }
    }
}
