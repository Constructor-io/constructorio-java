package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/** Constructor.io Filter Facet Option ... uses Gson/Reflection to load data in */
public class FilterFacetOption {

    @SerializedName("count")
    private Integer count;

    @SerializedName("data")
    private Map<String, Object> data;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("status")
    private String status;

    @SerializedName("value")
    private String value;

    @SerializedName("range")
    private Object[] range; // Array to hold two values of either Number or "inf", "-inf"

    @SerializedName("options")
    private List<FilterFacetOption> options;

    /**
     * @return the counts
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the range
     */
    public Object[] getRange() {
        return range;
    }

    /**
     * @return the options
     */
    public List<FilterFacetOption> getOptions() {
        return options;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRange(Object[] range) {
        this.range = range;
    }

    public void setOptions(List<FilterFacetOption> options) {
        this.options = options;
    }
}
