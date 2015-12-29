package cn.nekocode.baseframework.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView
import butterknife.bindView

import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.data.Model
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick

/**
 * Created by nekocode on 2015/7/22.
 */
class ResultAdapter(private val list: List<Model.WeatherInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    object Type {
        const val TYPE_ITEM: Int = 0;
    }

    var onWeatherItemClickListener: ((Model.WeatherInfo) -> Unit)? = null
    var onWeatherItemLongClickListener: ((Model.WeatherInfo) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            Type.TYPE_ITEM -> {
                val v = parent!!.context.layoutInflater.inflate(R.layout.item_result, parent, false)
                return ItemViewHolder(v);
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int) = when(position) {
        else -> Type.TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                holder.setData(list[position]);
            }
        }
    }

    private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView by bindView(R.id.imageView)
        val textView: TextView by bindView(R.id.textView2)

        fun setData(weatherInfo : Model.WeatherInfo) {
            itemView?.onClick { onWeatherItemClickListener?.invoke(weatherInfo) }
            itemView?.onLongClick { onWeatherItemLongClickListener?.invoke(weatherInfo) ?: false }

            textView.text = weatherInfo.city
//            Picasso.with(itemView.context).load(weather.getWeatherInfo().getCity()).centerCrop().fit().into(imageView)
        }
    }
}


