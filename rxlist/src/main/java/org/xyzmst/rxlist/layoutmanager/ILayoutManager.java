package org.xyzmst.rxlist.layoutmanager;

import android.support.v7.widget.RecyclerView;


/**
 * @author mac
 * @title ILayoutManager
 * @description 用于封装 RecyclerView layout的统一处理
 * @modifier
 * @date
 * @since 04/07/16
 **/
public interface ILayoutManager {

    RecyclerView.LayoutManager getLayoutManager();


    int findLastVisiblePosition();
}