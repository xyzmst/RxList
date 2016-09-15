package org.xyzmst.rxlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mac
 * @title RxBaseAdapter
 * @description
 * @modifier
 * @date
 * @since 16/4/22 13:27
 **/
public class RxBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RxAdapterAble<T> {

    private List<T> mList = new ArrayList<>();
    private Event mAdapterEvent;
    private RxAdapterBindView<T> mBindView;
    private static final String TAG = "RxBaseAdapter";

    public RxBaseAdapter() {

    }

    public void setAdapterEvent(Event adapterEvent) {
        mAdapterEvent = adapterEvent;
    }

    public void setBindView(RxAdapterBindView<T> bindView) {
        mBindView = bindView;
    }

    @Override
    public int getItemViewType(int position) {
        return mBindView.getItemViewType(mList.get(position), position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: rx");
        return mBindView.bindView(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: rx");
        if (mAdapterEvent != null)
            mAdapterEvent.onBindViewHolder(holder, mList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void clear() {
        mList.clear();
    }

    @Override
    public void addAll(Object all) {
        mList.addAll((Collection<? extends T>) all);
    }

    @Override
    public int size() {
        return mList.size();
    }

    @Override
    public void notifyChanged() {
        notifyDataSetChanged();
    }

    @Override
    public List<T> getList() {
        return mList;
    }


    public interface Event<T> {
        void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);
    }

}
