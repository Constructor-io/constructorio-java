package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/** Constructor.io Facet Option Configuration ... uses Gson/Reflection to load data in */
public class FacetOptionConfiguration {

    @SerializedName("value")
    private String value;

    @SerializedName("value_alias")
    private String valueAlias;

    @SerializedName("replace_value_alias")
    private Boolean replaceValueAlias;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("position")
    private int position;

    @SerializedName("data")
    private Map<String, Object> data;

    @SerializedName("hidden")
    private Boolean hidden;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the valueAlias
     */
    public String getValueAlias() {
        return valueAlias;
    }

    /**
     * @return the replaceValueAlias
     */
    public Boolean getReplaceValueAlias() {
        return replaceValueAlias;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @return the hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueAlias(String valueAlias) {
        this.valueAlias = valueAlias;
    }

    public void setReplaceValueAlias(Boolean replaceValueAlias) {
        this.replaceValueAlias = replaceValueAlias;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
