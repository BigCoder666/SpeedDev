package me.tx.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


/**
 * Created by tx on 2016/9/1.
 */
public abstract class BasePopupWindow {
    public abstract int getPopupWindowResourceId();
    public abstract void popupWindowSet(View view);
    PopupWindow popupWindow;
    public Context context;
    View popView;
    public BasePopupWindow(Context context){
        this.context=context;
    }

    public void setListener(PopupWindow.OnDismissListener listener){
        popupWindow.setOnDismissListener(listener);
    }

    public boolean isAdd(){
        return popupWindow.isShowing();
    }

    public BasePopupWindow initPopWindow() {
        popView = LayoutInflater.from(context).inflate( getPopupWindowResourceId(),null);
        popupWindowSet(popView);
        popupWindow = new PopupWindow(popView);
        return this;
    }

    public BasePopupWindow initPopWindowDown() {
        View popView = LayoutInflater.from(context).inflate(getPopupWindowResourceId(), null);
        popupWindowSet(popView);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return this;
    }

    public BasePopupWindow showPop(View parent) {
        popupWindow.setWidth(parent.getWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        //设置popwindow显示位置
        popupWindow.showAsDropDown(parent,0,0);
        popupWindow.update();
        return this;
    }

    public BasePopupWindow showPop(View parent,int dp) {
        popupWindow.setWidth(parent.getWidth());
        popupWindow.setHeight(DPPX.dip2px(parent.getContext(),dp));
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        //设置popwindow显示位置
        popupWindow.showAsDropDown(parent,0,DPPX.dip2px(context,2));
        popupWindow.update();
        return this;
    }

    public BasePopupWindow showPopBottom(View mainView) {
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        //设置popwindow显示位置
        popupWindow.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
        popupWindow.update();
        return this;
    }

    public void dismiss() {
        //设置popwindow显示位置
        popupWindow.dismiss();
    }
}
