package utils;

import android.content.Context;

/**
 * Created by a.dewan on 9/27/17.
 */

public class TextUtils {
    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

}
