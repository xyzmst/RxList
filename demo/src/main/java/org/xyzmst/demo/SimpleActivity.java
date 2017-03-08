package org.xyzmst.demo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.util.RxListActivity;

import rx.Observable;


/**
 * @author mac
 * @title SimpleActivity
 * @description
 * @modifier
 * @date
 * @since 2017/3/8 12:18
 **/
public class SimpleActivity extends RxListActivity<String> {
    @Override
    public int getItemViewType(Object data, int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public Observable<RxBaseData<String>> bindData() {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, String item, int position) {

    }
}
