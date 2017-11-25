package com.example.remoteviews.abtesting.network;


import com.example.remoteviews.abtesting.network.model.ABExperimentModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by a.dewan on 10/10/17.
 */

public interface ABExperiment {

    @GET("configs")
    Call<ABExperimentModel> getExperimentData();

    @GET("/abtestingbucket/{path}")
    Call<ResponseBody> getExperimentView(@Path(value="path",encoded = true) String url);
}
