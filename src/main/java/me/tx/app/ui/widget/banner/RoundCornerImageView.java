package me.tx.app.ui.widget.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import me.tx.app.utils.DPPX;

public class RoundCornerImageView extends AppCompatImageView {
  
    public RoundCornerImageView(Context context) {
        super(context);  
    }  
      
    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
      
    public RoundCornerImageView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);  
    }  
  
    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();  
        int h = this.getHeight();  
        clipPath.addRoundRect(new RectF(0, 0, w, h), DPPX.dip2px(getContext(),5),DPPX.dip2px(getContext(),5), Path.Direction.CW);
        canvas.clipPath(clipPath);  
        super.onDraw(canvas);  
    }  
}  