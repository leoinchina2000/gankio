package com.ccooy.gankio.module.home

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.widget.LinearLayout
import android.widget.Toast
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationBar.*
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ccooy.gankio.R
import com.ccooy.gankio.base.adapter.CommonViewPagerAdapter
import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.module.base.BaseActivity
import com.ccooy.gankio.module.category.CategoryFragment
import com.ccooy.gankio.module.fuli.FuliFragment
import com.ccooy.gankio.utils.*
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import kotlinx.android.synthetic.main.activity_home.*

/**
 * 主页面
 *
 */
class MainActivity : BaseActivity() {

    lateinit var mLLContent: LinearLayout
    lateinit var mMainLayout: ConstraintLayout
    // 保存用户按返回键的时间
    private var mExitTime: Long = 0

    override val contentViewLayoutID: Int
        get() = R.layout.activity_home

    override fun initView(savedInstanceState: Bundle?) {
        mLLContent = ll_content
        mMainLayout = mainActivity

        var homeFragment: HomeFragment = HomeFragment.newInstance("Home Fragment")
        var articleFragment: FuliFragment = FuliFragment.newInstance("Article Fragment")
        var fuliFragment: FuliFragment = FuliFragment.newInstance("Fuli Fragment")
        var mineFragment: FuliFragment = FuliFragment.newInstance("Mine Fragment")

        bottomNavigationBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener{
                override fun onTabReselected(position: Int) {
                }

                override fun onTabUnselected(position: Int) {
                    //上一次选中的item
                }

                override fun onTabSelected(position: Int) {
                    var transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
                    when(position){
                        0 -> {
                            if(homeFragment==null){
                                homeFragment=HomeFragment.newInstance("Home Fragment")
                            }
                            transaction.replace(R.id.ll_content, homeFragment)
                        }
                        1 -> {
                            if(fuliFragment==null){
                                fuliFragment=FuliFragment.newInstance("Fuli Fragment")
                            }
                            transaction.replace(R.id.ll_content, fuliFragment)
                        }
                        2 -> {
                            if(fuliFragment==null){
                                fuliFragment=FuliFragment.newInstance("Fuli Fragment")
                            }
                            transaction.replace(R.id.ll_content, fuliFragment)
                        }
                        3 -> {
                            if(fuliFragment==null){
                                fuliFragment=FuliFragment.newInstance("Fuli Fragment")
                            }
                            transaction.replace(R.id.ll_content, fuliFragment)
                        }
                        else -> {
                            if(homeFragment==null){
                                homeFragment=HomeFragment.newInstance("Home Fragment")
                            }
                            transaction.replace(R.id.ll_content, homeFragment)
                        }
                    }
                    transaction.commit()
                }
            })

        bottomNavigationBar
            .setMode(MODE_FIXED) // 设置mode
            .setBackgroundStyle(BACKGROUND_STYLE_STATIC)  // 背景样式
            .setBarBackgroundColor("#2FA8E1") // 背景颜色
            .setInActiveColor("#929292") // 未选中状态颜色
            .setActiveColor("#ffffff") // 选中状态颜色
            .addItem(BottomNavigationItem(R.drawable.ic_launcher_background,"首页").setInactiveIconResource(R.drawable.ic_launcher_foreground)) // 添加Item
            .addItem(BottomNavigationItem(R.drawable.ic_launcher_background,"闲读").setInactiveIconResource(R.drawable.ic_launcher_foreground))
            .addItem(BottomNavigationItem(R.drawable.ic_launcher_background,"福利").setInactiveIconResource(R.drawable.ic_launcher_foreground))
            .addItem(BottomNavigationItem(R.drawable.ic_launcher_background,"我的").setInactiveIconResource(R.drawable.ic_launcher_foreground))
            .initialise()  // 提交初始化（完成配置）

        bottomNavigationBar.setFirstSelectedPosition(0)
        var transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ll_content, homeFragment)
        transaction.commit()
    }

    override fun beforeInit() {
        super.beforeInit()
//        StatusBarUtil.setTranslucent(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Snackbar.make(mMainLayout, R.string.exit_toast, Toast.LENGTH_SHORT).show()
            mExitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

}
