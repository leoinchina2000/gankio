package com.ccooy.gankio.module.article

import com.ccooy.gankio.model.XianDuCategoriesResult
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticlePresenter(
    private val mArticleView: IArticleView
) : IArticlePresenter{

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
        val observer: Observer<XianDuCategoriesResult> = object : Observer<XianDuCategoriesResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mArticleView.hideSwipeLoading()
                mArticleView.getCategoryItemsFail("列表数据获取失败！")
            }

            override fun onNext(categoryResult: XianDuCategoriesResult) {
                if (categoryResult != null && !categoryResult.error) {
                    if (categoryResult.results.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mArticleView.getCategoryItemsFail("获取数据为空！")
                    } else {
                        mArticleView.setXianDuCategoryItems(categoryResult.results)
                        mArticleView.hideSwipeLoading()
                        mArticleView.setLoading()
                    }
                } else {
                    mArticleView.getCategoryItemsFail("获取数据失败！")
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

    }

    override fun getData(isRefresh: Boolean) {

    }
}