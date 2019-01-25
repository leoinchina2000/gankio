package com.ccooy.gankio

import android.app.Activity
import android.app.Application
import android.content.Context
import com.ccooy.gankio.di.component.DaggerAppComponent
import com.ccooy.gankio.utils.ProcessPhoenix
import com.ccooy.gankio.utils.Utils
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * 应用程序
 */

class App : Application(), HasActivityInjector {

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    init {
        instance = this
    }

    companion object {
        @get:Synchronized
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
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

    fun appContext(): Context {
        return instance
    }

    fun reStart() {
        ProcessPhoenix.triggerRebirth(instance)
    }
}
