package org.xyzmst.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.xyzmst.rxlist.R;
import org.xyzmst.rxlist.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingDialogFragment extends DialogFragment {
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_SHOW_PROGRESS_VALUE = "KEY_SHOW_PROGRESS_VALUE";

    @BindView(R2.id.progressBar)
    CircleProgressBar mProgressBar;
    @BindView(R2.id.title)
    TextView mTitle;
    @BindView(R2.id.root)
    RelativeLayout mRoot;
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    public LoadingDialogFragment() {
        setArguments(new Bundle());
        //去除边框
        setStyle(STYLE_NORMAL, R.style.Widget_Mofun_HalfTransparentPanel);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.setContentView(R.layout.simple_loading_dialog);
        ButterKnife.bind(this, dialog.getWindow().getDecorView());
        mProgressBar.setColorSchemeResources(android.R.color.holo_blue_bright);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setTitle(getArguments().getString(KEY_TITLE));
        refresh();
        return dialog;
    }


    /**
     * Set a listener to be invoked when the dialog is canceled.
     * <p>
     * <p>This will only be invoked when the dialog is canceled.
     * Cancel events alone will not capture all ways that
     * the dialog might be dismissed. If the creator needs
     * to know when a dialog is dismissed in general, use
     * {@link #setOnDismissListener}.</p>
     *
     * @param listener The {@link DialogInterface.OnCancelListener} to use.
     */
    public void setOnCancelListener(final DialogInterface.OnCancelListener listener) {
        if (getDialog() != null) {
            getDialog().setOnCancelListener(listener);
        } else {
            mOnCancelListener = listener;
        }
    }

    /**
     * Set a listener to be invoked when the dialog is dismissed.
     *
     * @param listener The {@link DialogInterface.OnDismissListener} to use.
     */
    public void setOnDismissListener(final DialogInterface.OnDismissListener listener) {
        if (getDialog() != null) {
            getDialog().setOnDismissListener(listener);
        } else {
            mOnDismissListener = listener;
        }
    }


    public int getMax() {
        if (mProgressBar == null) {
            return -1;
        }
        return mProgressBar.getMax();
    }

    public void setMax(int max) {
        if (mProgressBar == null) {
            return;
        }
        mProgressBar.setMax(max);
    }

    public int getProgress() {
        if (mProgressBar == null) {
            return -1;
        }
        return mProgressBar.getProgress();
    }

    public void setProgress(int progress) {
        if (mProgressBar == null) {
            return;
        }
        if (getMax() > 0) {
            mProgressBar.setShowProgressText(true);
            mProgressBar.setProgress(progress);
        }
    }

    public final void refresh() {
        if (getDialog() != null) {
            getDialog().setTitle(getArguments().getString(KEY_TITLE));
        }
        if (mProgressBar != null) {
            if (getArguments().getBoolean(KEY_SHOW_PROGRESS_VALUE, false)) {
                mProgressBar.setShowProgressText(true);
            } else {
                mProgressBar.setShowProgressText(false);
            }
        }
        if (mTitle != null) {
            mTitle.setText(getArguments().getString(KEY_TITLE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static class Builder {
        String mTitle = "";
        boolean mIsShowValue;

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder showValue(boolean isShowValue) {
            mIsShowValue = isShowValue;
            return this;
        }

        public LoadingDialogFragment create() {
            LoadingDialogFragment ldf = new LoadingDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, mTitle);
            bundle.putBoolean(KEY_SHOW_PROGRESS_VALUE, mIsShowValue);
            ldf.setArguments(bundle);
            return ldf;
        }
    }
}