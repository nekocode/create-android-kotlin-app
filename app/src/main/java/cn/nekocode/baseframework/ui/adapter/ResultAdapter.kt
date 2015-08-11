package cn.nekocode.baseframework.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Weather;
import cn.nekocode.baseframework.ui.adapter.helper.HelperAdapter;
import cn.nekocode.baseframework.ui.adapter.helper.HelperItemViewHolder;
import com.squareup.picasso.Picasso
import java.util

/**
 * Created by nekocode on 2015/7/22.
 */
class ResultAdapter(context: Context, list: List<Weather>?) : HelperAdapter<Weather>(context, list) {

    val TYPE_ITEM = 0;

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            TYPE_ITEM -> {
                val v = layoutInflater.inflate(R.layout.item_result, parent, false)
                return ItemViewHolder(v);
            }

        }

        throw UnsupportedOperationException()
    }

    override fun getItemCount() = list?.size() ?:0



    public ResultAdapter(Context context, util.List<Weather> list) {
        super(context, list);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM:
                View v = layoutInflater.inflate(R.layout.item_result, parent, false);
                return new ItemViewHolder(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(holder instanceof ItemViewHolder) {

        }
    }

//    private class ItemViewHolder : HelperItemViewHolder() {
//        ImageView imageView;
//
//        ItemViewHolder(View view) {
//            super(view);
//        }
//    }


    private class ItemViewHolder(view: View, var onItemClickListener: ((Weather) -> Unit)?) : HelperItemViewHolder(view) {

        public val image: ImageView


        fun setItem(weather : Weather) {
            itemView?.setOnClickListener() { onItemClickListener?.invoke() }
//            Picasso.with(itemView.getContext()).load(weather.getWeatherInfo().getCity()).centerCrop().fit().into(image)
        }
    }
}


