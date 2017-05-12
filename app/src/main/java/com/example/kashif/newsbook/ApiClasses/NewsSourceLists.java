package com.example.kashif.newsbook.ApiClasses;

import com.google.gson.ExclusionStrategy;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsSourceLists {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("name")
    private String name;

    /*@Expose
    @SerializedName("urlsToLogos")
    private SourceListLogo sourceListLogo;*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

   /* public String getSourceLogo() {
        return sourceListLogo.logo_url;
    }

    private class SourceListLogo {
        @Expose
        @SerializedName("small")
        private String logo_url;
    }*/
}
