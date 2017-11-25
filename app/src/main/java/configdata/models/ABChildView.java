package configdata.models;

import java.util.ArrayList;

/**
 * Created by a.dewan on 9/26/17.
 */

public class ABChildView {
    String type;
    ArrayList<ABProperties> properties;

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
}