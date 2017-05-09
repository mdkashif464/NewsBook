package com.example.kashif.newsbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class ReadFullStoryActivity extends AppCompatActivity {

    private WebView fullStoryWebView;
    private SmoothProgressBar smoothProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_full_story);

        fullStoryWebView = (WebView) this.findViewById(R.id.fullStory_webView);
        smoothProgressBar = (SmoothProgressBar) findViewById(R.id.smoothProgressBar);

        Intent intent = getIntent();
        String fullStoryLink = intent.getExtras().getString("fullStoryLink");
        String sourceName = intent.getExtras().getString("sourceName");

        getSupportActionBar().setTitle(sourceName);

        fullStoryWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("kashif", "onPage finished Called");
                hideProgressBar();

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgressBar();
                Log.d("kashif", "onPage started Called");
            }
        });

        fullStoryWebView.getSettings().setJavaScriptEnabled(true);
        fullStoryWebView.loadUrl(fullStoryLink);
        fullStoryWebView.requestFocus();
    }

    public void showProgressBar() {
        smoothProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        smoothProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && fullStoryWebView.canGoBack()) {
            fullStoryWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (fullStoryWebView.canGoBack()) {
                fullStoryWebView.goBack();
            } else {
                finish();
            }
        }
        return true;
    }

}
