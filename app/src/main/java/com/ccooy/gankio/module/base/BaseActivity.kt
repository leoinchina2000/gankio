package com.ccooy.gankio.module.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import butterknife.ButterKnife
import com.ccooy.gankio.utils.AndroidUtil
import com.ccooy.gankio.utils.ToolScanner

/**
 * Activity基类，所有Activity应该继承此类
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var scanner: ToolScanner

    /**
     * 获取布局ID
     *
     * @return  布局id
     */
    protected abstract val contentViewLayoutID: Int

    /**
     * 界面初始化前期准备
     */
    protected open fun beforeInit() {

    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInit()
        if (contentViewLayoutID != 0) {
            setContentView(contentViewLayoutID)
            initView(savedInstanceState)
        }

        scanner = ToolScanner(object : ToolScanner.OnScanSuccessListener{
            override fun onScanSuccess(barcode: String) {
                onScanFinishListener(barcode)
            }

            override fun onScanStart() {

            }

            override fun onScanDeploySuccess(barcode: String) {
                onDeploySuccessListener(barcode)
            }

            override fun onScanDeploy() {
                onDeployListener()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        AndroidUtil.fixLeak(this)
        scanner.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        scanner.analysisKeyEvent(event)
        return super.dispatchKeyEvent(event)
    }

    open fun onScanFinishListener(barcode: String){ }

    open fun onDeployListener(){ }

    open fun onDeploySuccessListener(barcode: String){ }
}