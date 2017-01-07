package cn.nekocode.kotgo.sample.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.DO.Meizi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_meizi.view.*
import org.jetbrains.anko.layoutInflater

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MeiziListAdapter(private val list: List<Meizi>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    object Type {
        const val TYPE_ITEM: Int = 0
    }

    var onMeiziItemClickListener: ((Meizi) -> Unit)? = null
    var onMeiziItemLongClickListener: ((Meizi) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            Type.TYPE_ITEM -> {
                val v = parent.context.layoutInflater.inflate(R.layout.item_meizi, parent, false)
                return ItemViewHolder(v)
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int) = Type.TYPE_ITEM

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.setData(list[position])
            }
        }
    }

    private inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var meizi: Meizi? = null

        init {
            view.setOnClickListener {
                meizi?.let {
                    onMeiziItemClickListener?.invoke(it)
                }
            }

            view.setOnLongClickListener {
                meizi?.let {
                    onMeiziItemLongClickListener?.invoke(it)
                } ?: false
            }
        }

        fun setData(meizi: Meizi) {
            this.meizi = meizi

            Picasso.with(itemView.context).load(meizi.url).centerCrop().fit().into(itemView.imageView)
            itemView.textView.text = meizi.id
            itemView.textView2.text = "${meizi.who} - ${meizi.type}"
        }
    }
}


