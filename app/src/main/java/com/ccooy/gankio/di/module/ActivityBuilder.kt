package com.ccooy.gankio.di.module

import com.ccooy.gankio.di.scope.ActivityScope
import com.ccooy.gankio.module.test.TestDaggerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindTestDaggerActivity(): TestDaggerActivity

}