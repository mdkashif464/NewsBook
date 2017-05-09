package com.example.kashif.newsbook.ApiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KASHIF on 3/1/2017.
 */
public class ArticleLists {

    @Expose
    @SerializedName("author")
    private String author;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("urlToImage")
    private String articleImageUrl;

    @Expose
    @SerializedName("publishedAt")
    private String publishedTime;

    @Expose
    @SerializedName("url")
    private String fullStoryURL;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public String getFullStoryURL() {
        return fullStoryURL;
    }
}
