package viewinjector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import configdata.models.ABParentView;
import configdata.models.ABProperty;
import customviews.ABImageView;
import customviews.ABLinearLayout;
import customviews.ABRelativeLayout;
import customviews.ABTextView;
import timber.log.Timber;
import utils.TextUtils;
import utils.UIUtils;

/**
 * Created by a.dewan on 9/27/17.
 */

public class ViewCreator {
    private static final String TAG = ViewCreator.class.getSimpleName() ;
    public static final String CENTER_CROP = "centerCrop";

    /*
       Step 1: from ABParentView Object create the parent view object
       Step 2: apply properties for the parent view
       Step 3: check if parent view has children, recursive call createViewGroupFromObject for each child
       Step 4: Go till reach end of tree branch and then come back up again.
     */

    public View createView(ABParentView tempView, Context context){
        Timber.tag(TAG).e("createView:"+tempView.getName() + " - " + tempView.getType());
        switch (tempView.getType()) {
            case "view_group":
                return createViewGroupFromObject(tempView,context);

            case "child_view":
                return createChildViewFromObject(tempView,context);
        }
        return null;
    }

    /**
     * ------------------PARENT VIEW CREATION METHODS-----------------------
     *
     */

    private ViewGroup createViewGroupFromObject(ABParentView tempView, Context context){
        Timber.tag(TAG).e("createViewGroupFromObject:"+tempView.getName() + " - " + tempView.getType());
        ViewGroup createdView = null;

        //Step 1
        createdView = processViewGroupFromObject(tempView,context);

        //Step 2
        applyPropertiesToViewGroup(createdView,tempView,context);

        //Step 3
        if (viewHasChildren(tempView)) {
            processChildrenOfViewGroup(createdView,tempView,context);
        } else {
            createdView.addView(createChildViewFromObject(tempView,context));
        }

        return createdView;
    }


    private ViewGroup processViewGroupFromObject(ABParentView tempView, Context context) {
        Timber.tag(TAG).e("processViewGroupFromObject:"+tempView.getName() + " - " + tempView.getType());
        switch (tempView.getName()) {

            case "relative_layout":
                ABRelativeLayout relativeLayout = new ABRelativeLayout(context);
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                return relativeLayout;

            case "linear_layout":
                ABLinearLayout linearLayout = new ABLinearLayout(context);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                return linearLayout;
        }
        return null;
    }

    private void applyPropertiesToViewGroup(ViewGroup createdView, ABParentView tempView, Context context) {
        for (ABProperty property : tempView.getProperties()) {
            addPropertyToViewGroup(createdView,property,context);
        }
    }

    private void addPropertyToViewGroup(ViewGroup createdView, ABProperty property, Context context) {
        switch (property.getName()) {
            case "width":
                if (property.getType().equalsIgnoreCase("string")) {
                    switch (property.getValue()) {
                        case "match_parent":
                            createdView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                            break;
                        case "wrap_content":
                            createdView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                            break;
                    }
                }

                if (property.getType().equalsIgnoreCase("int")) {
                    createdView.getLayoutParams().width = (int) UIUtils.pxFromDp(Integer.parseInt(property.getValue()),context);
                }
                break;

            case "height":
                if (property.getType().equalsIgnoreCase("string")) {
                    switch (property.getValue()) {
                        case "match_parent":
                            createdView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                            break;
                        case "wrap_content":
                            createdView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            break;
                    }
                }

                if (property.getType().equalsIgnoreCase("int")) {
                    createdView.getLayoutParams().height = (int) UIUtils.pxFromDp(Integer.parseInt(property.getValue()),context);
                }
                break;

            case "orientation" :
                if (property.getValue().equalsIgnoreCase("vertical")){
                    ((LinearLayout)createdView).setOrientation(LinearLayout.VERTICAL);
                } else {
                    ((LinearLayout)createdView).setOrientation(LinearLayout.HORIZONTAL);
                }
                break;
        }
    }

    private void processChildrenOfViewGroup(ViewGroup createdView, ABParentView tempView, Context context) {
        Timber.tag(TAG).e("processChildrenOfViewGroup:"+tempView.getName() + " - " + tempView.getType());
        for(ABParentView childView : tempView.getChildren()) {
            createdView.addView(createView(childView,context));
        }
    }

    /**
     * ------------------CHILD VIEW CREATION METHODS-----------------------
     *
     */

    private View createChildViewFromObject(ABParentView tempView, Context context) {
        Timber.tag(TAG).e("createChildViewFromObject:"+tempView.getName() + " - " + tempView.getType());
        View createdView = null;

        createdView = processChildFromObject(tempView, context);

        applyPropertiesToChildView(createdView,tempView,context);

        return createdView;
    }

    private View processChildFromObject(ABParentView tempView, Context context) {
        Timber.tag(TAG).e("processChildFromObject:"+tempView.getName() + " - " + tempView.getType());
        switch (tempView.getName()) {

            case "image_view":
                ABImageView imageView = new ABImageView(context);
                imageView.setLayoutParams(processChildLayoutParams(tempView));
                return imageView;

            case "text_view":
                ABTextView textView = new ABTextView(context);
                textView.setLayoutParams(processChildLayoutParams(tempView));
                return textView;
        }

        return null;
    }

    private ViewGroup.LayoutParams processChildLayoutParams(ABParentView tempView) {
        switch (tempView.getParent()) {

            case "relative_layout":
                return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            case "linear_layout":
                return  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return null;
    }


    private void applyPropertiesToChildView(View createdView, ABParentView tempView, Context context) {
        for (ABProperty property : tempView.getProperties()) {
            addPropertyToView(createdView,property,context);
        }
    }

    private void addPropertyToView(View createdView, ABProperty property, Context context) {
        switch(property.getName()) {

            case "width":
                if (property.getType().equalsIgnoreCase("string")) {
                    switch (property.getValue()) {
                        case "match_parent":
                            createdView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                            break;
                        case "wrap_content":
                            createdView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                            break;
                    }
                }

                if (property.getType().equalsIgnoreCase("int")) {
                    createdView.getLayoutParams().width = (int) UIUtils.pxFromDp(Integer.parseInt(property.getValue()),context);
                }
                break;

            case "height":
                if (property.getType().equalsIgnoreCase("string")) {
                    switch (property.getValue()) {
                        case "match_parent":
                            createdView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                            break;
                        case "wrap_content":
                            createdView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            break;
                    }
                }

                if (property.getType().equalsIgnoreCase("int")) {
                    createdView.getLayoutParams().height = (int) UIUtils.pxFromDp(Integer.parseInt(property.getValue()),context);
                }
                break;

            case "src":
                Resources resources = context.getResources();
                final int id = resources.getIdentifier(property.getValue(),"drawable",context.getPackageName());
                ((ImageView)createdView).setImageDrawable(resources.getDrawable(id));
                break;

            case "scaleType":
                if (property.getValue().equalsIgnoreCase(CENTER_CROP)) {
                    ((ImageView) createdView).setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;

            case "text" :
                ((TextView) createdView).setText(property.getValue());
                break;

            case "textSize" :
                ((TextView)createdView).setTextSize(TextUtils.pxFromDp(Float.parseFloat(property.getValue()),context));
                break;

            case "textColor":
                ((TextView)createdView).setTextColor(Color.parseColor(property.getValue()));
                break;

            case "textStyle":
                if (property.getValue().equalsIgnoreCase("bold")) {
                    ((TextView)createdView).setTypeface(Typeface.DEFAULT_BOLD);
                }
                break;

            case "layout_alignParentBottom":
                if (property.getValue().equalsIgnoreCase("true")) {
                    ((RelativeLayout.LayoutParams)createdView.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                }
                break;

            case "layout_marginBottom":
                ((RelativeLayout.LayoutParams)createdView.getLayoutParams()).bottomMargin = (int) TextUtils.pxFromDp(Float.parseFloat(property.getValue()),context);
                break;

            case "layout_centerHorizontal":
                if (property.getValue().equalsIgnoreCase("true")) {
                    ((RelativeLayout.LayoutParams) createdView.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
                }
                break;
        }
    }

    /**
     * ------------------UTIL METHODS-----------------------
     *
     */

    private Boolean viewHasChildren(ABParentView abParentView) {
        Timber.tag(TAG).e("viewHasChildren:"+abParentView.getName());
        if (abParentView.getChildren() != null && abParentView.getChildren().size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
