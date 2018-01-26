package yy.custombanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by YY on 2017/12/7.
 */
public class VpAdapter extends PagerAdapter {

    private Context context;
    private List<String> list;
    private boolean canLoop;
    private IBannerClickListener iBannerClickListener;

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public VpAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount() * 500 : getRealCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String imgPath = null;
        if (!canLoop) {
            imgPath = list.get(position);
        }else {
            imgPath = list.get(position % list.size());
        }
        Glide.with(context).load(imgPath).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iBannerClickListener != null) {
                    iBannerClickListener.onBannerClick(position);
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private int getRealCount() {
        return list == null ? 0 : list.size();
    }

    public void setiBannerClickListener(IBannerClickListener iBannerClickListener) {
        this.iBannerClickListener = iBannerClickListener;
    }

    public interface IBannerClickListener{
        void onBannerClick(int pos);
    }
}
