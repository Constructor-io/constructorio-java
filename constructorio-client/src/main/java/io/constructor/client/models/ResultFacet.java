package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Item Facet ... uses Gson/Reflection to load data in */
public class ResultFacet {

    @SerializedName("name")
    private String name;

    @SerializedName("values")
    private List<String> values;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
