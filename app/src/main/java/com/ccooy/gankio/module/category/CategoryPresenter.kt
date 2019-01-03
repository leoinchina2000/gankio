package com.ccooy.gankio.module.category

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.CategoryResult
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ICategoryPresenter
 *
 */

class CategoryPresenter(
    private val mCategoryICategoryView: ICategoryView
) : ICategoryPresenter {

    private var mPage = 1
    private var mDisposable: Disposable? = null

    override fun subscribe() {
        getCategoryItems(true)
    }

    override fun unSubscribe() {
        mDisposable?.let {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }

    override fun getCategoryItems(isRefresh: Boolean) {
        if (isRefresh) {
            mPage = 1
            mCategoryICategoryView.showSwipeLoading()
        } else {
            mPage++
        }
        val observer: Observer<CategoryResult> = object : Observer<CategoryResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mCategoryICategoryView.hideSwipeLoading()
                mCategoryICategoryView.getCategoryItemsFail(mCategoryICategoryView.categoryName + " 列表数据获取失败！")
            }

            override fun onNext(categoryResult: CategoryResult) {
                if (categoryResult != null && !categoryResult.error) {
                    if (categoryResult.results.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mCategoryICategoryView.getCategoryItemsFail("获取数据为空！")
                    } else {
                        if (isRefresh) {
                            mCategoryICategoryView.setCategoryItems(categoryResult.results)
                            mCategoryICategoryView.hideSwipeLoading()
                            mCategoryICategoryView.setLoading()
                        } else {
                            mCategoryICategoryView.addCategoryItems(categoryResult.results)
                        }
                        // 如果当前获取的数据数目没有全局设定的每次获取的条数，说明已经没有更多数据
                        if (categoryResult.results.size < GlobalConfig.CATEGORY_COUNT) {
                            mCategoryICategoryView.setNoMore()
                        }
                    }
                } else {
                    mCategoryICategoryView.getCategoryItemsFail("获取数据失败！")
                }

            }
        }

        NetWork.getGankApi()
            .getCategoryData(mCategoryICategoryView.categoryName, GlobalConfig.CATEGORY_COUNT, mPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }
}
