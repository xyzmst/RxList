package org.xyzmst.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author mac
 * @title ChannelTabButton
 * @description
 * @modifier
 * @date
 * @since 16/3/16 下午4:06
 **/
public class ViewPagerTab extends LinearLayout {


    @BindView(R.id.content_wrapper)
    LinearLayout mContentWrapper;
    private Context mContext;
    private Event mEvent;

    private ArrayList<ViewPagerTabItem> mList = new ArrayList<>();

    public ViewPagerTab(Context context) {
        this(context, null);
    }

    public ViewPagerTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_tab_button, this, true);
        ButterKnife.bind(this, view);
    }

    public void setEvent(Event event) {
        mEvent = event;
    }

    public void addItem(final String item, final int index) {
        final ViewPagerTabItem tabItem = new ViewPagerTabItem(mContext);
        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        tabItem.setName(item);
        tabItem.setTag(index);
        tabItem.setLayoutParams(layoutParams);
        mList.add(tabItem);
        tabItem.mItemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(index);
            }
        });
        mContentWrapper.addView(tabItem);
    }

    public void addItem(final String item, final int index, final float weight) {
        final ViewPagerTabItem tabItem = new ViewPagerTabItem(mContext);
        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
        tabItem.setName(item);
        tabItem.setTag(index);
        tabItem.setLayoutParams(layoutParams);
        mList.add(tabItem);
        tabItem.mItemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(index);
            }
        });
        mContentWrapper.addView(tabItem);
    }

    public void changeTab(int index) {
        chaneNormal();
        mList.get(index).setSelected(true);
        if (mEvent != null) {
            mEvent.changeTab(index);
        }

    }

    public void chaneNormal() {
        for (ViewPagerTabItem item : mList) {
            item.setSelected(false);
        }
    }

    public interface Event {
        void changeTab(int index);
    }

}
