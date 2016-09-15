package org.xyzmst.rxlist.util;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import org.xyzmst.base.ActionBarBaseActivity;
import org.xyzmst.rxlist.R;
import org.xyzmst.rxlist.R2;
import org.xyzmst.rxlist.fragment.RxLinearListFragment;

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
public abstract class RxListActivity extends ActionBarBaseActivity {

    public RxLinearListFragment mFragment;

    @BindView(R2.id.title)
    public TextView mTitle;

    public static final String TITLE = "TITLE";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_list);
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


    public abstract void bindDubFragment();

}
