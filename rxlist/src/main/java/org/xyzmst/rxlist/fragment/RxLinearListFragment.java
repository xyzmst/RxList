package org.xyzmst.rxlist.fragment;


import org.xyzmst.rxlist.layoutmanager.ILayoutManager;
import org.xyzmst.rxlist.layoutmanager.RxLinearLayoutManager;

/**
 * @author mac
 * @title 线性的 RxListFragment
 * @description
 * @modifier
 * @date
 * @since 16/3/21 15:19
 **/
public class RxLinearListFragment extends RxListFragment {

    @Override
    ILayoutManager getLayoutManager() {
        return new RxLinearLayoutManager(getActivity());
    }
}
