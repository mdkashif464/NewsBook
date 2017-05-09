package com.example.kashif.newsbook.ApiClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by KASHIF on 2/28/2017.
 */
public class SourceResponse {

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("sources")
    public List<NewsSourceLists> newsSourceLists;
}
