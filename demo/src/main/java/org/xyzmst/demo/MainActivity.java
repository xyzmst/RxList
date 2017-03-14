package org.xyzmst.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.xyzmst.demo.view.SimpleView;
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
        mTitle.setText("");
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
        rxBaseData.list.add("PagerActivity");
        rxBaseData.list.add("RxScridActivity");
        return Observable.just(rxBaseData);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final String item, final int position) {
        ((SimpleView) holder.itemView).setData(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (position == 0) {
                    intent = new Intent(MainActivity.this, PagerActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, RxSGridActivityDemo.class);
                }
                startActivity(intent);
            }
        });


    }
}
