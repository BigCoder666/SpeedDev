package me.tx.app.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import me.tx.app.R;
import me.tx.app.ui.widget.EmptyHolder;
import me.tx.app.utils.DPPX;

//recycler id 必须为recycler
public abstract class RefreshRecyclerFragment<T extends EmptyHolder> extends RefreshFragment {

    final int EMPTY = -8888;

    boolean couldLoadMore = true;

    public RecyclerView recycler;

    RecyclerView.Adapter<T> adapter;

    public final int pageSize = 20;

    int page = 1;

    public interface IResult {
        void empty();
    }

    IResult iResult = new IResult() {
        @Override
        public void empty() {
            couldLoadMore = false;
            page--;
        }
    };

    public abstract void setSwipeRecyclerFragment(View view);

    public abstract LinearLayoutManager getLayoutManager();

    public abstract int getItemViewType(int position);

    public abstract int getItemCount();

    public abstract T onCreateViewHolder(ViewGroup viewGroup, int type);

    public abstract void onBindViewHolder(T holder, int position);

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
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!couldLoadMore) {
                    return;
                }
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if ((lastPosition + 1) == RefreshRecyclerFragment.this.getItemCount() && RefreshRecyclerFragment.this.getItemCount() >= pageSize) {
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
                        return (T) EmptyHolder.EMPTY(getContext(), viewGroup);
                    }
                    return RefreshRecyclerFragment.this.onCreateViewHolder(viewGroup, type);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void onBindViewHolder(@NonNull T holder, int position) {
                try {
                    if (RefreshRecyclerFragment.this.getItemCount() == 0) {
                        return;
                    } else {
                        RefreshRecyclerFragment.this.onBindViewHolder(holder, position);
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return;
                }
            }

            @Override
            public int getItemCount() {
                if (RefreshRecyclerFragment.this.getItemCount() == 0) {
                    return 1;
                } else {
                    return RefreshRecyclerFragment.this.getItemCount();
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (RefreshRecyclerFragment.this.getItemCount() == 0) {
                    return EMPTY;
                }
                return RefreshRecyclerFragment.this.getItemViewType(position);
            }
        };
        recycler.setAdapter(adapter);

        setSwipeRecyclerFragment(view);
    }


    @Override
    public void loadFinish() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(false);
                recycler.getAdapter().notifyDataSetChanged();
            }
        });
    }
}
