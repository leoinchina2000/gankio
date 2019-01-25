package com.ccooy.gankio.module.test

import android.os.Bundle
import android.text.TextUtils
import com.ccooy.gankio.ConfigManage
import com.ccooy.gankio.R
import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.module.base.BaseActivity
import com.ccooy.gankio.utils.encrypt.DefaultSharedPreferenceUtil
import com.ccooy.gankio.utils.encrypt.EncryptHelper
import dagger.android.AndroidInjection
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_test_dagger.*
import okhttp3.HttpUrl
import javax.inject.Inject

class TestDaggerActivity : BaseActivity() {

    @Inject
    lateinit var mDefaultSharedPreferenceUtil: DefaultSharedPreferenceUtil
    @Inject
    lateinit var mEncryptHelper: EncryptHelper

    override val contentViewLayoutID: Int
        get() = R.layout.activity_test_dagger

    override fun beforeInit() {
        super.beforeInit()
        AndroidInjection.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        testSp.text = ConfigManage.getThumbnailQuality().toString() + ConfigManage.isListShowImg().toString()
        origin.text = mDefaultSharedPreferenceUtil.getStringValue(GlobalConfig.FRONT_SP)
    }

    override fun onScanFinishListener(barcode: String) {
        result.text = barcode
    }

    override fun onDeployListener() {
        Toasty.info(applicationContext, "正在解析，请稍等").show()
    }

    override fun onDeploySuccessListener(barcode: String) {
        val plainText = mEncryptHelper.decrypt(barcode, scanner.salt)
        result.text = plainText
        val split = plainText.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (split.size < 4) {
            Toasty.warning(applicationContext, "参数不完整").show()
            return
        }
        val timeStamp = java.lang.Long.parseLong(split[1])
        val anHourTimeSpan = (60 * 60 * 1000).toLong()
        if (System.currentTimeMillis() > timeStamp + anHourTimeSpan) {
            Toasty.warning(applicationContext, "过期无效").show()
            return
        }
        val merchantNumber = split[2]
        if (TextUtils.isEmpty(merchantNumber)) {
            Toasty.warning(applicationContext, "缺少商户号").show()
            return
        }
        val storeNumber = split[3]
        if (TextUtils.isEmpty(storeNumber)) {
            Toasty.warning(applicationContext, "缺少门店号").show()
            return
        }
        var frontURL = split[0]
        val httpUrl = HttpUrl.parse(frontURL) //检测url合法性
        if (httpUrl == null) {
            Toasty.warning(applicationContext, "Url格式异常").show()
        } else {
            val pathSegments = httpUrl.pathSegments()
            if ("" != pathSegments[pathSegments.size - 1]) {
                frontURL = "$frontURL/"
            }
            saveFrontIP(frontURL)
        }
    }

    private fun saveFrontIP(frontIP: String) {
        if (frontIP.endsWith("/")) {
            mDefaultSharedPreferenceUtil.setValue(GlobalConfig.FRONT_SP, frontIP)
        } else {
            mDefaultSharedPreferenceUtil.setValue(GlobalConfig.FRONT_SP, "$frontIP/")
        }
    }
}