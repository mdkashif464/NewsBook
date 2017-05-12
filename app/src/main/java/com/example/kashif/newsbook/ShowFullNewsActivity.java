package com.example.kashif.newsbook;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kashif.newsbook.ApiClasses.ArticleLists;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ShowFullNewsActivity extends AppCompatActivity {

    ImageView fullNewsImageView;
    TextView publishedTimeTextView;
    TextView authorNameTextView;
    TextView newsTitleTextView;
    TextView newsDescriptionTextView;
    TextView fullStoryLinkTextView;

    private String newsTitle;
    private String newsDescription;
    private String fullStoryLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_news);

        fullNewsImageView = (ImageView) findViewById(R.id.fullNews_iView);
        publishedTimeTextView = (TextView) findViewById(R.id.publishedTime_tv);
        authorNameTextView = (TextView) findViewById(R.id.authorName_tv);
        newsTitleTextView = (TextView) findViewById(R.id.newsTitle_tv);
        newsDescriptionTextView = (TextView) findViewById(R.id.newsDescription_tv);
        fullStoryLinkTextView = (TextView) findViewById(R.id.fullStoryLink_tv);


        Intent intent = getIntent();
        String articleLists = intent.getExtras().getString("kashif");
        Gson gson = new Gson();
        ArticleLists newArticleList = gson.fromJson(articleLists, ArticleLists.class);

        final String sourceName = ArticleListActivity.SOURCE_NAME;
        getSupportActionBar().setTitle(sourceName);


        newsTitle = newArticleList.getTitle();
        newsDescription = newArticleList.getDescription();
        fullStoryLink = newArticleList.getFullStoryURL();

        Picasso.with(this).load(newArticleList.getArticleImageUrl()).into(fullNewsImageView);
        publishedTimeTextView.setText(newArticleList.getPublishedTime());
        authorNameTextView.setText(newArticleList.getAuthor());
        newsTitleTextView.setText(newsTitle);
        newsDescriptionTextView.setText(newsDescription);


        fullStoryLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowFullNewsActivity.this, ReadFullStoryActivity.class);
                intent.putExtra("fullStoryLink", fullStoryLink);
                intent.putExtra("sourceName", sourceName);
                startActivity(intent);


            }
        });
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(newsTitle + "\n" + fullStoryLink)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }

}
