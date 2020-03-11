package me.tx.app.ui.widget.zoom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ZoomContainerView extends FrameLayout {
    boolean caScorll = true;

    public boolean isContainerCanScorll(){
        return caScorll;
    }

    public ZoomContainerView(@NonNull Context context) {
        this(context,null);
    }

    public ZoomContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
