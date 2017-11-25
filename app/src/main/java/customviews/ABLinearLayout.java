package customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by a.dewan on 9/27/17.
 */

public class ABLinearLayout extends LinearLayout {
    public ABLinearLayout(Context context) {
        super(context);
    }

    public ABLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ABLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ABLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
