package me.tx.app.ui.actionbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import me.tx.app.ui.activity.BaseActivity;

public class ImageClick extends AppCompatImageView {
    public ImageClick(Context context) {
        super(context);
    }

    public ImageClick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageClick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int src,OnClickListener click){
        setScaleType(ScaleType.CENTER_INSIDE);
        setImageResource(src);
        setOnClickListener(click);
    }

    public void init(String url, BaseActivity activity, OnClickListener click){
        setScaleType(ScaleType.CENTER_INSIDE);
        activity.center.loadImg(url,this);
        setOnClickListener(click);
    }

}
