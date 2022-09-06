package me.tx.app.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import me.tx.app.R;
import me.tx.app.ui.actionbar.BaseActionbar;
import me.tx.app.utils.OneClicklistener;

public class TitleTextActionbar extends BaseActionbar {
    public TitleTextActionbar(Context context) {
        super(context);
    }

    public TitleTextActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleTextActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(String title,String event,OneClicklistener click){
        setBackgroundColor(getResources().getColor(R.color.base));
        setTextColor(R.color.white);
        setTitleColor(R.color.white);
        super.init(new TYPE[]
                        {TYPE.T,TYPE.RT}
                ,new int[]{0,0,},
                new String[]{title,event},
                new OneClicklistener[]{null,click});
    }
}
