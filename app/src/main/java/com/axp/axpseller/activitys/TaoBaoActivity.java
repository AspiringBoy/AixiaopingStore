package com.axp.axpseller.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.views.AXPWebView;

/**
 * Created by Dong on 2016/12/8.
 */
public class TaoBaoActivity extends AppCompatActivity {
    private String URL;
    AXPWebView mAxpWebView;
    WebView mWebView;
    RelativeLayout rl;
    Toolbar toolBar;
    TextView titles;
    private String toolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobaoweb);
        init();
        initListener();
        mWebView.loadUrl(URL);
    }

    private void init() {
        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");
        toolbar_title = intent.getStringExtra("toolbar_title");
        mAxpWebView = (AXPWebView) findViewById(R.id.axpwv_webview);
        titles = (TextView)findViewById(R.id.title);
        toolBar = (Toolbar)findViewById(R.id.toolbar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaoBaoActivity.this.finish();
            }
        });
        rl = (RelativeLayout) findViewById(R.id.rl);
        mWebView = mAxpWebView.getWebView();
        if (toolbar_title != null && toolbar_title.length() > 0) {
            toolBar.setVisibility(View.VISIBLE);
            titles.setText(toolbar_title);
        }else {
            toolBar.setVisibility(View.GONE);
        }
    }

    private void initListener(){

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                titles.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });

        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("test","开始加载。。。");
                rl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("test","加载完成。。。");
                rl.setVisibility(View.GONE);
            }
        });
    }
}
