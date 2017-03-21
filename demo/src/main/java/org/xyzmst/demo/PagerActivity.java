package org.xyzmst.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.xyzmst.base.BaseFragment;
import org.xyzmst.demo.view.SimpleView;
import org.xyzmst.demo.view.ViewPagerTab;
import org.xyzmst.rxlist.RxBaseData;
import org.xyzmst.rxlist.SRxListFragmentBind;
import org.xyzmst.rxlist.SRxSubscriber;
import org.xyzmst.rxlist.adapter.RxAdapterBindView;
import org.xyzmst.rxlist.adapter.RxSimpleViewHolder;
import org.xyzmst.rxlist.fragment.RxLinearListFragment;
import org.xyzmst.rxlist.fragment.RxListFragment;
import org.xyzmst.rxlist.fragment.RxSGrid2ListFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;


/**
 * @author mac
 * @title PagerActivity
 * @description
 * @modifier
 * @date
 * @since 2017/3/8 17:07
 **/
public class PagerActivity extends ActionBarActivity {

    @BindView(R.id.tab)
    ViewPagerTab mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private ArrayList<PagerInfo> fragmentList;
    private FragmentViewPagerAdapter myViewPagerAdapter;
    private HashMap<Object, BaseFragment> fragmentWeakContainer = new HashMap<Object, BaseFragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);
        ButterKnife.bind(this);
        mTab.addItem("RxLinearListFragmentDemo", 0);
        mTab.addItem("RxSGrid2ListFragmentDemo", 1);
        mTab.changeTab(0);
        initViewPager();
    }

    private void initViewPager() {
        fragmentList = new ArrayList<PagerInfo>();
        myViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
        PagerInfo pagerInfo;
        pagerInfo = new PagerInfo(RxLinearListFragment.class);
        myViewPagerAdapter.addItem(pagerInfo);
        pagerInfo = new PagerInfo(RxSGrid2ListFragment.class);
        myViewPagerAdapter.addItem(pagerInfo);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {


        public FragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            final BaseFragment mWeakFragment = fragmentWeakContainer.get(position);
            if (mWeakFragment != null) {
                return mWeakFragment;
            }
            Bundle bundle = new Bundle();
            return BaseFragment.instantiate(PagerActivity.this.getApplicationContext(), fragmentList.get(position).aClass.getName(), bundle);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public BaseFragment instantiateItem(ViewGroup container, final int position) {
            BaseFragment mWeakFragment = fragmentWeakContainer.get(position);
            if (mWeakFragment == null) {
                final RxListFragment fragment = (RxListFragment) super.instantiateItem(container, position);
                initFragmentData(position, fragment);
                return fragment;
            } else {
                return mWeakFragment;
            }
        }

        public void addItem(PagerInfo pagerInfo) {
            fragmentList.add(pagerInfo);
            notifyDataSetChanged();
        }
    }

    private void initFragmentData(final int position, final RxListFragment fragment) {
        SRxListFragmentBind.bindView(new RxAdapterBindView() {
            @Override
            public int getItemViewType(Object data, int position) {
                return 0;
            }

            @Override
            public RecyclerView.ViewHolder bindView(ViewGroup parent, int viewType) {
                return new RxSimpleViewHolder(new SimpleView(PagerActivity.this));
            }
        })
                .bindFragment(fragment)
                .subscribe(new SRxSubscriber<String>(fragment) {
                    @Override
                    public Observable<RxBaseData<String>> bindData() {
                        RxBaseData<String> data = new RxBaseData<String>();
                        data.list = new ArrayList<String>();
                        for (int i = 0; i < 20; i++) {
                            data.list.add(position + "-" + i);
                        }
                        data.count = 50;
                        return Observable.just(data);
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, String item, int position) {
                        ((SimpleView) holder.itemView).setData(item);
                    }
                });
    }

    static class PagerInfo {
        public final Class<?> aClass;

        public PagerInfo(Class<?> aClass) {
            this.aClass = aClass;
        }
    }

}
