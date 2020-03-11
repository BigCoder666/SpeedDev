package me.tx.app.ui.widget.zoom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import me.tx.app.R;

public class ZoomView extends LinearLayout {
    ZoomHeaderView zoomHeaderView;
    ZoomContainerView zoomContainerView;
    TypedArray ta;
    int percent = 100;
    IPercentChange iPercentChange;

    public interface IPercentChange{
        void percent(int percent);
    }

    public void setPercentChangeListener(IPercentChange iPercentChange) {
        this.iPercentChange = iPercentChange;
    }

    public enum ZOOMWAY{UP,DOWN,LEFT,RIGHT}

    public int activeLimit = 0;
    public int firstX,firstY =0;
    public int lastX,lastY =0;
    public ZOOMWAY zoomWay =null;

    public ZoomView(Context context) {
        this(context, null, 0);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ta = context.obtainStyledAttributes(attrs, R.styleable.ZoomView);
    }

    public void init(){
        if(getChildCount()!=2){
            throw new InflateException("zoom child view count != 2");
        }
        switch (ta.getInt(R.styleable.ZoomView_zoom_type,3)){
            case 1:{//LEFT
                setOrientation(HORIZONTAL);
                if(!(getChildAt(0) instanceof ZoomHeaderView)){
                    throw new InflateException("TYPE LEFT NEED CHILD 0 == ZoomHeaderView");
                }
                if(!(getChildAt(1) instanceof ZoomContainerView)){
                    throw new InflateException("TYPE LEFT NEED CHILD 1 == ZoomContainerView");
                }
                zoomHeaderView = (ZoomHeaderView) getChildAt(0);
                zoomHeaderView.init();
                zoomContainerView =(ZoomContainerView) getChildAt(1);
                zoomWay=ZOOMWAY.LEFT;
                break;
            }
            case 2:{//RIGHT
                setOrientation(HORIZONTAL);
                if(!(getChildAt(0) instanceof ZoomContainerView)){
                    throw new InflateException("TYPE RIGHT NEED CHILD 0 == ZoomContainerView");
                }
                if(!(getChildAt(1) instanceof ZoomHeaderView)){
                    throw new InflateException("TYPE RIGHT NEED CHILD 1 == ZoomHeaderView");
                }
                zoomHeaderView = (ZoomHeaderView) getChildAt(1);
                zoomHeaderView.init();
                zoomContainerView =(ZoomContainerView) getChildAt(0);
                zoomWay=ZOOMWAY.RIGHT;
                break;
            }
            case 3:{//UP
                setOrientation(VERTICAL);
                if(!(getChildAt(0) instanceof ZoomHeaderView)){
                    throw new InflateException("TYPE UP NEED CHILD 0 == ZoomHeaderView");
                }
                if(!(getChildAt(1) instanceof ZoomContainerView)){
                    throw new InflateException("TYPE UP NEED CHILD 1 == ZoomContainerView");
                }
                zoomHeaderView = (ZoomHeaderView) getChildAt(0);
                zoomHeaderView.init();
                zoomContainerView =(ZoomContainerView) getChildAt(1);
                zoomWay=ZOOMWAY.UP;
                break;
            }
            case 4:{//DOWN
                setOrientation(VERTICAL);
                if(!(getChildAt(0) instanceof ZoomContainerView)){
                    throw new InflateException("TYPE DOWN NEED CHILD 0 == ZoomContainerView");
                }
                if(!(getChildAt(1) instanceof ZoomHeaderView)){
                    throw new InflateException("TYPE DOWN NEED CHILD 1 == ZoomHeaderView");
                }
                zoomHeaderView = (ZoomHeaderView) getChildAt(1);
                zoomHeaderView.init();
                zoomContainerView =(ZoomContainerView) getChildAt(0);
                zoomWay=ZOOMWAY.DOWN;
                break;
            }
            default:{
                setOrientation(VERTICAL);
                if(!(getChildAt(0) instanceof ZoomHeaderView)){
                    throw new InflateException("TYPE UP NEED CHILD 0 == ZoomHeaderView");
                }
                if(!(getChildAt(1) instanceof ZoomContainerView)){
                    throw new InflateException("TYPE UP NEED CHILD 1 == ZoomContainerView");
                }
                zoomHeaderView = (ZoomHeaderView) getChildAt(0);
                zoomHeaderView.init();
                zoomContainerView =(ZoomContainerView) getChildAt(1);
                zoomWay=ZOOMWAY.DOWN;
                return;
            }
        }
    }

    public void Hide(){
        if(zoomWay==ZOOMWAY.UP||zoomWay==ZOOMWAY.DOWN) {
            percent = onResizeHeader(zoomHeaderView.getLayoutParams().height);
        }else {
            percent = onResizeHeader(zoomHeaderView.getLayoutParams().width);
        }
    }

    public void Show(){
        if(zoomWay==ZOOMWAY.UP||zoomWay==ZOOMWAY.DOWN) {
            percent = onResizeHeader(-zoomHeaderView.startH);
        }else {
            percent = onResizeHeader(-zoomHeaderView.startW);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        super.dispatchTouchEvent(event);
        if(!zoomHeaderView.ZoomCanResize()){
            return false;
        }
        if(!zoomContainerView.isContainerCanScorll()){
            return false;
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            firstX=(int)event.getRawX();
            firstX=(int)event.getRawY();
            lastX=(int)event.getRawX();
            lastY=(int)event.getRawY();
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(zoomWay==ZOOMWAY.UP||zoomWay==ZOOMWAY.DOWN){
                if(Math.abs(firstY - (int)event.getRawY())>activeLimit){
                    percent = onResizeHeader(lastY - (int)event.getRawY());
                    if(iPercentChange!=null){
                        iPercentChange.percent(percent);
                    }
                    lastY = (int)event.getRawY();
                    return true;
                }else {
                    return false;
                }
            }else {
                if(Math.abs(firstX - (int)event.getRawX())>activeLimit){
                    percent = onResizeHeader(lastX - (int)event.getRawX());
                    if(iPercentChange!=null){
                        iPercentChange.percent(percent);
                    }
                    lastX = (int)event.getRawX();
                    return true;
                }else {
                    return false;
                }
            }
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            if(zoomWay==ZOOMWAY.UP||zoomWay==ZOOMWAY.DOWN) {
                touchFinish(lastY - (int)event.getRawY());
            }else {
                touchFinish(lastX - (int)event.getRawX());
            }
            lastY=0;
            lastX=0;
            firstX=0;
            firstY=0;
            return true;
        }
        return false;
    }

    private int onResizeHeader(int value){
        return zoomHeaderView.onResize(zoomWay,value);
    }

    private void touchFinish(int value){
        zoomHeaderView.onFinishResize(zoomWay,value);
    }
}
