package me.tx.app.ui.widget.zoom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import me.tx.app.R;

public class ZoomHeaderView extends FrameLayout {

    int startW,startH=0;
    boolean canResize=true;
    int percent = 100;
    int minValue =0;

    AlphaHideView alphaHideView;
    AlphaShowView alphaShowView;

    public boolean ZoomCanResize(){
        return canResize;
    }

    public ZoomHeaderView(@NonNull Context context) {
        this(context,null);
    }

    public ZoomHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    @SuppressLint("ResourceType")
    public ZoomHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] systemAttrs = {android.R.attr.layout_height,android.R.attr.layout_width};
        TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
        startH = a.getDimensionPixelSize(0, 0);
        startW = a.getDimensionPixelSize(1, 0);
        TypedArray ta =context.obtainStyledAttributes(attrs, R.styleable.ZoomView);
        minValue =(int)ta.getDimension(R.styleable.ZoomView_header_min_value,0);
    }

    public void init(){
        for(int i =0;i<getChildCount();i++){
            if(getChildAt(i) instanceof AlphaShowView){
                alphaShowView = (AlphaShowView) getChildAt(i);
            }
            if(getChildAt(i) instanceof AlphaHideView){
                alphaHideView = (AlphaHideView) getChildAt(i);
            }
        }
    }

    private void onFinishDownAnimation(){
        boolean again = getLayoutParams().height<startH;
        if(again){
            canResize=false;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    percent = onResize(ZoomView.ZOOMWAY.DOWN,-30);
                    if(((ZoomView)getParent()).iPercentChange!=null){
                        ((ZoomView)getParent()).iPercentChange.percent(percent);
                    }
                    onFinishDownAnimation();
                }
            }, 10);
        }else {
            canResize=true;
        }
    }

    private void onFinishUpAnimation(){
        boolean again = getLayoutParams().height>minValue;
        if(again){
            canResize=false;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    percent =onResize(ZoomView.ZOOMWAY.DOWN,30);
                    if(((ZoomView)getParent()).iPercentChange!=null){
                        ((ZoomView)getParent()).iPercentChange.percent(percent);
                    }
                    onFinishUpAnimation();
                }
            }, 10);
        }else {
            canResize=true;
        }
    }

    private void onFinishLeftAnimation(){
        boolean again = getLayoutParams().width>minValue;
        if(again){
            canResize=false;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    percent =onResize(ZoomView.ZOOMWAY.LEFT,30);
                    if(((ZoomView)getParent()).iPercentChange!=null){
                        ((ZoomView)getParent()).iPercentChange.percent(percent);
                    }
                    onFinishLeftAnimation();
                }
            }, 10);
        }else {
            canResize=true;
        }
    }

    private void onFinishRightAnimation(){
        boolean again = getLayoutParams().width<startW;
        if(again){
            canResize=false;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    percent =onResize(ZoomView.ZOOMWAY.RIGHT,30);
                    if(((ZoomView)getParent()).iPercentChange!=null){
                        ((ZoomView)getParent()).iPercentChange.percent(percent);
                    }
                    onFinishLeftAnimation();
                }
            }, 10);
        }else {
            canResize=true;
        }
    }

    public void onFinishResize(ZoomView.ZOOMWAY zoomway, int value){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)getLayoutParams();
        if(zoomway== ZoomView.ZOOMWAY.DOWN||zoomway== ZoomView.ZOOMWAY.UP){
            if((startH-minValue)>=2*(layoutParams.height-minValue)){
                //up
                onFinishUpAnimation();
            }else {
                //down
                onFinishDownAnimation();
            }
        }else{
            if((startW-minValue)>=2*(layoutParams.weight-minValue)){
                //LEFT
                onFinishLeftAnimation();
            }else {
                //RIGHT
                onFinishRightAnimation();
            }
        }
    }

    public int onResize(ZoomView.ZOOMWAY zoomway, int value){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)getLayoutParams();
        if(zoomway== ZoomView.ZOOMWAY.DOWN||zoomway== ZoomView.ZOOMWAY.UP){
            layoutParams.height =  layoutParams.height - value;
            if(layoutParams.height<minValue){
                layoutParams.height = minValue;
            }
            if(layoutParams.height>startH){
                layoutParams.height = startH;
            }
            setLayoutParams(layoutParams);
            percent = (int)(((float)(layoutParams.height-minValue)/(float)(startH-minValue))*100);
            changeAlpha();
            return percent;
        }else{
            layoutParams.width =  layoutParams.width - value;
            if(layoutParams.width<minValue){
                layoutParams.width = minValue;
            }
            if(layoutParams.weight>startW){
                layoutParams.width = startW;
            }
            setLayoutParams(layoutParams);
            percent = (int)(((float)(layoutParams.width-minValue)/(float)(startW-minValue))*100);
            return percent;
        }
    }

    public void changeAlpha(){
        if(alphaHideView!=null){
            alphaHideView.change(percent);
        }
        if(alphaShowView!=null){
            alphaShowView.change(percent);
        }
    }
}
