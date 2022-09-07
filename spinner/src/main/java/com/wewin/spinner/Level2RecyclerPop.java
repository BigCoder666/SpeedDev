package com.wewin.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Level2RecyclerPop extends BasePopupWindow {

    RecyclerView l1,l2;

    public interface IDealList{
        int getItemCount();
        void setLevel1(SelectHolder holder, int p);
        void setLevel2(SelectHolder holder, CommonLevel2Spinner.NameIdLevel2 nameIdLevel2);
        void onClick(int level1,CommonLevel2Spinner.NameIdLevel2 nameIdLevel2);
        ArrayList<CommonLevel2Spinner.NameIdLevel2> getNextLevel(int p);
    }


    public class SelectHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public SelectHolder( View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    IDealList iDealList;

    public Level2RecyclerPop(Context context, IDealList iDealList) {
        super(context);
        this.iDealList = iDealList;
    }

    @Override
    public int getPopupWindowResourceId() {
        return R.layout.pop_level2_recycler_item_select;
    }

    @Override
    public void popupWindowSet(final View view) {
        l1 = view.findViewById(R.id.l1);
        l2 = view.findViewById(R.id.l2);
        l1.setLayoutManager(new LinearLayoutManager(view.getContext()));
        l2.setLayoutManager(new LinearLayoutManager(view.getContext()));
        l1.setAdapter(new RecyclerView.Adapter<SelectHolder>() {
            @Override
            public SelectHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(l1.getContext()).inflate(R.layout.item_pop_recyler_item,viewGroup,false);
                return new SelectHolder(v);
            }

            @Override
            public void onBindViewHolder( final SelectHolder viewHolder, final int p) {
                iDealList.setLevel1(viewHolder,p);
                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        l2.setVisibility(View.VISIBLE);
                        l2.setAdapter(new RecyclerView.Adapter<SelectHolder>() {
                            @Override
                            public SelectHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
                                View v = LayoutInflater.from(l1.getContext()).inflate(R.layout.item_pop_recyler_item,viewGroup,false);
                                return new SelectHolder(v);
                            }

                            @Override
                            public void onBindViewHolder( final SelectHolder viewHolder, final int position) {
                                iDealList.setLevel2(viewHolder,iDealList.getNextLevel(p).get(position));
                                viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        iDealList.onClick(p,iDealList.getNextLevel(p).get(position));
                                        dismiss();
                                    }
                                });
                            }

                            @Override
                            public int getItemCount() {
                                return iDealList.getNextLevel(p).size();
                            }
                        });
                    }
                });
            }

            @Override
            public int getItemCount() {
                return iDealList.getItemCount();
            }
        });
    }

}
