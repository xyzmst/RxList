package org.xyzmst.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



/**
 * Created by panwenye on 15/1/3.
 */
public class LoadingDialogUtils {

    public static @Nullable
    LoadingDialogFragment findByTag(@NonNull FragmentManager fm, @NonNull String tag){
        Fragment fragmentByTag = fm.findFragmentByTag(tag);
        if(fragmentByTag !=null&&fragmentByTag instanceof LoadingDialogFragment){
            return (LoadingDialogFragment) fragmentByTag;
        }else {
            return null;
        }
    }

    /**
     * 展示指定的LoadingDialogFragment；如果已经存在了，则沿用已有
     * @param fm
     * @param tag
     * @param ldf
     * @return
     */
    public static LoadingDialogFragment show(@NonNull FragmentManager fm, @NonNull String tag, @NonNull LoadingDialogFragment ldf) {

        LoadingDialogFragment dialogFragment = findByTag(fm, tag);
        if(dialogFragment !=null){
            dialogFragment.getArguments().putAll(ldf.getArguments());
            dialogFragment.refresh();
            return dialogFragment;
        }
        FragmentTransaction ft = fm.beginTransaction();
        try {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }catch (Exception e){
            e.printStackTrace();
        }
        ldf.show(ft, tag);
        return ldf;
    }

    public static LoadingDialogFragment show(FragmentManager fm, String tag) {
        return show(fm, tag, new LoadingDialogFragment());
    }

    public static void hide(FragmentManager fm, String tag) {
        Fragment frag = fm.findFragmentByTag(tag);
        if (frag != null && frag instanceof DialogFragment) {
            try {
                ((DialogFragment) frag).dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
