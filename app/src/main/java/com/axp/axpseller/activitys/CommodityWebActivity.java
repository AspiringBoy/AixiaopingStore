package com.axp.axpseller.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.axp.axpseller.Constants;
import com.axp.axpseller.ContextParameter;
import com.axp.axpseller.ImageUp;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.AXPWebView;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Dong on 2017/1/13.
 */
public class CommodityWebActivity extends BaseActivity {

    private static final int REQUEST_CODE_GALLERY = 0;
    Toolbar mToolbar;

    AXPWebView mAxpWebView;
    WebView mWebView;
    SpinKitView mSpinKitView;
    RelativeLayout rl;
    public static final String KEY_LOAD_URL = "load_url";
    public static final String KEY_TITLE = "title";
    String str;

    /**
     * 页面正在加载的URL
     */
    String loadUrl = "http://seller.aixiaoping.com/?id="+ ContextParameter.getUserInfo().getAdminuserId();
//    String loadUrl = "http://test.aixiaoping.com/?id=" + ContextParameter.getUserInfo().getAdminuserId();
    /**
     * 网页标题
     */
    String title;
    MultiImageSelector selector = MultiImageSelector.create(CommodityWebActivity.this);
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private ArrayList<String> mSelectPath_new = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
                //触发点击事件，获得相对与绝对路径
//                selector.single();
                // selector.count(9);
//                selector.start(CommodityWebActivity.this, 2);


                /*FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(8).setEnableCamera(true).setEnableCrop(true).setEnableEdit(true).build();
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<cn.finalteam.galleryfinal.model.PhotoInfo> resultList) {
                        mSelectPath.clear();
                        mSelectPath_new.clear();
                        for (PhotoInfo photoInfo : resultList) {
                            mSelectPath.add(photoInfo.getPhotoPath());
                        }
                        for (int i = 0; i < mSelectPath.size(); i++) {
                            String s = mSelectPath.get(i);
                            String new_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/axp" + i + "new.jpg";
                            Bitmap newBtm = BitmapUtil.centerSquareScaleBitmap(BitmapFactory.decodeFile(s), 120);
                            degree = BitmapUtil.readPictureDegree(s);
                            if (degree != 0) {
                                newBtm = BitmapUtil.rotaingImageView(degree, BitmapFactory.decodeFile(s));
//                                FileUtils.compressImage(new_path);
//                                BitmapUtil.savePNG_After(bitmap, new_path);
//                                mSelectPath_new.add(new_path);
                            } *//*else if (degree == 0) {
                                mSelectPath_new.add(s);
                            }*//*
                            BitmapUtil.savePNG_After(newBtm,new_path);
                            mSelectPath_new.add(new_path);
                        }
                        updateImg();
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });*/

                Intent ImgIntent = new Intent(CommodityWebActivity.this, ImageGridActivity.class);
                startActivityForResult(ImgIntent,10);
            } else if (msg.what == 0x122) {
                CommodityWebActivity.this.finish();
            }
        }
    };
    private int degree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.aixiaoping.library.R.layout.activity_web);

        initView();
        initData();
        initListener();
        mWebView.addJavascriptInterface(new InJavaScriptGetBody(),
                "android");
        mWebView.loadUrl(loadUrl);
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if (getIntent().getExtras() == null || StringUtils.isEmpty(getIntent().getExtras().getString(KEY_TITLE, null))) {
                    mToolbar.setTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mSpinKitView.setVisibility(View.VISIBLE);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSpinKitView.setVisibility(View.GONE);
                rl.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });
    }

    private void initView() {
        rl = (RelativeLayout) findViewById(net.aixiaoping.library.R.id.rl);
        mToolbar = (Toolbar) findViewById(net.aixiaoping.library.R.id.tool_bar);
        mToolbar.setVisibility(View.GONE);
        //setSupportActionBar(mToolbar);
        mAxpWebView = (AXPWebView) findViewById(net.aixiaoping.library.R.id.axpwv_webview);
        mSpinKitView = (SpinKitView) findViewById(net.aixiaoping.library.R.id.spin_kit);
        mWebView = mAxpWebView.getWebView();
    }

    private void initData() {
        if (getIntent().getExtras() != null) {
            loadUrl = getIntent().getExtras().getString(KEY_LOAD_URL);
            title = getIntent().getExtras().getString(KEY_TITLE, "网页");
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    /**
     * 供网页js调用的方法
     */
    class InJavaScriptGetBody {

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void uploadMess() {

            handler.sendEmptyMessage(0x121);

        }

        @JavascriptInterface
        public String getUplodInfo(String data) {

            return str;
        }

        @JavascriptInterface
        public void backIndex() {
            handler.sendEmptyMessage(0x122);
        }

    }

    private void updateImg() {
        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (mSelectPath_new != null && mSelectPath_new.size() > 0) {
            for (int i = 0; i < mSelectPath_new.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath_new.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);
                uploadFileBeen.add(bean);
                Log.d("雨落无痕丶", "updateImgPath: "+mSelectPath_new.get(i));
            }
        }
        /*if (mSelectPath != null && mSelectPath.size() > 0) {
            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);
                uploadFileBeen.add(bean);
            }
        }*/
        RXUtils.uploadImages(CommodityWebActivity.this, uploadFileBeen, new UploadSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(CommodityWebActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                //提交给网页
                Gson gson = new Gson();
                List<ImageUp> list = new ArrayList<>();
                for (int i = 0; i < responses.size(); i++) {
                    ImageUp imageUp = new ImageUp();
                    imageUp.setImage(responses.get(i).getData().getOppositeUrl());
                    list.add(imageUp);
                }
                str = gson.toJson(list);
                //  mWebView.loadUrl("javascript"+str);

                //旧版本
                mWebView.loadUrl("javascript:getUplodInfo(" + str + ")");

                //新版本
//                mWebView.loadUrl("javascript:getAddressUplodInfo("+str+")");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                L.e("==上传图片返回的错误码:" + response.getMessage());
                Log.d("雨落无痕丶", "上传图片返回的错误码: "+response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "上传图片出现的异常: "+e.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                updateImg();
                Log.d("雨落无痕丶", "onActivityResult: " + mSelectPath.get(0));
            }
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 10) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mSelectPath_new.clear();
                for (ImageItem image : images) {
                    mSelectPath_new.add(image.path);
                }
                updateImg();
            }
        }
    }

}
