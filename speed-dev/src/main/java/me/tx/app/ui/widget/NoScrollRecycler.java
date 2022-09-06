package me.tx.app.ui.widget;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class NoScrollRecycler extends RecyclerView {
    public NoScrollRecycler(Context context) {
        super(context);
    }
    public NoScrollRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        layout.setAutoMeasureEnabled(true);
        setHasFixedSize(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        if(getTag()!=null){
            //Log.e(getTag().toString(),heightMeasureSpec+"/"+expandSpec+"/"+getId());
        }
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}