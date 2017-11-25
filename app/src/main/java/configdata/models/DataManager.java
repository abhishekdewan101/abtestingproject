package configdata.models;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import configdata.ConfigDataManager;

/**
 * Created by a.dewan on 9/27/17.
 */

public class DataManager {

    String mJsonString;

    public DataManager(Context context) {
        loadJSON(context);
    }

    public ABParentView getABParentFromJson(){
        Gson gson = new Gson();
        ABParentView abParentView = gson.fromJson(mJsonString,ABParentView.class);
        return abParentView;
    }

    private void loadJSON(Context context) {
        try {
            InputStream is = context.getAssets().open("config2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            mJsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
