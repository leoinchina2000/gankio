package com.ccooy.gankio.module.home

import com.ccooy.gankio.model.DayResult
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ICategoryPresenter
 *
 */

class HomePresenter(
    private val mCategoryICategoryView: IHomeView
) : IHomePresenter {

    private var mDisposable: Disposable? = null

    override fun subscribe() {
        getTodayItems()
    }

    override fun unSubscribe() {
        mDisposable?.let {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }

    override fun getTodayItems() {
        mCategoryICategoryView.showLoading()
        val observer: Observer<DayResult> = object : Observer<DayResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mCategoryICategoryView.hideLoading()
                mCategoryICategoryView.getTodayItemsFail("今日数据获取失败！")
            }

            override fun onNext(todayResult: DayResult) {
                mCategoryICategoryView.hideLoading()
                if (todayResult != null && !todayResult.error) {
                    var list: List<ResultsBean> = mergeList(todayResult)
                    if (list.isEmpty()) {
                        // 如果可以，这里可以增加占位图
                        mCategoryICategoryView.getTodayItemsFail("获取数据为空！")
                    } else {
                        mCategoryICategoryView.setTodayItems(list)
                    }
                } else {
                    mCategoryICategoryView.getTodayItemsFail("获取数据失败！")
                }
                mCategoryICategoryView.setNoMore()

            }
        }

        NetWork.getGankApi()
            .getTodayData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun mergeList(todayResult: DayResult): List<ResultsBean> {
        var names: List<String> = todayResult.category
        var datas: HashMap<String, List<ResultsBean>> = todayResult.results
        var results: MutableList<ResultsBean> = mutableListOf()
        for (name in names) {
            if(datas.get(name)!=null) {
                var temp: List<ResultsBean> = datas.get(name)!!
                if (temp.isNotEmpty())
                    results.addAll(temp.toMutableList())
            }
        }
        return results
    }
}
