package com.ccooy.gankio.module.picture

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Environment
import com.ccooy.gankio.R
import com.ccooy.gankio.module.picture.PictureContract.Presenter
import com.ccooy.gankio.utils.ToastyUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PicturePresenter(private val mContext: Context) : Presenter {

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    @SuppressLint("CheckResult")
    override fun saveGirl(url: String, bitmap: Bitmap, title: String) {
        Observable.create(ObservableOnSubscribe<Bitmap> { subscriber ->
            subscriber.onError(Exception("无法下载到图片！"))
            subscriber.onNext(bitmap)
            subscriber.onComplete()
        }).flatMap { bitmap1 ->
            val appDir = File(Environment.getExternalStorageDirectory(), mContext.resources.getString(R.string.app_name))
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val fileName = title.replace('/', '-') + ".jpg"
            val file = File(appDir, fileName)
            try {
                val fos = FileOutputStream(file)
                if (bitmap1 != null) {
                    bitmap1.compress(CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val uri = Uri.fromFile(file)
            // 通知图库更新
            val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
            mContext.sendBroadcast(scannerIntent)
            Observable.just(uri)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val appDir = File(Environment.getExternalStorageDirectory(), mContext.resources.getString(R.string.app_name))
                val msg = String.format("图片已保存至 %s 文件夹", appDir.absoluteFile)
                ToastyUtil.showSuccess(msg)
            }

    }
}
