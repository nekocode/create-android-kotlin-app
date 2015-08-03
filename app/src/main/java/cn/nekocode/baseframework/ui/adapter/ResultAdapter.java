package cn.nekocode.baseframework.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Result;
import cn.nekocode.baseframework.ui.adapter.helper.HelperAdapter;
import cn.nekocode.baseframework.ui.adapter.helper.HelperItemViewHolder;

/**
 * Created by nekocode on 2015/7/22.
 */
public class ResultAdapter extends HelperAdapter<Result> {
    static final int TYPE_ITEM = 0;

    public ResultAdapter(Context context, List<Result> list) {
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

    static class ItemViewHolder extends HelperItemViewHolder {
        @InjectView(R.id.imageView)
        ImageView imageView;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
