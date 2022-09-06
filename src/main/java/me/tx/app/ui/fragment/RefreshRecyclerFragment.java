package me.tx.app.ui.fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

import me.tx.app.R;
import me.tx.app.common.base.CommonHolder;
import me.tx.app.databinding.ItemEmptyLayoutBinding;

//recycler id 必须为recycler
public abstract class RefreshRecyclerFragment<VB extends ViewBinding,T extends ViewBinding,K> extends RefreshFragment<VB> {

    final int EMPTY = -8888;

    boolean couldLoadMore = true;

    ArrayList<K> dataList = new ArrayList<>();

    public RecyclerView recycler;

    RecyclerView.Adapter<CommonHolder<T>> adapter;

    public RecyclerView.OnScrollListener onScrollListener;

    public void noScorllListener(){
        recycler.removeOnScrollListener(onScrollListener);
    }

    public final int pageSize = 20;

    public int page = 1;

    public interface IResult {
        void empty();
    }

    public IResult iResult = new IResult() {
        @Override
        public void empty() {
            couldLoadMore = false;
            page--;
        }
    };

    public ArrayList<K> getDataList(){
        return dataList;
    }

    public void addData(List<K> addList){
        dataList.addAll(addList);
    }

    public void resetData(List<K> addList){
        dataList.clear();
        dataList.addAll(addList);
    }

    public void clearData(){
        dataList.clear();
    }
    
    public abstract void setSwipeRecyclerFragment(View view);

    public abstract RecyclerView.LayoutManager getLayoutManager();

    public abstract int getItemViewType(int position);

    public abstract T getHolderBinding(ViewGroup viewGroup,int type);

//    public abstract T onCreateViewHolder(ViewGroup viewGroup, int type);

    public CommonHolder<T> onCreateViewHolder(ViewGroup viewGroup, int type) {
        return new CommonHolder<T>(getHolderBinding(viewGroup,type));
    }

    public abstract void onBindViewHolder(CommonHolder<T> holder, K object, int p);

    public abstract void load(int page, IResult iResult, boolean needClear);

    public void setUnLoadMore() {
        couldLoadMore = false;
    }

    @Override
    public void load() {
        page = 1;
        load(page, iResult, true);
    }

    @Override
    public void setSwipFragment(View view) {
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(getLayoutManager());
        onScrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!couldLoadMore) {
                    return;
                }
                if(swip.isRefreshing()){
                    recycler.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),MotionEvent.ACTION_CANCEL
                            ,0,0,0));
                    return;
                }
                if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if ((lastPosition + 1) == dataList.size() && dataList.size() >= pageSize) {
                        page++;
                        load(page, iResult, false);
                    }
                }else if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    int lastPosition = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPositions(null)[0];
                    if ((lastPosition + 1) == dataList.size() && dataList.size() >= pageSize) {
                        page++;
                        load(page, iResult, false);
                    }
                }
            }
        };
        recycler.addOnScrollListener(onScrollListener);
        adapter = new RecyclerView.Adapter<CommonHolder<T>>() {

            @Override
            public CommonHolder<T> onCreateViewHolder( ViewGroup viewGroup, int type) {
                try {
                    if (type == EMPTY) {
                        return new CommonHolder(ItemEmptyLayoutBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false));
                    }
                    return RefreshRecyclerFragment.this.onCreateViewHolder(viewGroup, type);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void onBindViewHolder(CommonHolder<T> holder, int position) {
                try {
                    if (dataList.size() == 0) {
                        return;
                    } else {
                        RefreshRecyclerFragment.this.onBindViewHolder(holder, dataList.get(position),position);
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return;
                }
            }

            @Override
            public int getItemCount() {
                if (dataList.size() == 0) {
                    return 1;
                } else {
                    return dataList.size();
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (dataList.size() == 0) {
                    return EMPTY;
                }
                return RefreshRecyclerFragment.this.getItemViewType(position);
            }
        };
        recycler.setAdapter(adapter);

        setSwipeRecyclerFragment(view);
    }

    public interface IDealList{
        void dealList();
    }

    public void loadFinish(final IDealList iDealList) {
        if(getActivity()==null){
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(false);
                iDealList.dealList();
                recycler.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void loadFinish() {
        if(getActivity()==null){
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(false);
                recycler.getAdapter().notifyDataSetChanged();
            }
        });
    }
}
