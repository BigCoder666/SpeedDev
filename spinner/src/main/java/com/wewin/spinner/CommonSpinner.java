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


public class CommonSpinner<T extends CommonSpinner.NameId> extends RelativeLayout {
    TextView text;
    ImageView updown;
    RecyclerPop recyclerPop;
    Adapter adapter;

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    boolean singleLine = false;

    ArrayList<T> nameIdArrayList =new ArrayList<>();
    T defult;
    T select;

    public T getSelect(){
        return select;
    }

    public void defultText(){
        select =defult;
        text.setText(defult.getName());
    }

    public interface NameId{
        String getName();
        String getId();
    }

    public interface Adapter<T extends NameId>{
        void click(T t);
        ArrayList<T> getData();
        void onShow();
    }

    public void setAdapter(Adapter adapter,T t){
        this.adapter = adapter;
        this.nameIdArrayList= this.adapter.getData();
        if(t==null) {
            defult = nameIdArrayList.get(0);
        }else {
            defult =t;
        }
        init(Gravity.CENTER);
    }

    public void setAdapter(Adapter adapter,T t, int gravity){
        this.adapter = adapter;
        this.nameIdArrayList= this.adapter.getData();
        if(t==null) {
            defult = nameIdArrayList.get(0);
        }else {
            defult =t;
        }
        init(gravity);
    }

    public CommonSpinner(Context context) {
        super(context);
    }

    public CommonSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    private void init(int gravity){
        removeAllViews();
        text = new TextView(getContext());
        text.setSingleLine(singleLine);
        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        text.setGravity(gravity);
        text.setMaxLines(2);
        text.setEllipsize(TextUtils.TruncateAt.END);
        text.setPadding(DPPX.dip2px(getContext(),30),0, DPPX.dip2px(getContext(),30),0);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        text.setTextColor(getResources().getColor(R.color.black));
        text.setText(defult.getName());
        select =defult;
        addView(text);
        updown = new ImageView(getContext());
        LayoutParams lp =new LayoutParams(DPPX.dip2px(getContext(),30), DPPX.dip2px(getContext(),30));
        lp.setMargins(DPPX.dip2px(getContext(),5),0,0,0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        updown.setLayoutParams(lp);
        updown.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        updown.setImageResource(R.drawable.updown);
        updown.setRotation(0);
        addView(updown);

        recyclerPop =new RecyclerPop(getContext(), new RecyclerPop.IDealList() {
            @Override
            public int getItemCount() {
                return nameIdArrayList.size();
            }

            @Override
            public void setHolder(RecyclerPop.SelectHolder holder, int p) {
                holder.textView.setText(nameIdArrayList.get(p).getName());
            }

            @Override
            public void onClick(int p) {
                select = nameIdArrayList.get(p);
                text.setText(nameIdArrayList.get(p).getName());
                adapter.click(nameIdArrayList.get(p));
            }
        });
        recyclerPop.initPopWindow();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onShow();
                recyclerPop.showPop(CommonSpinner.this);
            }
        });
    }

    public void refresh(){
        recyclerPop.refresh();
    }

    public void clearAll(){
        removeAllViews();
        nameIdArrayList.clear();
        setOnClickListener(null);
    }
}

