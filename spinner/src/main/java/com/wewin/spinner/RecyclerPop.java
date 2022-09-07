package com.wewin.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerPop extends BasePopupWindow {

    RecyclerView recyclerView;

    public interface IDealList{
        int getItemCount();
        void setHolder(SelectHolder holder, int p);
        void onClick(int p);
    }


    public class SelectHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SelectHolder( View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    IDealList iDealList;

    public RecyclerPop(Context context, IDealList iDealList) {
        super(context);
        this.iDealList = iDealList;
    }

    public void select(int p){
        iDealList.onClick(p);
    }

    @Override
    public int getPopupWindowResourceId() {
        return R.layout.pop_recycler_item_select;
    }

    @Override
    public void popupWindowSet(final View view) {
        recyclerView = view.findViewById(R.id.recycler_pop);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter<SelectHolder>() {

            @Override
            public SelectHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.item_pop_recyler_item,viewGroup,false);
                return new SelectHolder(v);
            }

            @Override
            public void onBindViewHolder( final SelectHolder viewHolder, final int p) {
                iDealList.setHolder(viewHolder,viewHolder.getAdapterPosition());
                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iDealList.onClick(viewHolder.getAdapterPosition());
                        dismiss();
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
