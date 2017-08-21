package com.namclu.android.deputyscheduler.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by namlu on 7/23/2017.
 *
 * To end network requests to an API, we need to use the Retrofit Builder class
 * and specify the base URL for the service
 */

public class ApiClient {

    private static final String BASE_URL =
            "https://apjoqdqpi3.execute-api.us-west-2.amazonaws.com/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
