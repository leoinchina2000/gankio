package com.ccooy.gankio.module.home

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import android.widget.LinearLayout
import android.widget.Toast
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationBar.*
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ccooy.gankio.App
import com.ccooy.gankio.R
import com.ccooy.gankio.module.article.ArticleFragment
import com.ccooy.gankio.module.base.BaseActivity
import com.ccooy.gankio.module.category.CategoryAllFragment
import com.ccooy.gankio.module.fuli.FuliFragment
import com.ccooy.gankio.module.test.TestFragment
import es.dmoral.toasty.Toasty
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
        var categoryAllFragment: CategoryAllFragment = CategoryAllFragment.newInstance("All Category Fragment")
        var articleFragment: ArticleFragment = ArticleFragment.newInstance("Article Fragment")
        var fuliFragment: FuliFragment = FuliFragment.newInstance("Fuli Fragment")
        var mineFragment: TestFragment = TestFragment.newInstance("Mine Fragment")

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
                                homeFragment=HomeFragment.newInstance("Today Fragment")
                            }
                            transaction.replace(R.id.ll_content, homeFragment)
                        }
                        1 -> {
                            if(categoryAllFragment==null){
                                categoryAllFragment= CategoryAllFragment.newInstance("All Category Fragment")
                            }
                            transaction.replace(R.id.ll_content, categoryAllFragment)
                        }
                        2 -> {
                            if(articleFragment==null){
                                articleFragment=ArticleFragment.newInstance("Article Fragment")
                            }
                            transaction.replace(R.id.ll_content, articleFragment)
                        }
                        3 -> {
                            if(fuliFragment==null){
                                fuliFragment=FuliFragment.newInstance("Fuli Fragment")
                            }
                            transaction.replace(R.id.ll_content, fuliFragment)
                        }
                        4 -> {
                            if(mineFragment==null){
                                mineFragment=TestFragment.newInstance("Mine Fragment")
                            }
                            transaction.replace(R.id.ll_content, mineFragment)
                        }
                        else -> {
                            if(mineFragment==null){
                                mineFragment= TestFragment.newInstance("Test Fragment")
                            }
                            transaction.replace(R.id.ll_content, mineFragment)
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
            .addItem(BottomNavigationItem(R.drawable.ic_launcher_background,"分类").setInactiveIconResource(R.drawable.ic_launcher_foreground))
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
            Toasty.normal(App.instance, resources.getString(R.string.exit_toast)).show()
            mExitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

}
