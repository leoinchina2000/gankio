package com.ccooy.gankio

import android.app.Application
import com.ccooy.gankio.utils.Utils
import com.squareup.leakcanary.LeakCanary

/**
 * 应用程序
 */

class App : Application() {

    init {
        instance = this
    }

    companion object {
        @get:Synchronized
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        // 初始化 LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        ConfigManage.initConfig(this)
        Utils.init(this)
    }

    fun exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

}
