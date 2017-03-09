package org.xyzmst.demo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.adapter.RxSimpleViewHolder;
import org.xyzmst.rxlist.util.RxListActivity;

import java.util.ArrayList;

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
        rxBaseData.list.add("来个复杂点的列表！！！");
        rxBaseData.list.add("viewpager");
        rxBaseData.count = 2;
        return Observable.just(rxBaseData);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final String item, final int position) {
        ((SimpleView) holder.itemView).setData(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "xxx", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
