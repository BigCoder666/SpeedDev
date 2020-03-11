package me.tx.app.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RecyclePagerAdapter<T, H extends RecyclePagerAdapter.PagerHolder> extends PagerAdapter {

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    private final WeakReference<Context> mContext;

    private final List<T> mInfos;

    private final View[] mCacheView;

    //不删除记号，用于destroyItem时不清除view
    private final Set<Integer> mUnRemoveTags = new HashSet<>();

    public RecyclePagerAdapter(Context context, List<T> infos) {
        this(context, infos, 1);
    }

    /**
     * @param pagerLimit 预加载一侧页数（即预加载总数为pagerLimit * 2）
     */
    public RecyclePagerAdapter(Context context, List<T> infos, int pagerLimit) {
        if(infos == null)
            throw new NullPointerException();
        if(pagerLimit < 1)
            pagerLimit = 1;
        this.mContext = new WeakReference<>(context);
        this.mInfos = infos;
        //(pagerLimit * 2)(预加载数) + 1(当前页) + 1(缓存页)
        mCacheView = new View[(pagerLimit + 1) * 2];
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //获取缓存下标
        int index = position % mCacheView.length;

        T info = mInfos.get(position);
        H holder;

        if(mCacheView[index] == null){
            holder = onCreaterPagerHolder(mContext.get(), position);
            mCacheView[index] = holder.view;
        }else{
            holder = (H) mCacheView[index].getTag();
        }

        //获取容器是否存在该View
        int i = container.indexOfChild(holder.view);
        if(i != -1)
            //存在则记录缓存下标
            mUnRemoveTags.add(index);
        else
            container.addView(holder.view);

        onBindPagerHolde(holder, info, position);

        return holder.view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int index = position % mCacheView.length;
        //检查是否在不删除标记内，是则不移除view，否则移除view
        if(!mUnRemoveTags.contains(index)) {
            View view = mCacheView[index];
            onReleasePagerHolde((H) view.getTag(), position);
            container.removeView(view);
        }else {
            mUnRemoveTags.remove(index);
        }
    }

    /**
     * 创建一个Holder
     * @param position 页面下标
     * @return holder
     */
    protected abstract H onCreaterPagerHolder(Context context, int position);

    /**
     * 绑定页面数据
     * @param info 页面数据
     * @param position 页面下标
     */
    protected abstract void onBindPagerHolde(H holder, T info, int position);

    /**
     * 用于释放页面destroyItem调用且移除时调用
     */
    protected void onReleasePagerHolde(H holder, int position){}

    public List<T> getPagerInfos(){
        return mInfos;
    }

    public Context getContext(){
        return mContext.get();
    }

    public static class PagerHolder{
        public final View view;
        public PagerHolder(View view) {
            view.setTag(this);
            this.view = view;
        }
    }
}