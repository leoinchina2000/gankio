package com.ccooy.gankio.module.search

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.CategoryResult
import com.ccooy.gankio.model.QueryResult
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private val mSearchView: ISearchView
) : ISearchPresenter {

    private var mPage = 1
    private var mDisposable: Disposable? = null

    override fun subscribe() {
        getSearchItems(true)
    }

    override fun unSubscribe() {
        mDisposable?.let {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }

    override fun getSearchItems(isRefresh: Boolean) {
        if (isRefresh) {
            mPage = 1
            mSearchView.showSwipeLoading()
        } else {
            mPage++
        }
        val observer: Observer<QueryResult> = object : Observer<QueryResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mSearchView.hideSwipeLoading()
                mSearchView.getSearchItemsFail("关键字：" + mSearchView.searchWords + "，分类：" + mSearchView.searchCategory + " 列表数据获取失败！")
            }

            override fun onNext(queryResult: QueryResult) {
                mSearchView.hideSwipeLoading()
                if (queryResult != null && !queryResult.error) {
                    if (queryResult.results.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mSearchView.getSearchItemsFail("获取数据为空！")
                        if (isRefresh) {
                            mSearchView.setSearchItems(queryResult.results)
                        }
                        mSearchView.setNoMore()
                    } else {
                        if (isRefresh) {
                            mSearchView.setSearchItems(queryResult.results)
                            mSearchView.setLoading()
                        } else {
                            mSearchView.addSearchItems(queryResult.results)
                        }
                        // 如果当前获取的数据数目没有全局设定的每次获取的条数，说明已经没有更多数据
                        if (queryResult.results.size < GlobalConfig.CATEGORY_COUNT) {
                            mSearchView.setNoMore()
                        }
                    }
                } else {
                    mSearchView.getSearchItemsFail("获取数据失败！")
                }

            }
        }

        NetWork.getGankApi()
            .getQueryData(mSearchView.searchWords, mSearchView.searchCategory, GlobalConfig.CATEGORY_COUNT, mPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }
}