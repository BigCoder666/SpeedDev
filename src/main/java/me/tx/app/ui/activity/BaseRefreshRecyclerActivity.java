package me.tx.app.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import me.tx.app.R;
import me.tx.app.ui.widget.EmptyHolder;
import me.tx.app.utils.DPPX;

public abstract class BaseRefreshRecyclerActivity<T extends EmptyHolder> extends BaseActivity {


    public boolean isChat = false;

    final String NO_MORE_DATA = "没有更多数据了~";
    final int END = -9999;
    final int EMPTY = -8888;

    EmptyHolder customerEmptyView =null;

    boolean couldLoadMore = true;

    boolean loadMoreState = true;

    public RecyclerView recycler;

    RecyclerView.Adapter<T> adapter;

    public SwipeRefreshLayout swip;

    public final int pageSize = 20;

    int page = 1;

    public void setCustomerEmptyView(EmptyHolder view){
        customerEmptyView = view;
    }

    public interface IResult {
        void empty();
    }

    BaseRefreshRecyclerActivity.IResult iResult = new BaseRefreshRecyclerActivity.IResult() {
        @Override
        public void empty() {
            loadMoreState = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recycler.scrollBy(0, -DPPX.dip2px(BaseRefreshRecyclerActivity.this, 50));
                }
            });
            page--;
//            toast(NO_MORE_DATA);
        }
    };

    public abstract void setRefreshRecyclerActivity();

    public abstract LinearLayoutManager getLayoutManager();

    public abstract int getItemViewType(int position);

    public abstract int getItemCount();

    public abstract T onCreateViewHolder(ViewGroup viewGroup, int type);

    public abstract void onBindViewHolder(T holder, int position);

    public abstract void load(int page, BaseRefreshRecyclerActivity.IResult iResult, boolean needClear);

    public void setUnLoadMore() {
        couldLoadMore = false;
        loadMoreState = false;
    }

    @Override
    public void load() {
        loadMoreState = couldLoadMore;
        page = 1;
        load(page, iResult, true);
    }


    @Override
    public void setView() {
        swip = findViewById(R.id.swipe);
        swip.setColorSchemeResources(R.color.base, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isChat) {
                    page++;
                    load(page, iResult, false);
                    return;
                }
                load();
            }
        });


        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (lastPosition == BaseRefreshRecyclerActivity.this.getItemCount() && BaseRefreshRecyclerActivity.this.getItemCount() >= pageSize) {
                    page++;
                    load(page, iResult, false);
                }
            }
        });
        adapter = new RecyclerView.Adapter<T>() {
            @NonNull
            @Override
            public T onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
                try {
                    if (type == EMPTY) {
                        if(customerEmptyView == null) {
                            return (T) EmptyHolder.EMPTY(BaseRefreshRecyclerActivity.this, viewGroup);
                        }else {
                            return (T) customerEmptyView;
                        }
                    }
                    if (type == END && BaseRefreshRecyclerActivity.this.getItemCount() >= pageSize) {
                        return (T) EmptyHolder.END(BaseRefreshRecyclerActivity.this, viewGroup);
                    }
                    return BaseRefreshRecyclerActivity.this.onCreateViewHolder(viewGroup, type);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void onBindViewHolder(@NonNull T holder, int position) {
                try {
                    if (BaseRefreshRecyclerActivity.this.getItemCount() == 0) {
                        return;
                    } else if (BaseRefreshRecyclerActivity.this.getItemCount() >= pageSize && position == BaseRefreshRecyclerActivity.this.getItemCount()) {
                        return;
                    } else {
                        BaseRefreshRecyclerActivity.this.onBindViewHolder(holder, position);
                    }
                } catch (ClassCastException e) {
                    return;
                }
            }

            @Override
            public int getItemCount() {
                if (BaseRefreshRecyclerActivity.this.getItemCount() == 0) {
                    return 1;
                }
                if (loadMoreState && BaseRefreshRecyclerActivity.this.getItemCount() >= pageSize) {
                    return BaseRefreshRecyclerActivity.this.getItemCount() + 1;
                } else {
                    loadMoreState = false;
                    return BaseRefreshRecyclerActivity.this.getItemCount();
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (BaseRefreshRecyclerActivity.this.getItemCount() == 0) {
                    return EMPTY;
                }
                if (BaseRefreshRecyclerActivity.this.getItemCount() >= pageSize && position == BaseRefreshRecyclerActivity.this.getItemCount()) {
                    return END;
                }
                return BaseRefreshRecyclerActivity.this.getItemViewType(position);
            }
        };
        recycler.setAdapter(adapter);

        setRefreshRecyclerActivity();
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
