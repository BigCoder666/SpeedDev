package me.tx.app.common.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {

    private float mPicRatio;//图片的宽高比
    public static final int RELATIVE_WIDTH = 0;//控件的宽度固定，根据比例求出高度
    public static final int RELATIVE_HEIGHT = 1;//控件的高度固定，根据比例求出宽度
    private int mRelative = RELATIVE_WIDTH;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, me.tx.app.R.styleable.RatioLayout);
        for (int i = 0; i < array.getIndexCount(); i++) {
            if (i == me.tx.app.R.styleable.RatioLayout_ratio) {
                mPicRatio = array.getFloat(i, 2.43f);

            } else if (i == me.tx.app.R.styleable.RatioLayout_relative) {
                mRelative = array.getInt(i, 0);

            }
        }
        array.recycle();
    }

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    public void setRelative(int relative) {
        mRelative = relative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childWidth = widthSize - getPaddingLeft() - getPaddingRight();
        int childHeight = heightSize - getPaddingBottom() - getPaddingTop();

        if (widthMode == MeasureSpec.EXACTLY && mPicRatio != 0 && mRelative == RELATIVE_WIDTH) {
            //修正高度的值
            childHeight = (int) (childWidth / mPicRatio + 0.5f);
        } else if (heightMode == MeasureSpec.EXACTLY && mPicRatio != 0 && mRelative ==
                RELATIVE_HEIGHT) {
            //修正宽度的值
            childWidth = (int) (childHeight * mPicRatio + 0.5f);
        }
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
        setMeasuredDimension(childWidth + getPaddingLeft() + getPaddingRight(), childHeight +
                getPaddingBottom() + getPaddingTop());

       /* widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth + getPaddingLeft() +
                getPaddingRight(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight + getPaddingBottom() +
                getPaddingTop(), MeasureSpec.EXACTLY);
        super.measure(widthMeasureSpec, heightMeasureSpec);*/
    }
}