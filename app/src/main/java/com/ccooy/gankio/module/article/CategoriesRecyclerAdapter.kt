package com.ccooy.gankio.module.article

import android.content.Context
import com.ccooy.gankio.R
import com.ccooy.gankio.module.base.adapter.CommonRecyclerAdapter
import com.ccooy.gankio.module.base.adapter.CommonRecyclerHolder
import com.ccooy.gankio.module.base.adapter.ListenerWithPosition
import com.ccooy.gankio.model.XianDuCategoryBean

class CategoriesRecyclerAdapter(context: Context) :
    CommonRecyclerAdapter<XianDuCategoryBean>(context, null, R.layout.item_xiandu_category) {

    lateinit var listener: ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder>

    override fun convert(holder: CommonRecyclerHolder, resultsBean: XianDuCategoryBean?) {
        if (resultsBean != null) {

            holder.setTextViewText(
                R.id.category_item_name,
                if (resultsBean.name == null || resultsBean.name.isEmpty()) "unknown" else resultsBean.name
            )
            holder.setTextViewText(
                R.id.category_item_en_name,
                if (resultsBean.en_name == null || resultsBean.en_name.isEmpty()) "unknown" else resultsBean.en_name
            )
            holder.setTextViewText(
                R.id.category_item_rank,
                if (resultsBean.rank == null) "unknown" else "" + resultsBean.rank
            )
            holder.setOnClickListener(listener, R.id.category_item_layout)
        }
    }

    fun setOnItemClickListener(onItemClickListener: ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder>) {
        listener = onItemClickListener
    }

    fun getItem(position: Int): XianDuCategoryBean {
        return mData[position]
    }
}
