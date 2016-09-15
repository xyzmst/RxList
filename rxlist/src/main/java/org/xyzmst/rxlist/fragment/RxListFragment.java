package org.xyzmst.rxlist.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.xyzmst.base.BaseFragment;
import org.xyzmst.rxlist.R;
import org.xyzmst.rxlist.R2;
import org.xyzmst.rxlist.adapter.RxAdapterAble;
import org.xyzmst.rxlist.layoutmanager.ILayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author mac
 * @title RxListFragment
 * @description
 * @modifier
 * @date
 * @since 16/3/21 15:19
 **/
public abstract class RxListFragment extends BaseFragment {


    @BindView(R2.id.recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R2.id.swipeLayout)
    public SwipeRefreshLayout mSwipeLayout;
    @BindView(R2.id.loading_image)
    ImageView mLoadingImage;
    @BindView(R2.id.loading_image_wrapper)
    RelativeLayout mLoadingImageWrapper;
    public int PAGE_COUNT = 20;
    public int TOTAL_COUNT = 0;
    public int cursor = 0;
    public boolean mIsLoadingMore = true;
    public RxAdapterAble adapter;
    @BindView(R2.id.root_view)
    public FrameLayout mRootView;
    private Event mEvent;
    ILayoutManager mLayoutManager;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    private int mBColor = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(R.layout.channel_fragment);
        ButterKnife.bind(this, getFragmentViewRoot());
        return getFragmentViewRoot();
    }

    public void setPadding(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    public void setBackgroundColor(int color) {
        mBColor = color;
        if (mRecyclerView != null)
            mRecyclerView.setBackgroundColor(color);
    }


    @Override
    protected void onFragmentLayoutInit() {
        ButterKnife.bind(this, getFragmentViewRoot());

        AnimationDrawable loadingImageDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
        loadingImageDrawable.start();
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {
                cursor = 0;
                mSwipeLayout.setRefreshing(true);
                fetchChannelMicrobologs();

            }
        });

        mLayoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager.getLayoutManager());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = mLayoutManager.findLastVisiblePosition();
                int totalItemCount = mLayoutManager.getLayoutManager().getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动

                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    if (mIsLoadingMore) {
                        //加载操作
                        fetchChannelMicrobologs();
                        //防止加载两次的情况
                        mIsLoadingMore = false;
                    }
                }
            }
        });

    }

    /**
     * 指定分布模式
     *
     * @return
     */
    abstract ILayoutManager getLayoutManager();


    @Override
    protected void onFragmentDataBind() {
        if (mRecyclerView != null) {
            mRecyclerView.setPadding(mLeft, mTop, mRight, mBottom);
            if (mBColor != -1) {
                mRecyclerView.setBackgroundColor(mBColor);
            }
            if (mEvent != null) {
                mRecyclerView.setAdapter((RecyclerView.Adapter) adapter);
            }
        }
        fetchChannelMicrobologs();

    }

    public RxAdapterAble bindAdapter(RxAdapterAble adapter) {
        this.adapter = adapter;
        return adapter;
    }


    private void fetchChannelMicrobologs() {
        if (mEvent != null) {
            mEvent.next();
        }
    }


    public void setEvent(Event event) {
        mEvent = event;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface Event {
        void next();
    }


    /**
     * 隐藏loading和停止刷新
     */
    public void stopLoadingOrRfresh() {
        if (mLoadingImageWrapper != null)
            mLoadingImageWrapper.setVisibility(View.GONE);
        if (mSwipeLayout != null)
            mSwipeLayout.setRefreshing(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor = 0;
    }
}
