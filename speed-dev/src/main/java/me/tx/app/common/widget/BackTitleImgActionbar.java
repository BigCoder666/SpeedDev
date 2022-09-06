package me.tx.app.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import me.tx.app.R;
import me.tx.app.ui.actionbar.BaseActionbar;
import me.tx.app.ui.activity.BaseActivity;
import me.tx.app.utils.OneClicklistener;

public class BackTitleImgActionbar extends BaseActionbar {
    public BackTitleImgActionbar(Context context) {
        super(context);
    }

    public BackTitleImgActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackTitleImgActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(BaseActivity activity, String title,int imgid,OneClicklistener click){
        setBackgroundColor(getResources().getColor(R.color.base));
        setTextColor(R.color.white);
        setTitleColor(R.color.white);
        super.init(new TYPE[]
                        {TYPE.LI, TYPE.T,TYPE.RI}
                ,new int[]{R.drawable.back,0,imgid},
                new String[]{null,title,null},
                new OneClicklistener[]{new FinishClick(activity),null,click});
    }
}
