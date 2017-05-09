package com.example.kashif.newsbook.ApiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by KASHIF on 3/1/2017.
 */
public class ArticleResponse {

    @Expose
    @SerializedName("source")
    public String source;

    @Expose
    @SerializedName("articles")
    public List<ArticleLists> articleLists;
}
