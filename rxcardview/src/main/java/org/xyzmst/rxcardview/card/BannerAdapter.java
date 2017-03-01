package org.xyzmst.rxcardview.card;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Random;
import org.xyzmst.rxcardview.R;

/**
 * @author mac
 * @title BannerAdapter
 * @description banner adapter
 * @modifier
 * @date
 * @since 16/8/4 14:37
 **/
public class BannerAdapter extends PagerAdapter {

    private List<BannerItem> mList;
    private Context mContext;

    public BannerAdapter(Context context, List<BannerItem> list) {
        mContext = context;
        mList = list;
    }


    public BannerItem getItem(int position) {
        if (mList != null && position < mList.size() && position >= 0) {
            return mList.get(position);
        }
        return null;
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView view = makeView(ColorfulPlaceHolder.getPlaceHolderRIDByPosition(position));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (DisplayAdapter.getWidthPixels() * 298.0/ 750.0));
        view.setLayoutParams(lp);
        final BannerItem dataItem = mList.get(position);
        view.setTag(dataItem);
        if (dataItem != null) {
            Picasso.with(mContext)
                    .load(dataItem.getThumbnail_720x405())
                    .placeholder(R.drawable.pic_16x9)
                    .error(R.drawable.pic_16x9)
                    .into(view);

        }

        container.addView(view);
        return view;
    }


    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= 0 && position < mList.size())
            container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return (mList == null) ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ImageView makeView(int rid) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(rid);
        imageView.setId(new Random().nextInt(Integer.MAX_VALUE));
        return imageView;
    }
}
