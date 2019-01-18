package com.ccooy.gankio.module.article

import com.ccooy.gankio.model.XianDuCategoriesResult
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

    override fun getData(isRefresh: Boolean) {

    }
}