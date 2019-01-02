package com.ccooy.gankio.module.home

import com.ccooy.gankio.config.GlobalConfig
import com.ccooy.gankio.model.CategoryResult
import com.ccooy.gankio.model.PictureModel
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.module.home.HomeContract.IHomePresenter
import com.ccooy.gankio.module.home.HomeContract.IHomeView
import com.ccooy.gankio.net.NetWork
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class HomePresenter internal constructor(private val mHomeView: IHomeView) : IHomePresenter {

    private var mSubscription: Subscription? = null

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
        mSubscription?.let {
            if (!mSubscription!!.isUnsubscribed) {
                mSubscription!!.unsubscribe()
            }
        }

    }

    override fun getBannerData() {
        mSubscription = NetWork.getGankApi()
                .getCategoryData(GlobalConfig.CATEGORY_NAME_FULI, 5, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CategoryResult> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        mHomeView.showBannerFail("Banner 图加载失败")
                    }

                    override fun onNext(categoryResult: CategoryResult?) {
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
                })
    }

}
