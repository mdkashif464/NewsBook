package com.example.kashif.newsbook.ApiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourceResponse {

    @Expose
    @SerializedName("sources")
    public List<NewsSourceLists> newsSourceLists;
}
