package viewinjector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import configdata.models.ABChildView;
import configdata.models.ABProperties;
import configdata.models.ABView;
import customviews.ABImageView;
import customviews.ABRelativeLayout;
import customviews.ABTextView;
import utils.TextUtils;

/**
 * Created by a.dewan on 9/27/17.
 */

public class ABViewCreator {
    public static final String RELATIVE_LAYOUT = "RelativeLayout";
    public static final String IMAGE_VIEW = "ImageView";
    public static final String CENTER_CROP = "centerCrop";

    public static View create(ABView abViewFromJson, Context mContext) {
        ViewGroup parentLayout = processParentLayout(abViewFromJson,mContext);
        for (ABChildView childView : abViewFromJson.getChildren()) {
            View view = processChildView(childView,mContext);
            parentLayout.addView(view);
        }
        return parentLayout;
    }

    private static View processChildView(ABChildView childView, Context mContext) {
        View tempView;
        switch (childView.getType()) {

            case IMAGE_VIEW:
                tempView = new ABImageView(mContext);
                tempView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tempView = processChildViewProperties(tempView,childView.getProperties(),mContext);
                return tempView;

            case "TextView":
                tempView = new ABTextView(mContext);
                tempView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tempView = processChildViewProperties(tempView,childView.getProperties(),mContext);
                return tempView;
        }
        return  null;
    }

    private static View processChildViewProperties(View tempView, ArrayList<ABProperties> properties, Context mContext) {
        for (ABProperties abProperties : properties) {
            addChildProperty(tempView,abProperties,mContext);
        }
        return  tempView;
    }

    private static void addChildProperty(View tempView, ABProperties abProperties, Context mContext) {
        switch(abProperties.getName()) {

            case "width":
                if (abProperties.getValue().equals("match_parent")) {
                    tempView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                break;

            case "height":
                if (abProperties.getValue().equals("match_parent")) {
                    tempView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                break;

            case "src":
                Resources resources = mContext.getResources();
                final int id = resources.getIdentifier(abProperties.getValue(),"drawable",mContext.getPackageName());
                ((ImageView)tempView).setImageDrawable(resources.getDrawable(id));
                break;

            case "scaleType":
                if (abProperties.getValue().equalsIgnoreCase(CENTER_CROP)) {
                    ((ImageView) tempView).setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;

            case "text" :
                ((TextView) tempView).setText(abProperties.getValue());
                break;

            case "textSize" :
                ((TextView)tempView).setTextSize(TextUtils.pxFromDp(Float.parseFloat(abProperties.getValue()),mContext));
                break;

            case "textColor":
                ((TextView)tempView).setTextColor(Color.parseColor(abProperties.getValue()));
                break;

            case "textStyle":
                if (abProperties.getValue().equalsIgnoreCase("bold")) {
                    ((TextView)tempView).setTypeface(Typeface.DEFAULT_BOLD);
                }
                break;

            case "layout_alignParentBottom":
                if (abProperties.getValue().equalsIgnoreCase("true")) {
                    ((RelativeLayout.LayoutParams)tempView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                }
                break;

            case "layout_marginBottom":
                ((RelativeLayout.LayoutParams)tempView.getLayoutParams()).bottomMargin = (int) TextUtils.pxFromDp(Float.parseFloat(abProperties.getValue()),mContext);
                break;

            case "layout_centerHorizontal":
                if (abProperties.getValue().equalsIgnoreCase("true")) {
                    ((RelativeLayout.LayoutParams) tempView.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
                }
                break;
        }
    }


    private static ViewGroup processParentLayout(ABView abView, Context mContext) {
        ViewGroup tempViewGroup;
        switch (abView.getType()) {

            case RELATIVE_LAYOUT:
                tempViewGroup = new ABRelativeLayout(mContext);
                tempViewGroup.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                tempViewGroup = processViewGroupProperties(tempViewGroup,abView.getProperties());
                return tempViewGroup;

        }
        return null;
    }

    private static ViewGroup processViewGroupProperties(ViewGroup tempViewGroup, ArrayList<ABProperties> properties) {
        for (ABProperties abProperties: properties) {
            addViewGroupProperty(tempViewGroup,abProperties);
        }
        return tempViewGroup;
    }

    private static void addViewGroupProperty(ViewGroup tempViewGroup, ABProperties abProperties) {
        switch (abProperties.getName()) {

            case "width":
                if (abProperties.getValue().equals("match_parent")) {
                    tempViewGroup.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                break;

            case "height":
                if (abProperties.getValue().equals("match_parent")) {
                    tempViewGroup.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                break;
        }
    }
}
