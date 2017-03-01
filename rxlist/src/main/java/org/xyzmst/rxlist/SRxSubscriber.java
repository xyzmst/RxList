package org.xyzmst.rxlist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.xyzmst.rxlist.adapter.RxAdapterData;
import org.xyzmst.rxlist.fragment.RxListFragment;


/**
 * @author mac
 * @title SRxSubscriber
 * @description
 * @modifier
 * @date
 * @since 16/4/23 18:49
 **/
public abstract class SRxSubscriber<T> extends RxListBaseDataSubscriber<T> {

    private static final String TAG = "SRxSubscriber";

    public SRxSubscriber(RxListFragment fragment) {
        super(fragment);
    }

    @Override
    final public void doOnNext(Object item) {
        if (item != null && item instanceof RxAdapterData) {
            RxAdapterData<T> data = (RxAdapterData) item;
            onBindViewHolder(data.holder, data.data, data.position);
        } else {
            Log.e(TAG, "doOnNext: doOnNext");
            super.doOnNext(item);
        }
    }


    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);


}
