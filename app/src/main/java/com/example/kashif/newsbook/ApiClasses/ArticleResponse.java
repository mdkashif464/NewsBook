package com.example.kashif.newsbook.ApiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @Expose
    @SerializedName("articles")
    public List<ArticleLists> articleLists;
}
