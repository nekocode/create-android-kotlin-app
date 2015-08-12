package cn.nekocode.baseframework.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView
import butterknife.bindView


import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Weather;
import cn.nekocode.baseframework.ui.activity.MainActivity
import com.squareup.picasso.Picasso
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.text
import java.util
import kotlin.platform.platformStatic

/**
 * Created by nekocode on 2015/7/22.
 */
class ResultAdapter(private val list: List<Weather>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object Type {
        public platformStatic val TYPE_ITEM: Int = 0;
    }

    var onWeatherItemClickListener: ((Weather) -> Unit)? = null
    var onWeatherItemLongClickListener: ((Weather) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            Type.TYPE_ITEM -> {
                val v = parent!!.getContext().layoutInflater.inflate(R.layout.item_result, parent, false)
                return ItemViewHolder(v);
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list.size()

    override fun getItemViewType(position: Int) = when(position) {
        else -> Type.TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                holder.setData(list.get(position));
            }
        }
    }

    private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView by bindView(R.id.imageView)
        val textView: TextView by bindView(R.id.textView)

        fun setData(weather : Weather) {
            itemView?.setOnClickListener() { onWeatherItemClickListener?.invoke(weather) }
            itemView?.setOnLongClickListener() { onWeatherItemLongClickListener?.invoke(weather) ?: false }

            textView.text = "hehe"
//            Picasso.with(itemView.getContext()).load(weather.getWeatherInfo().getCity()).centerCrop().fit().into(imageView)
        }
    }
}


