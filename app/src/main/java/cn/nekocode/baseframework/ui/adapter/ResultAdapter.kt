package cn.nekocode.baseframework.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.bindView


import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Weather;
import cn.nekocode.baseframework.ui.activity.MainActivity
import cn.nekocode.baseframework.ui.adapter.helper.HelperItemViewHolder;
import com.squareup.picasso.Picasso
import java.util
import kotlin.platform.platformStatic

/**
 * Created by nekocode on 2015/7/22.
 */
class ResultAdapter(val context: Context, val list: List<Weather>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private platformStatic val TYPE_ITEM = 0;
    }

    private val layoutInflater: LayoutInflater
    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            TYPE_ITEM -> {
                val v = layoutInflater.inflate(R.layout.item_result, parent, false)
                return ItemViewHolder(v);
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list.size()

    override fun getItemViewType(position: Int) = when(position) {
        else -> TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                holder.setData(list.get(position));
                holder.onItemClickListener = {}
            }
        }
    }

    private class ItemViewHolder(view: View): HelperItemViewHolder<Weather>(view) {

        public val image: ImageView by bindView(R.id.imageView)

        override fun setData(weather : Weather) {
            super.setData(weather)
            Picasso.with(itemView.getContext()).load(weather.getWeatherInfo().getCity()).centerCrop().fit().into(image)
        }
    }
}


