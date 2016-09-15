package org.xyzmst.rxlist.fragment;


import org.xyzmst.rxlist.layoutmanager.ILayoutManager;
import org.xyzmst.rxlist.layoutmanager.RxStaggeredGridLayoutManager;

/**
 * @author mac
 * @title RxSGrid2ListFragment
 * @description
 * @modifier
 * @date
 * @since 16/3/21 15:19
 **/
public class RxSGrid2ListFragment extends RxListFragment {

    @Override
    ILayoutManager getLayoutManager() {
        return new RxStaggeredGridLayoutManager(2, RxStaggeredGridLayoutManager.VERTICAL);
    }
}
