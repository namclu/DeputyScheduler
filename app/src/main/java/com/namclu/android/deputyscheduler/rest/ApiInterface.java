package com.namclu.android.deputyscheduler.rest;

import com.namclu.android.deputyscheduler.BuildConfig;
import com.namclu.android.deputyscheduler.models.Shift;
import com.namclu.android.deputyscheduler.models.ShiftPostBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by namlu on 7/23/2017.
 *
 * The endpoints are defined inside of an interface using special retrofit annotations
 * to encode details about the parameters and request method. In addition, the return
 * value is always a parameterized Call<T> object such as Call<MovieResponse>
 */

public interface ApiInterface {

    @GET("/dmc/shifts")
    Call<List<Shift>> getShifts(@Header("Authorization") String deputySha);

    @POST("/dmc/shift/start")
    @Headers({"Authorization: " + "Deputy " + BuildConfig.USER_SHA})
    Call<String> postShift(@Body ShiftPostBody postBody);
}
