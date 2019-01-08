package com.ccooy.gankio.module.fuli

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.CategoryResult
import com.ccooy.gankio.model.PictureModel
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FuliPresenter internal constructor(private val mFuliView: FuliContract.IFuliView) : FuliContract.IFuliPresenter {
    private var mDisposable: Disposable? = null
    private var mPage = 1
    private val mModels: MutableList<PictureModel>

    val pictures: List<PictureModel>
        get() = this.mModels

    init {
        mModels = ArrayList()
    }

    override fun subscribe() {
        getFuliData(true)
    }

    override fun unSubscribe() {
        mDisposable?.let {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }
    }

    override fun getFuliData(isRefresh: Boolean) {
        if (isRefresh) {
            mPage = 1
            mFuliView.showSwipeLoading()
        } else {
            mPage++
        }
        var observer: Observer<CategoryResult> = object : Observer<CategoryResult> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mFuliView.hideSwipeLoading()
                mFuliView.getFuliItemsFail(mFuliView.fuliName + " 列表数据获取失败！")
            }

            override fun onNext(fuliResult: CategoryResult) {
                if (fuliResult != null && !fuliResult.error) {
                    if (fuliResult.results.isEmpty()) {
                        mFuliView.getFuliItemsFail("获取数据为空！")
                    } else {
                        if (isRefresh) {
                            mFuliView.setFuliItems(fuliResult.results)
                            mFuliView.hideSwipeLoading()
                            mFuliView.setLoading()
                        } else {
                            mFuliView.addFuliItems(fuliResult.results)
                        }
                        if (fuliResult.results.size < GlobalConfig.CATEGORY_COUNT) {
                            mFuliView.setNoMore()
                        }
                    }
                } else {
                    mFuliView.getFuliItemsFail("获取数据失败！")
                }
            }
        }

        NetWork.getGankApi()
            .getCategoryData(mFuliView.fuliName, GlobalConfig.CATEGORY_COUNT, mPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }
}