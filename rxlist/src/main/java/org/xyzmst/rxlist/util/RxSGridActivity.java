package org.xyzmst.rxlist.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xyzmst.rxlist.R;
import org.xyzmst.rxlist.R2;
import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.SRxListFragmentBind;
import org.xyzmst.rxlist.SRxSubscriber;
import org.xyzmst.rxlist.adapter.RxAdapterBindView;
import org.xyzmst.rxlist.fragment.RxSGrid2ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * @author mac
 * @title LiveBillboardActivity
 * @description
 * @modifier
 * @date
 * @since 16/4/16 11:09
 **/
public abstract class RxSGridActivity<T> extends ActionBarActivity {

    public RxSGrid2ListFragment mFragment;

    @BindView(R2.id.title)
    public TextView mTitle;

    public static final String TITLE = "TITLE";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mFragment = new RxSGrid2ListFragment();
        bindDubFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_wrapper, mFragment).commit();

        String title = getIntent().getStringExtra(TITLE);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    @OnClick(R2.id.cancel_button_wrapper)
    public void back() {
        onBackPressed();
    }


    public void bindDubFragment() {
        SRxListFragmentBind
                .bindView(new RxAdapterBindView() {
                    @Override
                    public int getItemViewType(Object data, int position) {
                        return RxSGridActivity.this.getItemViewType(data, position);
                    }

                    @Override
                    public RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType) {
                        return RxSGridActivity.this.bindView(parent, viewType);
                    }
                })
                .bindFragment(mFragment)
                .subscribe(new SRxSubscriber<T>(mFragment) {

                    @Override
                    public Observable<RxBaseData<T>> bindData() {
                        return RxSGridActivity.this.bindData();
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position) {
                        RxSGridActivity.this.onBindViewHolder(holder, item, position);
                    }
                });


    }

    public abstract int getItemViewType(Object data, int position);

    public abstract RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType);

    public abstract Observable<RxBaseData<T>> bindData();

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);

}
