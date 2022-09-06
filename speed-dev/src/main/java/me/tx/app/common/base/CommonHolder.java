package me.tx.app.common.base;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * @author tx
 * @date 2022/9/6 15:04
 */
public class CommonHolder<T extends ViewBinding> extends RecyclerView.ViewHolder {
    public T hvb;
    public CommonHolder(T t) {
        super(t.getRoot());
        hvb = t;
    }
}
