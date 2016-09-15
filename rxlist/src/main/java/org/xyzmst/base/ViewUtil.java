package org.xyzmst.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Administrator on 14-1-10.
 */
public class ViewUtil {
    /**
     * 一次性执行onGlobalLayoutListener
     *
     * @param onGlobalLayoutListener
     */
    public static void onGlobalLayoutOnce(final View view, final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view == null) {
            throw new IllegalArgumentException("view can not be null");
        }
        if (onGlobalLayoutListener != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                onGlobalLayoutOnce16Old(view, onGlobalLayoutListener);
            } else {
                onGlobalLayoutOnce16(view, onGlobalLayoutListener);
            }
        }
    }

    @TargetApi(16)
    static void onGlobalLayoutOnce16(final View view, final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view == null) {
            throw new IllegalArgumentException("view can not be null");
        }
        if (onGlobalLayoutListener != null) {
            ViewTreeObserver.OnGlobalLayoutListener tempListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override

                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    onGlobalLayoutListener.onGlobalLayout();
                }
            };
            view.getViewTreeObserver().addOnGlobalLayoutListener(tempListener);
        }
    }

    static void onGlobalLayoutOnce16Old(final View view, final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view == null) {
            throw new IllegalArgumentException("view can not be null");
        }
        if (onGlobalLayoutListener != null) {
            ViewTreeObserver.OnGlobalLayoutListener tempListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override

                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    onGlobalLayoutListener.onGlobalLayout();
                }
            };
            view.getViewTreeObserver().addOnGlobalLayoutListener(tempListener);
        }
    }


//    public static
}
