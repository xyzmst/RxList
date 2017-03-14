package org.xyzmst.demo.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mac
 * @title AlbumSelectAdapter
 * @description 主要用于gridview 的adpater
 * @modifier
 * @date
 * @since 16/8/22 12:11
 **/
public abstract class BaseCardAdapter<T, V extends View> extends BaseAdapter {

    public List<T> mList = new ArrayList<>();
    public Context mContext;

    public BaseCardAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = setConvertView();
            mViewHolder = new ViewHolder((V) convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList.get(position) != null) {
            setData(mList.get(position), position);
        }
        return convertView;
    }

    /**
     * 添加卡片
     *
     * @return
     */
    public abstract View setConvertView();

    /**
     * 注入数据
     *
     * @param t
     * @param position
     */
    public abstract void setData(T t, int position);

    public ViewHolder mViewHolder;

    public class ViewHolder {
        public V mCard;

        private ViewHolder(V view) {
            mCard = view;
        }
    }
}
