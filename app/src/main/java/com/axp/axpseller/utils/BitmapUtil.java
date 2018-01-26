package com.axp.axpseller.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by YY on 2017/9/5.
 */
public class BitmapUtil {

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    //保存图片
    public static void savePNG_After(Bitmap bitmap, String name) {
        File file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //缩放
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    //等比缩放图片
    public static Bitmap scaleImg(String filePath, int destWidth, int destHeight) {
        /**********************第一次采样，目的就是为了计算缩放比例***************************************/
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置只加载图像的边界到内存中
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //获取原图的宽度
        int outWidth = options.outWidth;
        //获取原图的高度
        int outHeight = options.outHeight;
        Log.d("sang", "btnClick: width:" + outWidth + ";height:" + outHeight);
        //缩放比例，该参数的值必须为2的n次幂
        int simpleSize = 1;
        while (outWidth / simpleSize > destWidth || outHeight / simpleSize > destHeight) {
            simpleSize *= 2;
        }
        /*****************************二次采样开始*******************************/
        //设置不仅只加载图片边界
        options.inJustDecodeBounds = false;
        //设置缩略图缩放比例
        options.inSampleSize = simpleSize;
        //设置图像的色彩模式,四种取值：
        //Bitmap.Config.ALPHA_8 加载只有透明度的图片，在这种色彩模式下，一个像素点占一个字节，图片所占内存大小：宽*高*1
        //Bitmap.Config.ARGB_4444   这种色彩模式下，透明度、红、绿、蓝各占4位，一个像素点占2个字节，图片所占内存大小：宽*高*2
        //Bitmap.Config.ARGB_8888（默认）   这种色彩模式下，透明度、红、绿、蓝各占8位，一个像素点占4个字节，图片所占内存大小：宽*高*4
        //Bitmap.Config.RGB_565 这种色彩模式下，红、绿、蓝各占5、6、5位，一个像素点占2个字节，图片所占内存大小：宽*高*2
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;
            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }

        return result;
    }

    private Bitmap cutPic(Bitmap srcBtm, int destLength) {
        if (srcBtm == null || destLength <= 0) {
            return null;
        }
        Bitmap result = null;
        int scaleWidth, scaleHeight;

        return result;
    }

    /**
     *  * 相片按相框的比例动态缩放
     *  * @param context 
     *  * @param 要缩放的图片
     *  * @param width 模板宽度
     *  * @param height 模板高度
     *  * @return
     *  
     */
    public static Bitmap upImageSize(Context context, Bitmap bmp, int width, int height) {
        if (bmp == null) {
            return null;
        }
        // 计算比例
        float scaleX = (float) width / bmp.getWidth();// 宽的比例
        float scaleY = (float) height / bmp.getHeight();// 高的比例
        //新的宽高
        int newW = 0;
        int newH = 0;
        if (scaleX > scaleY) {
            newW = (int) (bmp.getWidth() * scaleX);
            newH = (int) (bmp.getHeight() * scaleX);
        } else if (scaleX <= scaleY) {
            newW = (int) (bmp.getWidth() * scaleY);
            newH = (int) (bmp.getHeight() * scaleY);
        }
        return Bitmap.createScaledBitmap(bmp, newW, newH, true);
    }

    /**
     * 根据图片url生成bitmap(需要在子线程执行)
     * @param url
     * @return
     */
    public Bitmap returnBitMap(final String url) {
        Bitmap bitmap = null;
        URL imageurl = null;
        try {
            imageurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
