package com.ccooy.gankio.module.fuli

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ccooy.gankio.ConfigManage
import com.ccooy.gankio.R
import com.ccooy.gankio.module.base.adapter.CommonRecyclerAdapter
import com.ccooy.gankio.module.base.adapter.CommonRecyclerHolder
import com.ccooy.gankio.module.base.adapter.ListenerWithPosition
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.module.picture.PictureActivity
import com.ccooy.gankio.utils.TimeUtil

class FuliRecyclerAdapter(context: Context)
    : CommonRecyclerAdapter<ResultsBean>(context, null, R.layout.item_category),
        ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder> {

    override fun convert(holder: CommonRecyclerHolder, resultsBean: ResultsBean?) {
        if (resultsBean != null) {
            val imageView = holder.getView<ImageView>(R.id.category_item_img)
            if (ConfigManage.isListShowImg()) { // 列表显示图片
                imageView.visibility = View.VISIBLE
                var quality = ""
                if (resultsBean.url != null && resultsBean.url.isNotEmpty()) {
                    when (ConfigManage.getThumbnailQuality()) {
                        0 // 原图
                        -> quality = ""
                        1 //
                        -> quality = "?imageView2/0/w/400"
                        2 -> quality = "?imageView2/0/w/190"
                    }
                    Glide.with(mContext)
                            .load(resultsBean.url + quality)
                            .placeholder(R.mipmap.image_default)
                            .error(R.mipmap.image_default)
                            .into(imageView)
                } else { // 列表不显示图片
                    Glide.with(mContext).load(R.mipmap.image_default).into(imageView)
                }
            } else {
                imageView.visibility = View.GONE
            }

//            holder.setTextViewText(R.id.category_item_desc, if (TextUtils.isEmpty(resultsBean.desc)) "unknown" else resultsBean.desc)
//            holder.setTextViewText(R.id.category_item_author, if (TextUtils.isEmpty(resultsBean.who)) "unknown" else resultsBean.who)
//            holder.setTextViewText(R.id.category_item_src, if (TextUtils.isEmpty(resultsBean.source)) "unknown" else resultsBean.source)

            // 上面的代码可以改写为这样：
            holder.setTextViewText(R.id.category_item_desc, if (resultsBean.desc == null || resultsBean.desc.isEmpty()) "unknown" else resultsBean.desc)
            holder.setTextViewText(R.id.category_item_author, if (resultsBean.who == null || resultsBean.who.isEmpty()) "unknown" else resultsBean.who)
            holder.setTextViewText(R.id.category_item_src, if (resultsBean.source == null || resultsBean.source.isEmpty()) "unknown" else resultsBean.source)
            holder.setTextViewText(R.id.category_item_time, TimeUtil.dateFormat(resultsBean.publishedAt))
            holder.setOnClickListener(this, R.id.category_item_layout)
        }
    }

    override fun onClick(v: View, position: Int, holder: CommonRecyclerHolder) {
        mContext.startActivity(PictureActivity.newIntent(mContext, mData[position].url, mData[position].desc))
//        PictureActivity.start(mContext as Activity, mData[position].url, mData[position].desc, v)
    }
}
