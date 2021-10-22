package me.tx.app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Administrator on 2017/3/6.
 */
public class NoScrollViewPager extends ViewPager {
        public NoScrollViewPager(Context context) {
            super(context);
        }

        public NoScrollViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onTouchEvent(MotionEvent arg0) {
            return false;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            return false;
        }
}
