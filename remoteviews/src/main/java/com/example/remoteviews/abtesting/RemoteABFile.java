package com.example.remoteviews.abtesting;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by a.dewan on 10/9/17.
 */

public class RemoteABFile {

    public static Retrofit initRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://s3-us-west-1.amazonaws.com/abtestingbucket/")
                .build();

        return retrofit;

    }
}
