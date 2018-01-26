package com.axp.axpseller.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;

import java.io.File;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by YY on 2017/4/19.
 */
public class MyImageLoader implements ImageLoader {

    private Bitmap.Config config;

    public MyImageLoader(){
        this(Bitmap.Config.RGB_565);
    }

    public MyImageLoader(Bitmap.Config config){
        this.config = config;
    }
    @Override
    public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
       Picasso.with(activity)
                .load(new File(path))
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .config(config)
                .resize(width, height)
                .centerInside()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
