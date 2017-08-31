package com.coderminion.webview;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    WebView mWebview;

    //TODO change URL_TO_LOAD
    String URL_TO_LOAD = "http://coderminion.com";

    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Please wait", "Page is loading", false);
        mWebview = (WebView) findViewById(R.id.webview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setBuiltInZoomControls(true);


        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = mWebview.getUrl();
                Log.i("WebURL Loaded ", webUrl);
                URL_TO_LOAD = webUrl;  //Assign to Load on Swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mWebview.loadUrl(URL_TO_LOAD);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onBackPressed() {

        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onRefresh() {
        mWebview.loadUrl(URL_TO_LOAD);
        Toast.makeText(getApplicationContext(),"Page refreshing",Toast.LENGTH_SHORT).show();
    }
}
