package com.example.kashif.newsbook;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kashif.newsbook.ApiClasses.ArticleLists;
import com.example.kashif.newsbook.ApiClasses.ArticleResponse;
import com.example.kashif.newsbook.ApiClasses.NewsApiClient;
import com.twotoasters.jazzylistview.effects.FlipEffect;
import com.twotoasters.jazzylistview.effects.FlyEffect;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends AppCompatActivity implements Callback<ArticleResponse>, NetworkStateReceiver.NetworkStateReceiverListener {

    RecyclerView showArticleListRecyclerView;
    ArticleListAdapter articleListAdapter;
    ProgressBar progressBar;
    TextView progressTextTextView;
    TextView noInternetMessageDisplayTextview;
    private SmoothProgressBar smoothProgressBar;


    private NetworkStateReceiver networkStateReceiver;

    private String SOURCE_ID = "source_Id";
    public static String SOURCE_NAME = "source_Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);


        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressTextTextView = (TextView) findViewById(R.id.progressText_tv);
        noInternetMessageDisplayTextview = (TextView) findViewById(R.id.noInternetMessage_tv);
        smoothProgressBar = (SmoothProgressBar) findViewById(R.id.smoothProgressBar);

        JazzyRecyclerViewScrollListener listener = new JazzyRecyclerViewScrollListener();
        listener.setTransitionEffect(new FlipEffect());


        Intent intent = getIntent();
        SOURCE_ID = intent.getExtras().getString("sourceId");
        SOURCE_NAME = intent.getExtras().getString("sourceName");

        getSupportActionBar().setTitle(SOURCE_NAME);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articleListAdapter = new ArticleListAdapter();
        showArticleListRecyclerView = (RecyclerView) findViewById(R.id.showArticleList_rV);

        showArticleListRecyclerView.setOnScrollListener(listener);

        showArticleListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showArticleListRecyclerView.setAdapter(articleListAdapter);

        // showArticlesList();
    }


    @Override
    public void networkAvailable() {
        Log.d("kashif", "Connected!");
    /* TODO: Your connection-oriented stuff here */
        hideNoInternetMessageText();
        showCenterProgressBar();
        showArticlesList();
    }

    @Override
    public void networkUnavailable() {
        Log.d("kashif", "Not Connected!");
    /* TODO: Your disconnection-oriented stuff here */
        hideCenterProgressBar();
        showNoInternetMessageText();
    }


    public void showArticlesList() {

        Log.d("refresh", "showArticleList called");
        Call<ArticleResponse> call = NewsApiClient.getClient().showArticlesList("4dd25d1cb54c4f368e7aa47e9fd6b88a", SOURCE_ID, "");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {

        Log.d("refresh", "onResponse called");
        // Hiding the progressBar here
        hideCenterProgressBar();
        hideSmoothProgressBar();

        showArticleListRecyclerView.smoothScrollToPosition(0);

        // UnRegister the Receiver so that it does not keeps a hard reference to the activity.
        try {
            this.unregisterReceiver(networkStateReceiver);
        } catch (final Exception exception) {

        }


        if (response.isSuccessful()) {
            articleListAdapter.setList((ArrayList<ArticleLists>) response.body().articleLists);
        }
    }

    @Override
    public void onFailure(Call<ArticleResponse> call, Throwable t) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItemId = item.getItemId();
        if (clickedItemId == R.id.action_refresh) {
            Log.d("refresh", " Refresh button clicked");
            showSmoothProgressBar();
            showArticlesList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showCenterProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressTextTextView.setVisibility(View.VISIBLE);
    }

    public void hideCenterProgressBar() {
        progressBar.setVisibility(View.GONE);
        progressTextTextView.setVisibility(View.GONE);
    }

    public void showSmoothProgressBar() {
        smoothProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideSmoothProgressBar() {
        smoothProgressBar.setVisibility(View.GONE);
    }

    public void showNoInternetMessageText() {
        noInternetMessageDisplayTextview.setVisibility(View.VISIBLE);
    }

    public void hideNoInternetMessageText() {
        noInternetMessageDisplayTextview.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            this.unregisterReceiver(networkStateReceiver);
        } catch (final Exception exception) {

        }
    }
}
