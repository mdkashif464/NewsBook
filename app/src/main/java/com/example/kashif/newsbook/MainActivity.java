package com.example.kashif.newsbook;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashif.newsbook.ApiClasses.NewsApi;
import com.example.kashif.newsbook.ApiClasses.NewsApiClient;
import com.example.kashif.newsbook.ApiClasses.NewsSourceLists;
import com.example.kashif.newsbook.ApiClasses.SourceResponse;
import com.twotoasters.jazzylistview.effects.CardsEffect;
import com.twotoasters.jazzylistview.effects.CurlEffect;
import com.twotoasters.jazzylistview.effects.FanEffect;
import com.twotoasters.jazzylistview.effects.FlipEffect;
import com.twotoasters.jazzylistview.effects.FlyEffect;
import com.twotoasters.jazzylistview.effects.GrowEffect;
import com.twotoasters.jazzylistview.effects.HelixEffect;
import com.twotoasters.jazzylistview.effects.SlideInEffect;
import com.twotoasters.jazzylistview.effects.TwirlEffect;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<SourceResponse>, NetworkStateReceiver.NetworkStateReceiverListener {

    RecyclerView sourceListRecyclerView;
    SourceListAdapter sourceListAdapter;
    ProgressBar progressBar;
    TextView progressBar_TextView;
    TextView noInternetMessageDisplayTextview;

    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("News Book-(70 Sources)");

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        progressBar = (ProgressBar) findViewById(R.id.progressBar_main);
        progressBar_TextView = (TextView) findViewById(R.id.progressText_tv);
        noInternetMessageDisplayTextview = (TextView) findViewById(R.id.noInternetMessage_tv);

        sourceListRecyclerView = (RecyclerView) this.findViewById(R.id.sourceList_rView);
        sourceListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        JazzyRecyclerViewScrollListener listener = new JazzyRecyclerViewScrollListener();
        listener.setTransitionEffect(new GrowEffect());
        sourceListRecyclerView.setOnScrollListener(listener);

        sourceListAdapter = new SourceListAdapter();
        sourceListRecyclerView.setAdapter(sourceListAdapter);

    }

    @Override
    public void networkAvailable() {
        Log.d("kashif", "Connected!");
        // fetching Source Lists
        fetchNewsLists();
        noInternetMessageDisplayTextview.setVisibility(View.GONE);
        showProgressBar();
    }

    @Override
    public void networkUnavailable() {
        Log.d("kashif", "Not Connected!");
        hideProgressBar();
        noInternetMessageDisplayTextview.setVisibility(View.VISIBLE);
    }

    // method that will be called to fetch source list
    public void fetchNewsLists() {
        Call<SourceResponse> call = NewsApiClient.getClient().fetchsourceLists("", "", "");
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<SourceResponse> call, Response<SourceResponse> response) {

        // Hiding the progressBar here
        hideProgressBar();
        // UnRegister the Receiver so that it does not keeps a hard reference to the activity.
        try {
            this.unregisterReceiver(networkStateReceiver);
        } catch (final Exception exception) {

        }

        if (response.isSuccessful()) {

            sourceListAdapter.setList((ArrayList<NewsSourceLists>) response.body().newsSourceLists);
        }
        else {
            Toast.makeText(this,response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<SourceResponse> call, Throwable t) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            this.unregisterReceiver(networkStateReceiver); //unregister receiver if activity gets destroyed
        } catch (final Exception exception) {

        }
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar_TextView.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        progressBar_TextView.setVisibility(View.GONE);
    }
}
