package com.example.remoteviews.abtesting.network;

import android.content.Context;
import android.util.Log;
import android.view.View;


import com.example.remoteviews.abtesting.layoutinflater.DynamicLayoutInflator;
import com.example.remoteviews.abtesting.network.model.ABExperimentModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a.dewan on 10/10/17.
 */

public class ABRemoteClient {

    private static final String TAG = ABRemoteClient.class.getSimpleName();
    static String s3BaseUrl = "https://s3-us-west-1.amazonaws.com/abtestingbucket/";
    static String prodBaseUrl = "https://ckba34nscf.execute-api.us-east-1.amazonaws.com/prod/";
    static ABRemoteClient mRemoteClient;
    static ABExperimentModel mExperimentModel;
    static ABExperiment mABExperiment;
    private ABExperimentLoadedInterface mListener;

    public static ABRemoteClient getInstance(){
        if (mRemoteClient == null) {
            return new ABRemoteClient();
        } else {
            return mRemoteClient;
        }
    }

    public void init(Context context, ABExperimentLoadedInterface listener){
        setListener(listener);
        initRetrofitClient();
        getABExperiment(context);
    }

    private void initRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(prodBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mABExperiment = retrofit.create(ABExperiment.class);
    }

    public void setListener(ABExperimentLoadedInterface listener) {
        mListener = listener;
    }


    public void getABExperiment(final Context context) {
        mABExperiment.getExperimentData().enqueue(new Callback<ABExperimentModel>() {
            @Override
            public void onResponse(Call<ABExperimentModel> call, Response<ABExperimentModel> response) {
                mExperimentModel = response.body();
                Log.e(TAG,"About to download file name:" + mExperimentModel.getmFileName());
                getABExperimentView(context);
            }

            @Override
            public void onFailure(Call<ABExperimentModel> call, Throwable throwable) {

            }
        });
    }

    public void getABExperimentView(final Context context) {
        Retrofit client = new Retrofit.Builder().baseUrl(s3BaseUrl).build();

        client.create(ABExperiment.class).getExperimentView(mExperimentModel.getmFileName()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                writeFileToDisk(response,context);
                mListener.experiementHasLoaded();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }

    private void writeFileToDisk(Response<ResponseBody> responseBody, Context context) {
        File customLayoutFile = new File(context.getExternalFilesDir(null) + File.separator + "custom_layout.xml");
        ResponseBody body = responseBody.body();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];

            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;

            inputStream = body.byteStream();
            outputStream = new FileOutputStream(customLayoutFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);

                fileSizeDownloaded += read;

                Log.d("MAINACTIVITY", "file download: " + fileSizeDownloaded + " of " + fileSize);
            }

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public View getExperimentView(Context context) {
        try {
            return DynamicLayoutInflator.inflate(context,new FileInputStream(new File(context.getExternalFilesDir(null) + File.separator + "custom_layout.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ABExperimentLoadedInterface {
        void experiementHasLoaded();
    }
}
