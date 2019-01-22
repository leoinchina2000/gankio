package com.ccooy.gankio.module.search

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ccooy.gankio.ConfigManage
import com.ccooy.gankio.R
import com.ccooy.gankio.model.QueryBean
import com.ccooy.gankio.module.base.adapter.CommonRecyclerAdapter
import com.ccooy.gankio.module.base.adapter.CommonRecyclerHolder
import com.ccooy.gankio.module.base.adapter.ListenerWithPosition
import com.ccooy.gankio.model.ResultsBean
import com.ccooy.gankio.module.web.WebViewActivity
import com.ccooy.gankio.utils.TimeUtil

class SearchRecyclerAdapter(context: Context)
    : CommonRecyclerAdapter<QueryBean>(context, null, R.layout.item_search_category),
        ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder> {

    override fun convert(holder: CommonRecyclerHolder, resultsBean: QueryBean?) {
        if (resultsBean != null) {

            // 上面的代码可以改写为这样：
            holder.setTextViewText(R.id.search_item_desc, if (resultsBean.desc == null || resultsBean.desc.isEmpty()) "unknown" else resultsBean.desc)
            holder.setTextViewText(R.id.search_item_author, if (resultsBean.who == null || resultsBean.who.isEmpty()) "unknown" else resultsBean.who)
            holder.setTextViewText(R.id.search_item_time, TimeUtil.dateFormat(resultsBean.publishedAt))
            holder.setOnClickListener(this, R.id.category_item_layout)
        }
    }

    override fun onClick(v: View, position: Int, holder: CommonRecyclerHolder) {
        val intent = Intent(mContext, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.GANK_TITLE, mData[position].desc)
        intent.putExtra(WebViewActivity.GANK_URL, mData[position].url)
        mContext.startActivity(intent)
    }
}
