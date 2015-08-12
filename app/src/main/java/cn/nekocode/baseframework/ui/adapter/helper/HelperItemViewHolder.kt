package cn.nekocode.baseframework.ui.adapter.helper

import android.support.v7.widget.RecyclerView
import android.view.View
import cn.nekocode.baseframework.model.Weather
import com.squareup.picasso.Picasso

/**
 * Created by nekocode on 2015/7/23.
 */
public abstract class HelperItemViewHolder<T> : RecyclerView.ViewHolder {
    public var onItemClickListener: ((T) -> Unit)?
        get
        set(value: ((T) -> Unit)?) {
            $onItemClickListener = value
        }

    public var onItemLongClickListener: ((T) -> Boolean)?
        get
        set(value: ((T) -> Boolean)?) {
            onItemLongClickListener = value
        }

    constructor(view: View) : super(view) {
        $onItemClickListener = null
        $onItemLongClickListener = null
    }

    open fun setData(data : T) {
        itemView?.setOnClickListener() { onItemClickListener?.invoke(data) }
        itemView?.setOnLongClickListener() { onItemLongClickListener?.invoke(data) ?: false }
    }
}
