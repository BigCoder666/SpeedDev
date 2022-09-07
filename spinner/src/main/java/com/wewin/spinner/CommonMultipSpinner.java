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


public class CommonMultipSpinner<T extends CommonMultipSpinner.NameId> extends RelativeLayout {
    TextView text;
    ImageView updown;
    SelectRecyclerPop recyclerPop;
    Adapter adapter;
    int src_true=0,src_false = 0;

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    boolean singleLine = false;

    ArrayList<T> nameIdArrayList =new ArrayList<>();
    String defultText;
    ArrayList<T> select = new ArrayList<>();

    public ArrayList<T> getSelect(){
        return select;
    }

    public interface NameId{
        String getName();
        String getId();
    }

    public interface Adapter<T extends NameId>{
        void change(ArrayList<T> select);
        ArrayList<T> getData();
        void onShow();
    }

    public void setAdapter(Adapter adapter,String defultText,int src_true,int src_false){
        this.adapter = adapter;
        this.nameIdArrayList= this.adapter.getData();
        this.defultText = defultText;
        this.src_true = src_true;
        this.src_false = src_false;
        init();
    }

    public CommonMultipSpinner(Context context) {
        super(context);
    }

    public CommonMultipSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    private void init(){
        removeAllViews();
        text = new TextView(getContext());
        text.setSingleLine(singleLine);
        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        text.setGravity(Gravity.CENTER);
        text.setMaxLines(2);
        text.setEllipsize(TextUtils.TruncateAt.END);
        text.setPadding(0,0, DPPX.dip2px(getContext(),30),0);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        text.setTextColor(getResources().getColor(R.color.black));
        text.setText(defultText);
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

        recyclerPop =new SelectRecyclerPop(getContext(), new SelectRecyclerPop.IDealList() {
            @Override
            public int getItemCount() {
                return nameIdArrayList.size();
            }

            @Override
            public void setHolder(SelectRecyclerPop.SelectHolder holder, int p) {
                holder.textView.setText(nameIdArrayList.get(p).getName());
                boolean selectThis = false;
                for(T t:select){
                    if(t.getId().equals(nameIdArrayList.get(p).getId())){
                        selectThis = true;
                    }
                }
                if(selectThis){
                    holder.img.setImageResource(src_true);
                }else {
                    holder.img.setImageResource(src_false);
                }
            }

            @Override
            public void onClick(SelectRecyclerPop.SelectHolder holder,int p) {
                for(T t:select){
                    if(t.getId().equals(nameIdArrayList.get(p).getId())){
                        select.remove(t);
                        adapter.change(select);
                        refresh();
                        return;
                    }
                }
                select.add(nameIdArrayList.get(p));
                adapter.change(select);
                refresh();
            }

            @Override
            public void onOk() {
                String result = "";
                for(T t:select){
                    result = result + t.getName()+";";
                }
                text.setText(result);
            }
        });
        recyclerPop.initPopWindow();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onShow();
                recyclerPop.showPop(CommonMultipSpinner.this);
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

