package com.example.kashif.newsbook.ApiClasses;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KASHIF on 2/28/2017.
 */

public class NewsApiClient {

    private static final String BASE_URL = " https://newsapi.org/v1/";

    public static NewsApi getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NewsApi.class);

    }
}
