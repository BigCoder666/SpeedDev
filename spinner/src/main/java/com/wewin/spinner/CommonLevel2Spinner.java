package com.wewin.spinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CommonLevel2Spinner extends RelativeLayout {
    TextView text;
    ImageView updown;
    Level2RecyclerPop recyclerPop;
    Adapter adapter;

    public ArrayList<NameIdLevel2> data = null;

    NameIdLevel2 defult;

    public String getText(){
        return text.getText().toString();
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    boolean singleLine = false;

    public interface NameIdLevel2 {
        String getName();

        String getId();

        int getLevel();

        ArrayList<NameIdLevel2> getNextLevelList();
    }

    public interface Adapter {
        void click(NameIdLevel2 n1,NameIdLevel2 n2);

        ArrayList<NameIdLevel2> getData();
    }

    public void defultText(){
        text.setText(defult.getName());
    }

    public void setAdapter(Adapter adapter, NameIdLevel2 d) {
        this.adapter = adapter;
        this.data = this.adapter.getData();
        if (d == null) {
            defult = new NameIdLevel2() {
                @Override
                public String getName() {
                    return "";
                }

                @Override
                public String getId() {
                    return "";
                }

                @Override
                public int getLevel() {
                    return 0;
                }

                @Override
                public ArrayList<NameIdLevel2> getNextLevelList() {
                    return new ArrayList<>();
                }
            };
        } else {
            defult = d;
        }
        init();
    }

    public CommonLevel2Spinner(Context context) {
        super(context);
    }

    public CommonLevel2Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    private void init() {
        removeAllViews();
        text = new TextView(getContext());
        text.setSingleLine(singleLine);
        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        text.setGravity(Gravity.CENTER);
        text.setMaxLines(2);
        text.setEllipsize(TextUtils.TruncateAt.END);
        text.setMinHeight(DPPX.dip2px(getContext(),40));
        text.setPadding(0, 0, DPPX.dip2px(getContext(), 30), 0);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        text.setTextColor(getResources().getColor(R.color.black));
        text.setText(defult.getName());
        addView(text);
        updown = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(DPPX.dip2px(getContext(), 30), DPPX.dip2px(getContext(), 30));
        lp.setMargins(DPPX.dip2px(getContext(), 5), 0, 0, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        updown.setLayoutParams(lp);
        updown.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        updown.setImageResource(R.drawable.back_black);
        updown.setRotation(-90);
        addView(updown);

        recyclerPop = new Level2RecyclerPop(getContext(), new Level2RecyclerPop.IDealList() {
            @Override
            public int getItemCount() {
                return data.size();
            }

            @Override
            public void setLevel1(Level2RecyclerPop.SelectHolder holder, int p) {
                holder.textView.setText(data.get(p).getName());
            }

            @Override
            public void setLevel2(Level2RecyclerPop.SelectHolder holder, NameIdLevel2 nameIdLevel2) {
                holder.textView.setText(nameIdLevel2.getName());
            }

            @Override
            public void onClick(int p1,NameIdLevel2 nameIdLevel2) {
                text.setText(data.get(p1).getName()+"→"+nameIdLevel2.getName());
                adapter.click(data.get(p1),nameIdLevel2);
            }

            @Override
            public ArrayList<NameIdLevel2> getNextLevel(int p) {
                return data.get(p).getNextLevelList();
            }
        });
        recyclerPop.initPopWindow();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerPop.showPop(CommonLevel2Spinner.this);
            }
        });
    }

}
