package org.xyzmst.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * @author mac
 * @title
 * @description
 * @modifier
 * @date
 * @since 15/8/17 上午11:15
 */
public abstract class BaseCardFrameCard<T> extends FrameLayout {

    public Context context;
    public View view;
    public int type;

    public BaseCardFrameCard(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseCardFrameCard(Context context, int type) {
        super(context);
        this.context = context;
        init();
    }

    public BaseCardFrameCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init() {
        view = LayoutInflater.from(context).inflate(Rid(), this, true);
        ButterKnife.bind(this, view);
    }

    public abstract int Rid();

    public abstract void setData(T t);

    public LinearLayout.LayoutParams getScaleParasLinearLayout(float scaleWidth, float scaleHight, int margin) {
        int width = getFullWidh() - 2 * dp2px(margin);
        int heigh = (int) (width / (scaleWidth + 0.0) * (scaleHight + 0.0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, heigh);
        params.setMargins(dp2px(margin), dp2px(margin), dp2px(margin), dp2px(margin));
        params.gravity = Gravity.CENTER;
        return params;
    }

    public LayoutParams getScaleParasFrameLayoutLayout(float scaleWidth, float scaleHight, int margin) {
        int width = getFullWidh() - 2 * dp2px(margin);
        int heigh = (int) (width / (scaleWidth + 0.0) * (scaleHight + 0.0));
        LayoutParams params = new LayoutParams(width, heigh);
        params.gravity = Gravity.CENTER;
        return params;
    }

    public RelativeLayout.LayoutParams getScaleParasRelativeLayout(float scaleWidth, float scaleHight, int margin) {
        int width = getFullWidh() - 2 * dp2px(margin);
        int heigh = (int) (width / (scaleWidth + 0.0) * (scaleHight + 0.0));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, heigh);
        return params;
    }

    public int dp2px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    public int getFullWidh() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}
