package com.ccooy.gankio.module.home

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.CategoryResult
import com.ccooy.gankio.model.PictureModel
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.module.home.HomeContract.IHomePresenter
import com.ccooy.gankio.module.home.HomeContract.IHomeView
import com.ccooy.gankio.net.NetWork
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter internal constructor(private val mHomeView: IHomeView) : IHomePresenter {

    private var mDisposable: Disposable? = null

    private val mModels: MutableList<PictureModel>

    val bannerModel: List<PictureModel>
        get() = this.mModels

    init {
        mModels = ArrayList()
    }

    override fun subscribe() {
        getBannerData()
    }

    override fun unSubscribe() {
        mDisposable?.let {
            if (!mDisposable!!.isDisposed) {
                mDisposable!!.dispose()
            }
        }

    }

    override fun getBannerData() {
        val observer: Observer<CategoryResult> = object : Observer<CategoryResult> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
            }

            override fun onError(e: Throwable) {
                mHomeView.showBannerFail("Banner 图加载失败")
            }

            override fun onNext(categoryResult: CategoryResult) {
                if (categoryResult != null && !categoryResult.results.isEmpty()) {
                    val imgUrls = ArrayList<String>()
                    for (result: ResultsBean in categoryResult.results) {
                        if (!result.url.isEmpty()) {
                            imgUrls.add(result.url)
                        }
                        mModels.add(PictureModel(if (result.desc.isEmpty()) "unknown" else result.desc, result.url))
                    }
                    mHomeView.setBanner(imgUrls)
                } else {
                    mHomeView.showBannerFail("Banner 图加载失败")
                }
            }
        }
        NetWork.getGankApi()
            .getCategoryData(GlobalConfig.CATEGORY_NAME_FULI, 5, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}
