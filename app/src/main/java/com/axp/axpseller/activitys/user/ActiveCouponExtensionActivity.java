package com.axp.axpseller.activitys.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.axp.axpseller.R;
import com.axp.axpseller.views.AXPWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveCouponExtensionActivity extends AppCompatActivity {

    @BindView(R.id.axp_webview)
    AXPWebView axpWebview;
    @BindView(R.id.rl)
    RelativeLayout rl;
    public static final String KEY_LOAD_URL = "load_url";
    private String loadUrl;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_coupon_extension);
        ButterKnife.bind(this);
        initData();
        initListenner();
        mWebView.addJavascriptInterface(new AndroidWebClass(),"android");
        mWebView.loadUrl(loadUrl);
    }

    private void initListenner() {
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                rl.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                rl.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initData() {
        if (getIntent().getExtras() != null) {
            loadUrl = getIntent().getExtras().getString(KEY_LOAD_URL);
        }
        mWebView = axpWebview.getWebView();
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.canGoBack()) {
            super.onBackPressed();
        }else {
            mWebView.goBack();
        }
    }

    /**
     * 供网页js调用的方法
     */
    class AndroidWebClass {

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void backIndex() {
            Log.d("雨落无痕丶", "backIndex: back");
            finish();
        }

    }
}
