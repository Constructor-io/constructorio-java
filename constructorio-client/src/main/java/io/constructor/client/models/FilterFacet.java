package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/** Constructor.io Filter Facet ... uses Gson/Reflection to load data in */
public class FilterFacet {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private Map<String, Object> status;

    @SerializedName("max")
    private Double max;

    @SerializedName("min")
    private Double min;

    @SerializedName("options")
    private List<FilterFacetOption> options;

    @SerializedName("type")
    private String type;

    @SerializedName("data")
    private Map<String, Object> data;

    @SerializedName("hidden")
    private Boolean hidden;

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the status
     */
    public Map<String, Object> getStatus() {
        return status;
    }

    /**
     * @return the max
     */
    public Double getMax() {
        return max;
    }

    /**
     * @return the min
     */
    public Double getMin() {
        return min;
    }

    /**
     * @return the options
     */
    public List<FilterFacetOption> getOptions() {
        return options;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
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

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Map<String, Object> status) {
        this.status = status;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public void setOptions(List<FilterFacetOption> options) {
        this.options = options;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
