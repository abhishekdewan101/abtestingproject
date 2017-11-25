package viewinjector;

import android.content.Context;
import android.view.View;

import configdata.models.DataManager;

/**
 * Created by a.dewan on 9/26/17.
 */

public class ViewInjector {

    private static ViewInjector mViewInjector;
    private static Context mContext;
    private DataManager mDataManager;
    private ViewCreator mViewCreator;

    public static ViewInjector getInstance(Context context) {
        if (mViewInjector != null) {
            return mViewInjector;
        } else {
            mContext = context;
            mViewInjector = new ViewInjector();
            return  mViewInjector;
        }
    }

    public ViewInjector() {
        initViewInjector();
    }

    private void initViewInjector() {
        mDataManager = new DataManager(mContext);
        mViewCreator = new ViewCreator();
    }

    public View createViewFromJson() {
        View view = mViewCreator.createView(mDataManager.getABParentFromJson(),mContext);
        return view;
    }
}