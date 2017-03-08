package org.xyzmst.rxlist.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.TextView;

import org.xyzmst.rxlist.R;
import org.xyzmst.rxlist.R2;
import org.xyzmst.rxlist.fragment.RxSGrid2ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author mac
 * @title LiveBillboardActivity
 * @description
 * @modifier
 * @date
 * @since 16/4/16 11:09
 **/
public abstract class RxSGridActivity extends ActionBarActivity {

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


    public abstract void bindDubFragment();

}
