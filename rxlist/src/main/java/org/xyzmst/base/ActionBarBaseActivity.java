package org.xyzmst.base;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import org.xyzmst.rxlist.R;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;


/**
 * 注意：使用loadingMask时，必须确保activity的根为id==root的RelativeLayout
 * Created by Administrator on 13-10-1.
 */
public class ActionBarBaseActivity extends ActionBarActivity {
    //    private Handler mTextColorHandler = new Handler();
    protected Toolbar mToolbar;
    protected View mContentView;
    private LoadingDialogFragment mLoadingDialogFragment;
    View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
    }

    public final
    @Nullable
    LoadingDialogFragment getLoadingDailog() {
        if (mLoadingDialogFragment == null) {
            LoadingDialogFragment ldf = LoadingDialogUtils.findByTag(getSupportFragmentManager(), getClass().getSimpleName());
            mLoadingDialogFragment = ldf;
        }
        return mLoadingDialogFragment;
    }

    @Deprecated
    public final
    @NonNull
    LoadingDialogFragment showLoadingDialog() {
        return showLoadingDialog("");
    }

    @Deprecated
    public final
    @NonNull
    LoadingDialogFragment showLoadingDialog(String title) {
        LoadingDialogFragment ldf = getLoadingDailog();
        if (ldf == null) {
            mLoadingDialogFragment = ldf = new LoadingDialogFragment();
        }
        if (LoadingDialogUtils.findByTag(getSupportFragmentManager(), getClass().getSimpleName()) == null) {
            LoadingDialogUtils.show(getSupportFragmentManager(), getClass().getSimpleName(), ldf);
        }
        ldf.getArguments().putString(LoadingDialogFragment.KEY_TITLE, title);
        ldf.getArguments().putBoolean(LoadingDialogFragment.KEY_SHOW_PROGRESS_VALUE, false);
        ldf.refresh();
        return ldf;
    }



    @Deprecated
    public final void hideLoadingDialog() {
        if (mLoadingDialogFragment == null) {
            return;
        }

        //如果Fragment还没有附加上，则延迟500ms处理
        if (!mLoadingDialogFragment.isAdded()) {
            AndroidSchedulers.mainThread()
                    .createWorker()
                    .schedule(new HideAction(this), 100, TimeUnit.MILLISECONDS);
        } else {
            mLoadingDialogFragment = null;
            LoadingDialogUtils.hide(getSupportFragmentManager(), getClass().getSimpleName());
        }
    }


    public final synchronized void showLoadingAnim(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView == null) {
                    loadingView = LayoutInflater.from(ActionBarBaseActivity.this).inflate(R.layout.loading_anim_ui, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ViewGroup contentViewParent = (ViewGroup) mContentView.getParent();
                    loadingView.setLayoutParams(params);
                    contentViewParent.addView(loadingView);
                }
                TextView loadingText = (TextView) loadingView.findViewById(R.id.loading_text);
                if (!TextUtils.isEmpty(text)) {
                    loadingText.setText(text);
                }
                TextView progress = (TextView) loadingView.findViewById(R.id.loading_progress);
                progress.setVisibility(View.GONE);
                ImageView loadingImage = (ImageView) loadingView.findViewById(R.id.loading_pic);
                AnimationDrawable loadingImageDrawable = (AnimationDrawable) loadingImage.getDrawable();
                loadingImageDrawable.start();
                viewFadeInAnim(loadingView);
            }
        });

    }

    public final synchronized void showLoadingAnim() {
        cancelable = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView == null) {
                    loadingView = LayoutInflater.from(ActionBarBaseActivity.this).inflate(R.layout.loading_anim_ui, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ViewGroup contentViewParent = (ViewGroup) mContentView.getParent();
                    loadingView.setLayoutParams(params);
                    contentViewParent.addView(loadingView);
                }
                TextView loadingText = (TextView) loadingView.findViewById(R.id.loading_progress);
                loadingText.setVisibility(View.GONE);
                ImageView loadingImage = (ImageView) loadingView.findViewById(R.id.loading_pic);
                AnimationDrawable loadingImageDrawable = (AnimationDrawable) loadingImage.getDrawable();
                loadingImageDrawable.start();
                viewFadeInAnim(loadingView);
            }
        });

    }

    public final synchronized void setLoadingAnimProgress(final int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView == null) {
                    loadingView = LayoutInflater.from(ActionBarBaseActivity.this).inflate(R.layout.loading_anim_ui, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ViewGroup contentViewParent = (ViewGroup) mContentView.getParent();
                    loadingView.setLayoutParams(params);
                    contentViewParent.addView(loadingView);
                }
                TextView loadingText = (TextView) loadingView.findViewById(R.id.loading_progress);
                loadingText.setVisibility(View.VISIBLE);
                loadingText.setText(String.valueOf(progress) + "%");
            }
        });

    }

    public final synchronized void setLoadingAnimText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView == null) {
                    loadingView = LayoutInflater.from(ActionBarBaseActivity.this).inflate(R.layout.loading_anim_ui, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ViewGroup contentViewParent = (ViewGroup) mContentView.getParent();
                    loadingView.setLayoutParams(params);
                    contentViewParent.addView(loadingView);
                }
                TextView loadingText = (TextView) loadingView.findViewById(R.id.loading_text);
                if (!TextUtils.isEmpty(text)) {
                    loadingText.setText(text);
                }
            }
        });

    }

    public final synchronized void hideLoadingAnim() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView == null) {
                    loadingView = LayoutInflater.from(ActionBarBaseActivity.this).inflate(R.layout.loading_anim_ui, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ViewGroup contentViewParent = (ViewGroup) mContentView.getParent();
                    loadingView.setLayoutParams(params);
                    contentViewParent.addView(loadingView);
                    return;
                }
                viewFadeOutAnim(loadingView);
                TextView loadingProgress = (TextView) loadingView.findViewById(R.id.loading_progress);
                loadingProgress.setVisibility(View.GONE);
                loadingProgress.setText(String.valueOf(0) + "%");
                TextView loadingText = (TextView) loadingView.findViewById(R.id.loading_text);
                loadingText.setText("正在加载");
            }
        });
    }

    public void setLoadingStateListener(LoadingStateInterface loadingStateListener) {
        loadingStateInterface = loadingStateListener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private boolean isLoading = false;

    public void viewFadeInAnim(View view) {
        isLoading = true;
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(200);
        view.startAnimation(alphaAnimation);
        view.setVisibility(View.VISIBLE);
    }


    public void viewFadeOutAnim(final View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                isLoading = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    boolean cancelable = true;

    public void setLoadingCancelable(boolean can) {
        cancelable = can;
    }

    public boolean getLoadingCancelable() {
        return cancelable;
    }

    @Override
    public void onBackPressed() {
        if (cancelable && isLoading) {
            hideLoadingAnim();
            if (loadingStateInterface != null) {
                loadingStateInterface.onLoadingCancel();
            }
            return;
        }
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /** 隐藏菜单栏 add by reason*/
        if (null != menu) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        setContentView(R.layout.empty_layout);
        //关闭受托管的LoadingDialog
        hideLoadingDialog();
        super.onDestroy();
    }

    private static class HideAction extends Action0WithWeakHost<ActionBarBaseActivity> {

        public HideAction(ActionBarBaseActivity host) {
            super(host);
        }

        @Override
        public void call() {
            if (isHostExist()) {
                getHost().mLoadingDialogFragment = null;
                LoadingDialogUtils.hide(getHost().getSupportFragmentManager(), getClass().getSimpleName());
                getHost().hideLoadingDialog();
            }
        }
    }

    public Bitmap getViewBitmap(View v) {
        v.clearFocus(); //
        v.setPressed(false); //
        // 能画缓存就返回false
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }


    public void getContentBitmap(final BitmapFetchListener bitmapFetchListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmapFetchListener.onComplete(getViewBitmap(mContentView));

            }
        }).run();
    }

    public interface BitmapFetchListener {
        void onComplete(Bitmap bitmap);
    }


    LoadingStateInterface loadingStateInterface;

    public interface LoadingStateInterface {
        void onLoadingCancel();

        void onLoadingStart();

        void onLoadingComplete();
    }
}
