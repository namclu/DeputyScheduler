package com.namclu.android.deputyscheduler.rest;

import com.namclu.android.deputyscheduler.models.ShiftResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by namlu on 7/23/2017.
 *
 * The endpoints are defined inside of an interface using special retrofit annotations
 * to encode details about the parameters and request method. In addition, the return
 * value is always a parameterized Call<T> object such as Call<MovieResponse>
 */

public interface ApiInterface {

    @GET("/shifts")
    Call<ShiftResponse> getShifts(@Header("Authorization") String deputySha);
}
