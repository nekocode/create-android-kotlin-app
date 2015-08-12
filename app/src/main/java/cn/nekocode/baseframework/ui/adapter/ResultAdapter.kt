package cn.nekocode.baseframework.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Weather;
import cn.nekocode.baseframework.ui.adapter.helper.HelperAdapter;
import cn.nekocode.baseframework.ui.adapter.helper.HelperItemViewHolder;
import com.squareup.picasso.Picasso
import java.util

/**
 * Created by nekocode on 2015/7/22.
 */
class ResultAdapter(context: Context, list: List<Weather>) : HelperAdapter<Weather>(context, list) {

    val TYPE_ITEM = 0;

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            TYPE_ITEM -> {
                val v = layoutInflater.inflate(R.layout.item_result, parent, false)
                return ItemViewHolder(v, {
                    weather ->
                });
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list?.size() ?: 0

    override fun getItemViewType(position: Int) = when(position) {
        else -> TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when(holder) {
            is ItemViewHolder -> {

            }
        }
    }

//    private class ItemViewHolder : HelperItemViewHolder() {
//        ImageView imageView;
//
//        ItemViewHolder(View view) {
//            super(view);
//        }
//    }


    private class ItemViewHolder : HelperItemViewHolder {

        public val image: ImageView
        public val onItemClickListener: ((Weather) -> Unit)?

        constructor(view: View, onItemClickListener: ((Weather) -> Unit)?) : super(view) {
            image = view.findViewById(R.id.imageView) as ImageView
            $onItemClickListener = onItemClickListener
        }


        fun setItem(weather : Weather) {
            itemView?.setOnClickListener() { onItemClickListener?.invoke(weather) }
//            Picasso.with(itemView.getContext()).load(weather.getWeatherInfo().getCity()).centerCrop().fit().into(image)
        }
    }
}


