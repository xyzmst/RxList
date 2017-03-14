package org.xyzmst.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.xyzmst.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author mac
 * @title ViewPagerTabItem
 * @description
 * @modifier
 * @date
 * @since 16/7/25 12:41
 **/
public class ViewPagerTabItem extends FrameLayout {


    @BindView(R.id.item_text)
    TextView mItemText;
    @BindView(R.id.bottom_line)
    View mBottomLine;
    @BindView(R.id.item_layout)
    FrameLayout mItemLayout;
    @BindView(R.id.tab_program_wrapper)
    FrameLayout mTabProgramWrapper;
    private Context mContext;


    public ViewPagerTabItem(Context context) {
        this(context, null);
    }

    public ViewPagerTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_tab_item, this, true);
        ButterKnife.bind(this, view);
    }


    public void setName(String name) {
        mItemText.setText(name);
    }

    public void setSelected(boolean selected) {
        mItemText.setSelected(selected);
        mBottomLine.setSelected(selected);
    }
}
