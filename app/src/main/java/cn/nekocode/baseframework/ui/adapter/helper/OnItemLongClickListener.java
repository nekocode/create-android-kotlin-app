package cn.nekocode.baseframework.ui.adapter.helper;

import android.view.View;

/**
 * Created by nekocode on 2015/7/23.
 */
public interface OnItemLongClickListener<T> {
    boolean onItemClick(View view, int position, T bean);
}