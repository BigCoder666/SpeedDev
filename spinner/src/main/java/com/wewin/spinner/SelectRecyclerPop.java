package com.wewin.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectRecyclerPop extends BasePopupWindow {

    RecyclerView recyclerView;

    public interface IDealList{
        int getItemCount();
        void setHolder(SelectHolder holder, int p);
        void onClick(SelectHolder holder, int p);
        void onOk();
    }


    public class SelectHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView img;
        public SelectHolder( View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.text);
            img =itemView.findViewById(R.id.img);
        }
    }

    IDealList iDealList;

    public SelectRecyclerPop(Context context, IDealList iDealList) {
        super(context);
        this.iDealList = iDealList;
    }

    @Override
    public int getPopupWindowResourceId() {
        return R.layout.pop_recycler_select_item_select;
    }

    @Override
    public void popupWindowSet(final View view) {
        TextView ok = view.findViewById(R.id.ok);
        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDealList.onOk();
                dismiss();
            }
        });
        recyclerView = view.findViewById(R.id.recycler_pop);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter<SelectHolder>() {

            @Override
            public SelectHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.item_select_pop_recyler_item,viewGroup,false);
                return new SelectHolder(v);
            }

            @Override
            public void onBindViewHolder( final SelectHolder viewHolder, final int p) {
                iDealList.setHolder(viewHolder,viewHolder.getAdapterPosition());
                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iDealList.onClick(viewHolder,viewHolder.getAdapterPosition());
                    }
                });
            }

            @Override
            public int getItemCount() {
                return iDealList.getItemCount();
            }
        });
    }

    public void refresh(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
