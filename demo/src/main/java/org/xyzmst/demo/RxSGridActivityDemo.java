package org.xyzmst.demo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.xyzmst.demo.view.SimpleView;
import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.SRxListFragmentBind;
import org.xyzmst.rxlist.SRxSubscriber;
import org.xyzmst.rxlist.adapter.RxAdapterBindView;
import org.xyzmst.rxlist.adapter.RxSimpleViewHolder;
import org.xyzmst.rxlist.util.RxSGridActivity;

import java.util.ArrayList;

import rx.Observable;


/**
 * @author mac
 * @title RxSGridActivityDemo
 * @description
 * @modifier
 * @date
 * @since 2017/3/12 22:55
 **/
public class RxSGridActivityDemo extends RxSGridActivity {


    @Override
    public void bindDubFragment() {
        SRxListFragmentBind
                .bindView(new RxAdapterBindView() {
                    @Override
                    public int getItemViewType(Object data, int position) {
                        return 0;
                    }

                    @Override
                    public RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType) {
                        return new RxSimpleViewHolder(new SimpleView(RxSGridActivityDemo.this));
                    }
                }).bindFragment(mFragment)
                .subscribe(new SRxSubscriber<String>(mFragment) {
                    @Override
                    public Observable<RxBaseData<String>> bindData() {
                        RxBaseData<String> rxBaseData = new RxBaseData<String>();
                        rxBaseData.list = new ArrayList<String>();
                        for (int i = 0; i < 40; i++) {
                            rxBaseData.list.add(mFragment.cursor / 40 + "" + i);
                        }
                        rxBaseData.count = 100;
                        return Observable.just(rxBaseData);
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, String item, int position) {
                        ((SimpleView) holder.itemView).setData(item);
                    }
                });
    }
}
