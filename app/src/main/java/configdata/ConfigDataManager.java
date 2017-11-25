package configdata;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import configdata.models.ABView;

/**
 * Created by a.dewan on 9/27/17.
 */

public class ConfigDataManager {

    String mExperimentJsonString;

    public ConfigDataManager(Context context) {
        loadJSON(context);
    }

    public ABView getABViewFromJson() {
        Gson gson = new Gson();
        ABView abView = gson.fromJson(mExperimentJsonString,ABView.class);
        return  abView;
    }

    private void loadJSON(Context context){
        try {
            InputStream is = context.getAssets().open("config.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            mExperimentJsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
