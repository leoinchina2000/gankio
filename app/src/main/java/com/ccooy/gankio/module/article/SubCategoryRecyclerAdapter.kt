package com.ccooy.gankio.module.article

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ccooy.gankio.ConfigManage
import com.ccooy.gankio.R
import com.ccooy.gankio.module.base.adapter.CommonRecyclerAdapter
import com.ccooy.gankio.module.base.adapter.CommonRecyclerHolder
import com.ccooy.gankio.module.base.adapter.ListenerWithPosition
import com.ccooy.gankio.model.XianDuSubCategoryBean
import com.ccooy.gankio.utils.TimeUtil

class SubCategoryRecyclerAdapter(context: Context) :
    CommonRecyclerAdapter<XianDuSubCategoryBean>(context, null, R.layout.item_xiandu_sub_category) {

    lateinit var listener: ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder>

    override fun convert(holder: CommonRecyclerHolder, resultsBean: XianDuSubCategoryBean?) {
        if (resultsBean != null) {
            val imageView = holder.getView<ImageView>(R.id.category_item_img)
            if (ConfigManage.isListShowImg()) { // 列表显示图片
                imageView.visibility = View.VISIBLE
                var quality = ""
                if (resultsBean.icon != null && resultsBean.icon.isNotEmpty()) {
                    when (ConfigManage.getThumbnailQuality()) {
                        0 // 原图
                        -> quality = ""
                        1 //
                        -> quality = "?imageView2/0/w/400"
                        2 -> quality = "?imageView2/0/w/190"
                    }
                    Glide.with(mContext)
                        .load(resultsBean.icon + quality)
                        .apply(RequestOptions().placeholder(R.mipmap.image_default).error(R.mipmap.image_default))
                        .into(imageView)
                } else { // 列表不显示图片
                    Glide.with(mContext).load(R.mipmap.image_default).into(imageView)
                }
            } else {
                imageView.visibility = View.GONE
            }

            holder.setTextViewText(
                R.id.category_item_desc,
                if (resultsBean.title == null || resultsBean.title.isEmpty()) "unknown" else resultsBean.title
            )
            holder.setTextViewText(
                R.id.category_item_author,
                if (resultsBean.id == null || resultsBean.id.isEmpty()) "unknown" else resultsBean.id
            )
            holder.setTextViewText(R.id.category_item_time, TimeUtil.dateFormat(resultsBean.created_at))
            holder.setOnClickListener(listener, R.id.category_item_layout)
        }
    }

    fun setOnItemClickListener(onItemClickListener: ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder>) {
        listener = onItemClickListener
    }

    fun getItem(position: Int): XianDuSubCategoryBean {
        return mData[position]
    }
}
