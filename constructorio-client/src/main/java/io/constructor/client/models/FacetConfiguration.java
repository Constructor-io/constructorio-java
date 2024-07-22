package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/** Constructor.io Facet Configuration ... uses Gson/Reflection to load data in */
public class FacetConfiguration {
    public enum MatchType {
        all,
        any,
        none,
    }

    @SerializedName("options")
    private List<FacetOptionConfiguration> options;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("sort_order")
    private String sortOrder;

    @SerializedName("sort_descending")
    private Boolean sortDescending;

    @SerializedName("range_type")
    private String rangeType;

    @SerializedName("range_format")
    private String rangeFormat;

    @SerializedName("range_inclusive")
    private String rangeInclusive;

    @SerializedName("range_limits")
    private List<String> rangeLimits;

    @SerializedName("match_type")
    private MatchType matchType;

    @SerializedName("position")
    private int position;

    @SerializedName("hidden")
    private Boolean hidden;

    @SerializedName("protected")
    private Boolean isProtected;

    @SerializedName("countable")
    private Boolean countable;

    @SerializedName("options_limit")
    private int optionsLimit;

    @SerializedName("data")
    private Map<String, Object> data;

    /**
     * @return the options
     */
    public List<FacetOptionConfiguration> getOptions() {
        return options;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @return the sortDescending
     */
    public Boolean getSortDescending() {
        return sortDescending;
    }

    /**
     * @return the rangeType
     */
    public String getRangeType() {
        return rangeType;
    }

    /**
     * @return the rangeFormat
     */
    public String getRangeFormat() {
        return rangeFormat;
    }

    /**
     * @return the rangeInclusive
     */
    public String getRangeInclusive() {
        return rangeInclusive;
    }

    /**
     * @return the rangeLimits
     */
    public List<String> getRangeLimits() {
        return rangeLimits;
    }

    /**
     * @return the matchType
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return the hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * @return the isProtected
     */
    public Boolean getIsProtected() {
        return isProtected;
    }

    /**
     * @return the countable
     */
    public Boolean getCountable() {
        return countable;
    }

    /**
     * @return the optionsLimit
     */
    public int getOptionsLimit() {
        return optionsLimit;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    public void setOptions(List<FacetOptionConfiguration> options) {
        this.options = options;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setSortDescending(Boolean sortDescending) {
        this.sortDescending = sortDescending;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public void setRangeFormat(String rangeFormat) {
        this.rangeFormat = rangeFormat;
    }

    public void setRangeInclusive(String rangeInclusive) {
        this.rangeInclusive = rangeInclusive;
    }

    public void setRangeLimits(List<String> rangeLimits) {
        this.rangeLimits = rangeLimits;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setIsProtected(Boolean isProtected) {
        this.isProtected = isProtected;
    }

    public void setCountable(Boolean countable) {
        this.countable = countable;
    }

    public void setOptionsLimit(int optionsLimit) {
        this.optionsLimit = optionsLimit;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
