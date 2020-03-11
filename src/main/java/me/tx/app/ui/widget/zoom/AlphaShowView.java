package me.tx.app.ui.widget.zoom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class AlphaShowView extends FrameLayout {
    public AlphaShowView(@NonNull Context context) {
        this(context,null);
    }

    public AlphaShowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlphaShowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void change(int percent){
        setAlpha(((float)percent)/100);
    }
}
