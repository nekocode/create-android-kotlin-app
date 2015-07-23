package cn.nekocode.baseframework.ui.adapter.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.nekocode.baseframework.R;

/**
 * Created by nekocode on 2015/7/22.
 */
public abstract class HelperAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;
    protected List<T> list;
    protected LayoutInflater layoutInflater;

    OnItemClickListener<T> onItemClickListener;
    OnItemLongClickListener<T> onItemLongClickListener;

    public HelperAdapter(Context _context, List<T> _list) {
        context = _context;
        list = _list;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HelperItemViewHolder) {
            HelperItemViewHolder itemViewHolder = (HelperItemViewHolder) holder;
            final int pos = position;
            final T bean = list.get(pos);
            itemViewHolder.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, pos, bean);
                    }
                }
            };

            itemViewHolder.onLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onItemLongClickListener != null) {
                        return onItemLongClickListener.onItemClick(v, pos, bean);
                    }
                    return false;
                }
            };
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
