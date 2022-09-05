package me.tx.app.ui.activity;

import android.animation.ValueAnimator;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.tx.app.R;
import me.tx.app.ui.widget.EmptyHolder;
import me.tx.app.utils.DPPX;

public abstract class BaseRefreshRecyclerActivity<T extends EmptyHolder,K> extends BaseActivity {

    final int END = -9999;
    final int EMPTY = -8888;

    ArrayList<K> dataList = new ArrayList<>();

    EmptyHolder customerEmptyView =null;

    boolean couldLoadMore = true;

    public RecyclerView recycler;

    RecyclerView.Adapter<T> adapter;

    public SwipeRefreshLayout swip;

    public final int pageSize = 20;

    int page = 1;

    int lastY = 0;
    int startY = 0;
    boolean isTopShow = true;

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

    public void setCustomerEmptyView(EmptyHolder view){
        customerEmptyView = view;
    }

    public interface IResult {
        void empty();
    }

    BaseRefreshRecyclerActivity.IResult iResult = new BaseRefreshRecyclerActivity.IResult() {
        @Override
        public void empty() {
            noMore();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(page>1) {
//                        center.toast("没有更多数据了");
//                    }
//                    recycler.smoothScrollBy(0, -DPPX.dip2px(BaseRefreshRecyclerActivity.this, 50));
//                    page--;
//                }
//            });
//            toast(NO_MORE_DATA);
        }
    };

    private void noMore(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(page>1) {
                    center.toast("没有更多数据了");
                }
                recycler.smoothScrollBy(0, -DPPX.dip2px(BaseRefreshRecyclerActivity.this, 50));
                page--;
            }
        });
    }

    public abstract void setRefreshRecyclerActivity();

    public abstract LinearLayoutManager getLayoutManager();

    public abstract int getItemViewType(int position);

    public abstract T onCreateViewHolder(ViewGroup viewGroup, int type);

    public abstract void onBindViewHolder(T holder, K object, int p);

    public abstract void load(int page, BaseRefreshRecyclerActivity.IResult iResult, boolean needClear);

    public abstract ScrollConnectView scorllConnectView();

    public class ScrollConnectView{
        public View view;
        public int trueHeightDp = 0;
        public int upByHidePx = 300;
        public int downByShowPx = 100;
    }

    public void setUnLoadMore() {
        couldLoadMore = false;
    }

    @Override
    public void load() {
        page = 1;
        load(page, iResult, true);
    }


    @Override
    public void setView() {
        swip = findViewById(R.id.swipe);
        swip.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });


        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(getLayoutManager());
        final ScrollConnectView scrollConnectView = scorllConnectView();
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(swip.isRefreshing()){
                    recycler.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),MotionEvent.ACTION_CANCEL
                    ,0,0,0));
                    return;
                }
                lastY = lastY +dy;
                if(scrollConnectView!=null) {
                    if(lastY - startY>scrollConnectView.upByHidePx && isTopShow == true){
                        ValueAnimator anim = ValueAnimator.ofFloat(DPPX.dip2px(BaseRefreshRecyclerActivity.this,scrollConnectView.trueHeightDp), 0);
                        anim.setDuration(200);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                ViewGroup.LayoutParams layoutParams = scrollConnectView.view.getLayoutParams();
                                layoutParams.height = ((Float) animation.getAnimatedValue()).intValue();
                                scrollConnectView.view.setLayoutParams(layoutParams);
                            }
                        });
                        hideSystemKeyBoard();
                        anim.start();
                        isTopShow = false;
                        startY = lastY;
                    }else if(lastY - startY >scrollConnectView.upByHidePx && isTopShow ==false){
                        startY = lastY;
                    }

                    if(lastY - startY<-scrollConnectView.downByShowPx && isTopShow == false){
                        ValueAnimator anim = ValueAnimator.ofFloat(0,DPPX.dip2px(BaseRefreshRecyclerActivity.this,scrollConnectView.trueHeightDp));
                        anim.setDuration(200);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                ViewGroup.LayoutParams layoutParams = scrollConnectView.view.getLayoutParams();
                                layoutParams.height = ((Float) animation.getAnimatedValue()).intValue();
                                scrollConnectView.view.setLayoutParams(layoutParams);
                            }
                        });
                        hideSystemKeyBoard();
                        anim.start();
                        isTopShow = true;
                        startY = lastY;
                    }else if(lastY - startY <-scrollConnectView.downByShowPx && isTopShow ==true){
                        startY = lastY;
                    }
                }
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (lastPosition == dataList.size() && dataList.size() >= pageSize) {
                    if(swip.isRefreshing()){
                        return;
                    }
                    page++;
                    load(page, iResult, false);
                }
            }
        });
        adapter = new RecyclerView.Adapter<T>() {

            @Override
            public T onCreateViewHolder( ViewGroup viewGroup, int type) {
                try {
                    if (type == EMPTY) {
                        if(customerEmptyView == null) {
                            return (T) EmptyHolder.EMPTY(BaseRefreshRecyclerActivity.this, viewGroup);
                        }else {
                            return (T) customerEmptyView;
                        }
                    }
                    if (type == END && dataList.size() >= pageSize) {
                        return (T) EmptyHolder.END(BaseRefreshRecyclerActivity.this, viewGroup);
                    }
                    return BaseRefreshRecyclerActivity.this.onCreateViewHolder(viewGroup, type);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void onBindViewHolder( T holder, int position) {
                try {
                    if (dataList.size() == 0) {
                        return;
                    } else if (dataList.size() >= pageSize && position == dataList.size()) {
                        return;
                    } else {
                        BaseRefreshRecyclerActivity.this.onBindViewHolder(holder, dataList.get(position),position);
                    }
                } catch (ClassCastException e) {
                    return;
                }
            }

            @Override
            public int getItemCount() {
                if (dataList.size() == 0) {
                    return 1;
                }
                if (couldLoadMore && dataList.size() >= pageSize) {
                    return dataList.size() + 1;
                } else {
                    return dataList.size();
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (dataList.size() == 0) {
                    return EMPTY;
                }
                if (dataList.size() >= pageSize && position == dataList.size()) {
                    return END;
                }
                return BaseRefreshRecyclerActivity.this.getItemViewType(position);
            }
        };
        recycler.setAdapter(adapter);

        setRefreshRecyclerActivity();
    }

    public interface IDealList{
        void dealList();
    }

    public void loadFinish(final IDealList iDealList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(false);
                iDealList.dealList();
                recycler.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void loadFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(false);
                recycler.getAdapter().notifyDataSetChanged();
            }
        });
    }

}
