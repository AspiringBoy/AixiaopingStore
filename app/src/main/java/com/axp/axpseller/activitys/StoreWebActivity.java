package com.axp.axpseller.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.axp.axpseller.activitys.mall.SellerAddressActivity;
import com.axp.axpseller.core.BaseActivity;
import com.axp.axpseller.core.UploadSubscriber;
import com.axp.axpseller.managers.LocationManager;
import com.axp.axpseller.models.Location;
import com.axp.axpseller.models.bean.UploadFileBean;
import com.axp.axpseller.network.Response;
import com.axp.axpseller.utils.AppUtils;
import com.axp.axpseller.utils.L;
import com.axp.axpseller.utils.RXUtils;
import com.axp.axpseller.utils.StringUtils;
import com.axp.axpseller.views.AXPWebView;
import com.axp.axpseller.views.dialogs.LoadingDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Dong on 2017/1/13.
 */
public class StoreWebActivity extends BaseActivity {

    Toolbar mToolbar;

    AXPWebView mAxpWebView;
    WebView mWebView;
    SpinKitView mSpinKitView;
    RelativeLayout rl;
    public static final String KEY_LOAD_URL = "load_url";
    public static final String KEY_TITLE = "title";
    String str;

    /** 页面正在加载的URL */
//    String loadUrl = "http://seller.aixiaoping.com/?id="+ ContextParameter.getUserInfo().getAdminuserId();
    String loadUrl = "http://test.aixiaoping.com/Admin/index?id="+ ContextParameter.getUserInfo().getAdminuserId();
    /** 网页标题 */
    String title;
    MultiImageSelector selector = MultiImageSelector.create(StoreWebActivity.this);
    private ArrayList<String> mSelectPath;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
               //触发点击事件，获得相对与绝对路径
                selector.single();
               // selector.count(9);
                selector.start(StoreWebActivity.this, 2);
            }else if(msg.what == 0x122) {
                StoreWebActivity.this.finish();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.aixiaoping.library.R.layout.activity_web);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
        mWebView.addJavascriptInterface(new InJavaScriptGetBody(), "android");
        mWebView.loadUrl(loadUrl);

    }

    private void initListener(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if(getIntent().getExtras() == null || StringUtils.isEmpty(getIntent().getExtras().getString(KEY_TITLE, null))){
                    mToolbar.setTitle(title);
                }
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mSpinKitView.setVisibility(View.VISIBLE);
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
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

    private void initView(){
        rl = (RelativeLayout) findViewById(net.aixiaoping.library.R.id.rl);
        mToolbar = (Toolbar) findViewById(net.aixiaoping.library.R.id.tool_bar);
        mToolbar.setVisibility(View.GONE);
        //setSupportActionBar(mToolbar);
        mAxpWebView = (AXPWebView) findViewById(net.aixiaoping.library.R.id.axpwv_webview);
        mSpinKitView = (SpinKitView) findViewById(net.aixiaoping.library.R.id.spin_kit);
        mWebView = mAxpWebView.getWebView();
    }

    private void initData(){
        if(getIntent().getExtras() != null){
            loadUrl = getIntent().getExtras().getString(KEY_LOAD_URL);
            title = getIntent().getExtras().getString(KEY_TITLE, "网页");
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 供网页js调用的方法
     */
    class InJavaScriptGetBody {

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void openMap() {
            AppUtils.toActivity(StoreWebActivity.this, SellerAddressActivity.class);
        }

        @JavascriptInterface
        public void backIndex(){
            finish();
        }

        @JavascriptInterface
        public void uploadMess(){
            handler.sendEmptyMessage(0x121);
        }

//        @JavascriptInterface
//        public String getUplodInfo(String data){
//
//            return str;
//        }

    }

    private void updateImg(){
        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (mSelectPath != null && mSelectPath.size() > 0) {

            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);
                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(StoreWebActivity.this, uploadFileBeen, new UploadSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(StoreWebActivity.this);
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
                for(int i = 0; i < responses.size();i++){
                    ImageUp imageUp= new ImageUp();
                    imageUp.setImage(responses.get(i).getData().getOppositeUrl());
                    list.add(imageUp);
                }
                str = gson.toJson(list);
              //  mWebView.loadUrl("javascript"+str);

                mWebView.loadUrl("javascript:getUplodInfo("+str+")");

            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                L.e("==上传图片返回的错误码="+response);
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                List<String> list;
                mSelectPath = new ArrayList<>();
                list = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                for (int i = 0;i<list.size();i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(list.get(i));
                    Bitmap scaleBitmap = centerSquareScaleBitmap(bitmap, 800);
                    try {
                        save2Local(scaleBitmap,i);
                        mSelectPath.add(new File(Environment.getExternalStorageDirectory(), i + ".jpg").getAbsolutePath());
//                        Log.d("雨落无痕丶", "onActivityResult: "+scaleBitmap.getHeight()+"----"+bitmap.getHeight());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                updateImg();
            }
        }
    }

    private void save2Local(Bitmap scaleBitmap, int i) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), i + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            scaleBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                fos.close();
        }
    }

    @Subscribe
    public void getJSMsg(String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Location location = LocationManager.syncGetLocation();
                JSONObject object = new JSONObject();
                try {
                    object.put("address",location.getAddress());
                    object.put("lat",location.getLatitude());
                    object.put("lng",location.getLongitude());
                    str = object.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:getAddressUplodInfo("+str+")");

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @param bitmap      原图
     * @param edgeLength  希望得到的正方形部分的边长
     * @return  缩放截取正中部分后的位图。
     */
    public  Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if(widthOrg > edgeLength && heightOrg > edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try{
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }

        return result;
    }

}
