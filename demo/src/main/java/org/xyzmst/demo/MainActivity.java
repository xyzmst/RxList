package org.xyzmst.demo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.SRxListFragmentBind;
import org.xyzmst.rxlist.SRxSubscriber;
import org.xyzmst.rxlist.adapter.RxAdapterBindView;
import org.xyzmst.rxlist.adapter.RxSimpleViewHolder;
import org.xyzmst.rxlist.util.RxListActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class MainActivity extends RxListActivity {

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
                        TextView textView = new TextView(MainActivity.this);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        textView.setLayoutParams(layoutParams);
                        return new RxSimpleViewHolder(textView);
                    }
                })
                .bindFragment(mFragment)
                .subscribe(new SRxSubscriber<String>(mFragment) {
                    @Override
                    public Observable<RxBaseData<String>> bindData() {
                        RxBaseData<String> rxBaseData = new RxBaseData<String>();
                        rxBaseData.list = new ArrayList<String>();
                        for (int i = 0; i < 50; i++) {
                            rxBaseData.list.add(mFragment.cursor+i + "");
                        }
                        rxBaseData.count = 200;
                        return Observable.just(rxBaseData).delay(2, TimeUnit.SECONDS);
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, String item, int position) {
                        ((TextView) holder.itemView).setText(item);
                    }
                });
    }
}
