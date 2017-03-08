package org.xyzmst.demo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.adapter.RxSimpleViewHolder;
import org.xyzmst.rxlist.util.RxListActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class MainActivity extends RxListActivity<String> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public int getItemViewType(Object data, int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType) {
        return new RxSimpleViewHolder(new SimpleView(this));
    }

    @Override
    public Observable<RxBaseData<String>> bindData() {
        RxBaseData<String> rxBaseData = new RxBaseData<String>();
        rxBaseData.list = new ArrayList<String>();
        rxBaseData.list.add("来个复杂点的！！！");
        return Observable.just(rxBaseData).delay(2, TimeUnit.SECONDS);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, String item, int position) {
        ((SimpleView) holder.itemView).setData(item);
    }
}
