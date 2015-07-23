package cn.nekocode.baseframework.ui.adapter.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by nekocode on 2015/7/23.
 */
public abstract class HelperItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    View.OnClickListener onClickListener;
    View.OnLongClickListener onLongClickListener;

    public HelperItemViewHolder(View view) {
        super(view);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null)
            onClickListener.onClick(v);
    }

    @Override
    public boolean onLongClick(View v) {
        if (onLongClickListener != null)
            return onLongClickListener.onLongClick(v);
        return false;
    }
}
