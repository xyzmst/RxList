package org.xyzmst.demo;

import android.content.Context;
import android.widget.TextView;

import org.xyzmst.rxcardview.util.BaseCardFrameCard;

import butterknife.BindView;


/**
 * @author mac
 * @title SimpleView
 * @description
 * @modifier
 * @date
 * @since 2017/3/8 15:13
 **/
public class SimpleView extends BaseCardFrameCard<String> {


    @BindView(R.id.cotent)
    TextView mCotent;

    public SimpleView(Context context) {
        super(context);
    }

    @Override
    public int Rid() {
        return R.layout.simple_view;
    }

    @Override
    public void setData(String s) {
        mCotent.setText(s);
    }
}
