package me.tx.app.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import me.tx.app.R;
import me.tx.app.ui.actionbar.BaseActionbar;
import me.tx.app.ui.activity.BaseActivity;
import me.tx.app.utils.OneClicklistener;

public class BackTitleTextActionbar extends BaseActionbar {
    public BackTitleTextActionbar(Context context) {
        super(context);
    }

    public BackTitleTextActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackTitleTextActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(BaseActivity activity, String title,String text,OneClicklistener click){
        setBackgroundColor(getResources().getColor(R.color.base));
        setTextColor(R.color.white);
        setTitleColor(R.color.white);
        super.init(new TYPE[]
                        {TYPE.LI, TYPE.T,TYPE.RT}
                ,new int[]{R.drawable.back,0,0},
                new String[]{null,title,text},
                new OneClicklistener[]{new FinishClick(activity),null,click});
    }
}
