package me.tx.app.ui.widget.zoom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AlphaHideView extends FrameLayout {
    public AlphaHideView(@NonNull Context context) {
        this(context,null);
    }

    public AlphaHideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlphaHideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAlpha(0);
    }

    public void change(int percent){
        setAlpha(1-(((float)percent)/100));
    }
}
