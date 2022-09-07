package com.makeid.wait_button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author tx
 * @date 2022/11/3 15:07
 */
public class WButton extends androidx.appcompat.widget.AppCompatButton {
    boolean done = true;

    public interface OnDoneListener {
        void done(View v);
    }

    public WButton(@NonNull Context context) {
        super(context);
    }

    public WButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if(done) {
            super.setOnClickListener(l);
            done = false;
        }
    }

    public void done(){
        done = true;
    }
}
