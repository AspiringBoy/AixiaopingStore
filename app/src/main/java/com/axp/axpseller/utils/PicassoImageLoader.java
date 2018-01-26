package com.axp.axpseller.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.axp.axpseller.R;
import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by YY on 2017/9/5.
 */
public class PicassoImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .resize(width, height)//
                .centerInside()//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
