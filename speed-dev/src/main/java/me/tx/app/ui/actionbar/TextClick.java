package me.tx.app.ui.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import me.tx.app.utils.DPPX;

public class TextClick extends AppCompatTextView {

    public TextClick(Context context) {
        super(context);
    }

    public TextClick(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextClick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(String text, int size, int colorSrc, OnClickListener click){
        setText(text);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        setTextColor(getResources().getColor(colorSrc));
        setGravity(Gravity.CENTER);
        setPadding(DPPX.dip2px(getContext(),10),0,DPPX.dip2px(getContext(),10),0);
        setOnClickListener(click);
    }
}
