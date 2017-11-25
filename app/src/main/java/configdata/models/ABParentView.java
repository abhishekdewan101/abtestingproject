package configdata.models;

import java.util.ArrayList;

/**
 * Created by a.dewan on 9/27/17.
 */

public class ABParentView {
    String name;
    String type;
    String parent;
    ArrayList<ABProperty> properties;
    ArrayList<ABParentView> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ABProperty> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<ABProperty> properties) {
        this.properties = properties;
    }

    public ArrayList<ABParentView> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ABParentView> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
