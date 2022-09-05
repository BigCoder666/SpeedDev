package me.tx.app.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import me.tx.app.R;
import me.tx.app.ui.actionbar.BaseActionbar;
import me.tx.app.ui.activity.BaseActivity;
import me.tx.app.utils.OneClicklistener;

public class BackTitleActionbar extends BaseActionbar {
    public BackTitleActionbar(Context context) {
        super(context);
    }

    public BackTitleActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackTitleActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(BaseActivity activity, String title){
        setBackgroundColor(getResources().getColor(R.color.base));
        setTextColor(R.color.white);
        setTitleColor(R.color.white);
        super.init(new TYPE[]
                        {TYPE.LI,TYPE.T}
                ,new int[]{R.drawable.back,0,},
                new String[]{null,title,},
                new OneClicklistener[]{new FinishClick(activity),null});
    }
}
