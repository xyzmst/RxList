package org.xyzmst.rxlist.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xyzmst.rxlist.R;
import org.xyzmst.rxlist.R2;
import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.SRxListFragmentBind;
import org.xyzmst.rxlist.SRxSubscriber;
import org.xyzmst.rxlist.adapter.RxAdapterBindView;
import org.xyzmst.rxlist.fragment.RxLinearListFragment;

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
public abstract class RxListActivity<T> extends ActionBarActivity {

    public RxLinearListFragment mFragment;

    @BindView(R2.id.title)
    public TextView mTitle;

    public static final String TITLE = "TITLE";

    @BindView(R2.id.cancel_button_wrapper)
    public LinearLayout mCancelButtonWrapper;
    @BindView(R2.id.common_toolbar)
    public RelativeLayout mToolbar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_list);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mFragment = new RxLinearListFragment();
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
                        return RxListActivity.this.getItemViewType(data, position);
                    }

                    @Override
                    public RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType) {
                        return RxListActivity.this.bindView(parent, viewType);
                    }
                })
                .bindFragment(mFragment)
                .subscribe(new SRxSubscriber<T>(mFragment) {

                    @Override
                    public Observable<RxBaseData<T>> bindData() {
                        return RxListActivity.this.bindData();
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position) {
                        RxListActivity.this.onBindViewHolder(holder, item, position);
                    }
                });


    }

    public abstract int getItemViewType(Object data, int position);

    public abstract RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType);

    public abstract Observable<RxBaseData<T>> bindData();

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);

}
