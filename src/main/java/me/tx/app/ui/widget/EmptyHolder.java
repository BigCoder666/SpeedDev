package me.tx.app.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.tx.app.R;

public class EmptyHolder extends RecyclerView.ViewHolder {
    public EmptyHolder(@NonNull View itemView) {
        super(itemView);
    }
    public static EmptyHolder EMPTY(Context context, ViewGroup viewGroup){
        return new EmptyHolder(LayoutInflater.from(context).inflate(R.layout.item_empty_layout,viewGroup,false));
    }
    public static EmptyHolder END(Context context, ViewGroup viewGroup){
        return new EmptyHolder(LayoutInflater.from(context).inflate(R.layout.item_end_layout,viewGroup,false));
    }
}
