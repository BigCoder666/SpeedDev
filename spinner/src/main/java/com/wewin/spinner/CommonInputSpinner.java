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


public class CommonInputSpinner<T extends CommonInputSpinner.NameId> extends RelativeLayout {
    TextView text;
    ImageView updown;
    InputRecyclerPop recyclerPop;
    CommonInputSpinner.Adapter adapter;
    T defult = null;

    public T getSelect() {
        return defult;
    }

    public interface NameId {
        String getName();

        String getId();
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    boolean singleLine = false;

    ArrayList<T> nameIdArrayList;

    public interface Adapter<T> {
        void click(T nameId,int pos);

        ArrayList<T> getData();
    }

    public void setAdapter(CommonInputSpinner.Adapter adapter, T t) {
        this.adapter = adapter;
        this.nameIdArrayList = this.adapter.getData();
        this.defult = t;
        init();
    }

    ArrayList<T> list;

    public CommonInputSpinner(Context context) {
        super(context);
    }

    public CommonInputSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    private void init() {
        removeAllViews();
        text = new TextView(getContext());
        text.setHint("请选择olt名称");
        text.setSingleLine(singleLine);
        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        text.setGravity(Gravity.CENTER);
        text.setMaxLines(2);
        text.setMinHeight(DPPX.dip2px(getContext(), 40));
        text.setEllipsize(TextUtils.TruncateAt.END);
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
        updown.setImageResource(R.drawable.ic_spinner_right);
        addView(updown);

        list = new ArrayList<>();
        list.addAll(nameIdArrayList);
        recyclerPop = new InputRecyclerPop(getContext(), new InputRecyclerPop.IDealList() {
            @Override
            public void onSelectChange(String input) {
                list.clear();
                for (T ni : nameIdArrayList) {
                    if (ni.getName().contains(input)) {
                        list.add(ni);
                    }
                }
                recyclerPop.recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public int getItemCount() {
                return list.size();
            }

            @Override
            public void setHolder(InputRecyclerPop.SelectHolder holder, int p) {
                holder.textView.setText(list.get(p).getName());
            }

            @Override
            public void onClick(int p) {
                defult = list.get(p);
                text.setText(list.get(p).getName());
                adapter.click(list.get(p),p);
            }
        });
        recyclerPop.initPopWindow();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerPop.showPop(CommonInputSpinner.this);
            }
        });
    }

    public void selectItemByIndex(int pos) {
        text.setText(list.get(pos).getName());
    }
}
