package org.xyzmst.rxcardview.card;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.memory.me.R;
import com.memory.me.devices.DisplayAdapter;
import com.memory.me.dto.home.BannerItem;
import com.memory.me.event.AppEvent;
import com.memory.me.ui.discovery.adapter.BannerAdapter;
import com.memory.me.ui.dispatcher.DispatcherActivityUtils;
import com.memory.me.widget.AutoScrollViewPagerEx;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * @author mac
 * @title DHomeBannerView
 * @description
 * @modifier
 * @date
 * @since 16/8/1 14:41
 **/
public class DHomeBannerView extends RelativeLayout {
    @InjectView(R.id.home_banner_viewpager)
    public AutoScrollViewPagerEx bannerViewPager;
    @InjectView(R.id.home_banner_vpindicator)
    public CirclePageIndicator home_banner_vpindicator;
    private Context mContext;
    private View view;
    public static final int AUTO_INTERVAL = 6000;
    private static final String TAG = "DHomeBannerView";
    private int mLastXIntercept;
    private int mLastYIntercept;
    private BannerAdapter bannerViewAdapter;

    public DHomeBannerView(Context context) {
        super(context);
        init(context);
    }

    public DHomeBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.d_home_banner, this, true);
        ButterKnife.inject(this, view);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DisplayAdapter.getWidthPixels() * 298 / 750);
        bannerViewPager.setLayoutParams(lp);
        //里面有一些视图变成无效，调用该方法
        bannerViewPager.requestLayout();
    }


    public void setAdapter(final BannerAdapter adapter) {
        bannerViewAdapter = adapter;
        bannerViewPager.setAdapter(adapter);
        bannerViewPager.setInterval(AUTO_INTERVAL);
        home_banner_vpindicator.setViewPager(bannerViewPager);
        home_banner_vpindicator.setFillColor(Color.parseColor("#ffffff"));
        home_banner_vpindicator.setPageColor(Color.parseColor("#77ffffff"));
        home_banner_vpindicator.setStrokeColor(Color.TRANSPARENT);
        home_banner_vpindicator.setSnap(true);//清除动画
        if (adapter.getCount() > 1)
            bannerViewPager.startAutoScroll();
        bannerViewPager.setOnTouchListener(new OnTouchViewPagerListener());
    }


    private float mStartX;
    private float mStartY;
    private float mEndX;
    private float mEndY;

    private class OnTouchViewPagerListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float minMove = 20;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mStartX = event.getX();
                mStartY = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mEndX = event.getX();
                mEndY = event.getY();
                float distanceX = mEndX - mStartX;
                float distanceY = mEndY - mStartY;
                if (Math.abs(distanceX) <= 15 && Math.abs(distanceY) >= minMove) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }


            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mEndX = event.getX();
                mEndY = event.getY();
                Log.e(TAG, "onTouch:x " + Math.abs(mEndX - mStartX) + ", y=" + Math.abs(mEndY - mStartY));
                if (Math.abs(mEndX - mStartX) < 100 && Math.abs(mEndY - mStartY) < 100) {
                    BannerItem item = bannerViewAdapter.getItem(bannerViewPager.getCurrentItem());
                    if (item != null) {
                        AppEvent.onEvent(AppEvent.discovery_banner_click_9_0);
                        DispatcherActivityUtils.startActivityForData(mContext, item.getTarget().toString());
                    }
                }

            }
            return false;
        }
    }


}
