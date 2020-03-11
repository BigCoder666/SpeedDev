package me.tx.app.ui.actionbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.tx.app.R;
import me.tx.app.utils.DPPX;
import me.tx.app.utils.OneClicklistener;

public class BaseActionbar extends RelativeLayout {

    public enum TYPE {LT, RT, T, LI, RI}

    public static class FinishClick extends OneClicklistener {
        Activity activity;

        public FinishClick(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void click(View view) {
            activity.finish();
        }
    }

    public int baseHeight = 50;
    public int addHeight = 25;

    int textSize = 16;
    int textColor = R.color.trans;
    int titleSize = 18;
    int titleColor = R.color.trans;
    Typeface titleStyle = Typeface.DEFAULT;
    public boolean needMargin = false;

    boolean userMargin = true;

    public void closeAddMargin() {
        userMargin = false;
    }

    TextView title;
    LinearLayout leftLayout;
    LinearLayout rightLayout;
    LinearLayout centerLayout;

    public void setHeight(int height) {
        baseHeight = height;
    }

    public List<View> leftList;
    public List<View> rightList;

    public void changRithtTxTitle(String title, int pos) {
        ((AppCompatTextView) rightList.get(pos)).setText(title);
    }

    private int getStateBarHeight() {
        if (!userMargin) {
            return 0;
        }
        int result = DPPX.dip2px(getContext(), 25);
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public BaseActionbar(Context context) {
        super(context);
    }

    public BaseActionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseActionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseActionbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(TYPE[] t, int[] id, String[] name, int[] size, int[] color, OneClicklistener[] click) {
        init();
        leftList = new ArrayList<>();
        rightList = new ArrayList<>();
        String title = "";
        for (int i = 0; i < t.length; i++) {
            switch (t[i]) {
                case T: {
                    title = name[i];
                    setTitle(title);
                    break;
                }
                case LT: {
                    TextClick textClick = new TextClick(getContext());
                    if (size[i] == 0 || color[i] == 0) {
                        textClick.init(name[i], textSize, textColor, click[i]);
                    } else {
                        textClick.init(name[i], size[i], color[i], click[i]);
                    }
                    leftList.add(textClick);
                    break;
                }
                case LI: {
                    ImageClick imageClick = new ImageClick(getContext());
                    imageClick.init(id[i], click[i]);
                    leftList.add(imageClick);
                    break;
                }
                case RT: {
                    TextClick textClick = new TextClick(getContext());
                    if (size[i] == 0 || color[i] == 0) {
                        textClick.init(name[i], textSize, textColor, click[i]);
                    } else {
                        textClick.init(name[i], size[i], color[i], click[i]);
                    }
                    rightList.add(textClick);
                    break;
                }
                case RI: {
                    ImageClick imageClick = new ImageClick(getContext());
                    imageClick.init(id[i], click[i]);
                    rightList.add(imageClick);
                    break;
                }
            }
        }
        setLeftView(leftList);
        setRightView(rightList);
    }

    public void init(TYPE[] t, int[] id, String[] name, OneClicklistener[] click) {
        int l = t.length;
        int[] size = new int[l];
        int[] color = new int[l];
        init(t, id, name, size, color, click);
    }

    private void init() {
        addHeight = getStateBarHeight();
        if (((ViewGroup) getParent()) instanceof LinearLayout) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                needMargin = true;
                setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DPPX.dip2px(getContext(), baseHeight) + addHeight));
            } else {
                needMargin = false;
                setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DPPX.dip2px(getContext(), baseHeight)));
            }
        } else if (((ViewGroup) getParent()) instanceof RelativeLayout) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                needMargin = true;
                setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DPPX.dip2px(getContext(), baseHeight) + addHeight));
            } else {
                needMargin = false;
                setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DPPX.dip2px(getContext(), baseHeight)));
            }
        }
    }


    public void setTitleColor(int color) {
        this.titleColor = color;
    }

    public void changeTitleColor(int color) {
        title.setTextColor(color);
    }


    public void setTitleStyle(Typeface style) {
        this.titleStyle = style;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTitle(String titleString) {
        title = new TextView(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, DPPX.dip2px(getContext(), baseHeight));
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(titleStyle);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleSize);
        title.setText(titleString);
        title.setTextColor(getResources().getColor(titleColor));
        addView(title, lp);
    }

    public void changeTitle(String t) {
        title.setText(t);
    }

    public void setLeftView(List<View> viewList) {
        //初始化左侧按钮组
        leftLayout = new LinearLayout(getContext());
        leftLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, DPPX.dip2px(getContext(), baseHeight));
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        leftLayout.setGravity(Gravity.LEFT);
        //初始化左侧按钮
        LinearLayout.LayoutParams leftLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DPPX.dip2px(getContext(), baseHeight));
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i) instanceof ImageClick) {
                leftLayout.addView(viewList.get(i), new ViewGroup.LayoutParams(DPPX.dip2px(getContext(), 50), DPPX.dip2px(getContext(), baseHeight)));
            } else {
                leftLayout.addView(viewList.get(i), leftLp);
            }
        }
        addView(leftLayout, lp);
    }

    public void setRightView(List<View> viewList) {
        //初始化左侧按钮组
        rightLayout = new LinearLayout(getContext());
        rightLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DPPX.dip2px(getContext(), baseHeight));
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rightLayout.setGravity(Gravity.RIGHT);
        //初始化左侧按钮
        LinearLayout.LayoutParams rightLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DPPX.dip2px(getContext(), baseHeight));
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i) instanceof ImageClick) {
                rightLayout.addView(viewList.get(i), new ViewGroup.LayoutParams(DPPX.dip2px(getContext(), 50), DPPX.dip2px(getContext(), baseHeight)));
            } else {
                rightLayout.addView(viewList.get(i), rightLp);
            }
        }
        addView(rightLayout, lp);
    }

    public void setCenterView(View view) {
        //初始化左侧按钮组
        centerLayout = new LinearLayout(getContext());
        centerLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DPPX.dip2px(getContext(), baseHeight));
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.setMargins(DPPX.dip2px(getContext(), 60), 0, DPPX.dip2px(getContext(), 60), 0);
        centerLayout.setGravity(Gravity.CENTER);
        //初始化左侧按钮
        LinearLayout.LayoutParams centerLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DPPX.dip2px(getContext(), baseHeight));
        centerLayout.addView(view, centerLp);

        addView(centerLayout, lp);
    }
}
