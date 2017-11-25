package configdata.models;

import java.util.ArrayList;

/**
 * Created by a.dewan on 9/26/17.
 */

public class ABView {
    String type;
    ArrayList<ABProperties> properties;
    ArrayList<ABChildView> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ABProperties> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<ABProperties> properties) {
        this.properties = properties;
    }

    public ArrayList<ABChildView> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ABChildView> children) {
        this.children = children;
    }
}