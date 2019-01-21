package com.ccooy.gankio.module.article

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.XianDuCategoriesResult
import com.ccooy.gankio.model.XianDuDataResult
import com.ccooy.gankio.model.XianDuSubCategoryResult
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticlePresenter(
    private val mArticleView: IArticleView
) : IArticlePresenter {

    private var mPage = 1
    private var mDisposable: Disposable? = null

    override fun subscribe() {
        getCategories()
    }

    override fun unSubscribe() {
        mDisposable?.let {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }

    override fun getCategories() {
        mArticleView.showSwipeLoading()
        val observer: Observer<XianDuCategoriesResult> = object : Observer<XianDuCategoriesResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mArticleView.hideSwipeLoading()
                mArticleView.getXianDuCategoryItemsFail("列表数据获取失败！")
            }

            override fun onNext(categoryResult: XianDuCategoriesResult) {
                if (categoryResult != null && !categoryResult.error) {
                    if (categoryResult.results.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mArticleView.getXianDuCategoryItemsFail("获取数据为空！")
                    } else {
                        mArticleView.setXianDuCategoryItems(categoryResult.results)
                        mArticleView.hideSwipeLoading()
                        mArticleView.setNoMore()
                    }
                } else {
                    mArticleView.getXianDuCategoryItemsFail("获取数据失败！")
                }

            }
        }

        NetWork.getGankApi()
            .getXianDuCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    override fun getSubCategory(category: String) {
        mArticleView.showSwipeLoading()
        val observer: Observer<XianDuSubCategoryResult> = object : Observer<XianDuSubCategoryResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mArticleView.hideSwipeLoading()
                mArticleView.getXianDuSubCategoryItemsFail("列表数据获取失败！")
            }

            override fun onNext(categoryResult: XianDuSubCategoryResult) {
                if (categoryResult != null && !categoryResult.error) {
                    if (categoryResult.results.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mArticleView.getXianDuSubCategoryItemsFail("获取数据为空！")
                    } else {
                        mArticleView.setXianDuSubCategoryItems(categoryResult.results)
                        mArticleView.hideSwipeLoading()
                        mArticleView.setNoMore()
                    }
                } else {
                    mArticleView.getXianDuSubCategoryItemsFail("获取数据失败！")
                }

            }
        }

        NetWork.getGankApi()
            .getXianDuCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }

    override fun getData(isRefresh: Boolean, subCategoryId: String) {
        if (isRefresh) {
            mPage = 1
            mArticleView.showSwipeLoading()
        } else {
            mPage++
        }
        val observer: Observer<XianDuDataResult> = object : Observer<XianDuDataResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mArticleView.hideSwipeLoading()
                mArticleView.getXianDuCategoryDataItemsFail(subCategoryId + " 列表数据获取失败！")
            }

            override fun onNext(xianduDataResult: XianDuDataResult) {
                if (xianduDataResult != null && !xianduDataResult.error) {
                    if (xianduDataResult.results.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mArticleView.getXianDuCategoryDataItemsFail("获取数据为空！")
                    } else {
                        if (isRefresh) {
                            mArticleView.setXianDuDataItems(xianduDataResult.results)
                            mArticleView.hideSwipeLoading()
                            mArticleView.setLoading()
                        } else {
                            mArticleView.addXianDuDataItems(xianduDataResult.results)
                        }
                        // 如果当前获取的数据数目没有全局设定的每次获取的条数，说明已经没有更多数据
                        if (xianduDataResult.results.size < GlobalConfig.CATEGORY_COUNT) {
                            mArticleView.setNoMore()
                        }
                    }
                } else {
                    mArticleView.getXianDuCategoryDataItemsFail("获取数据失败！")
                }

            }
        }

        NetWork.getGankApi()
            .getXianDuData(subCategoryId, GlobalConfig.CATEGORY_COUNT, mPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }
}