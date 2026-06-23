package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Constructor.io Facet Configuration V2 model.
 *
 * <p>This class represents the v2 facet configuration with path_in_metadata support. Uses
 * Gson/Reflection to load data in.
 */
public class FacetConfigurationV2 {

    @SerializedName("name")
    private String name;

    @SerializedName("path_in_metadata")
    private String pathInMetadata;

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
    private List<Number> rangeLimits;

    @SerializedName("match_type")
    private String matchType;

    @SerializedName("position")
    private Integer position;

    @SerializedName("hidden")
    private Boolean hidden;

    @SerializedName("protected")
    private Boolean isProtected;

    @SerializedName("countable")
    private Boolean countable;

    @SerializedName("options_limit")
    private Integer optionsLimit;

    @SerializedName("data")
    private Map<String, Object> data;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the path in metadata
     */
    public String getPathInMetadata() {
        return pathInMetadata;
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
    public List<Number> getRangeLimits() {
        return rangeLimits;
    }

    /**
     * @return the matchType
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @return the hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * @return the protected
     */
    public Boolean getProtected() {
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
    public Integer getOptionsLimit() {
        return optionsLimit;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @return the creation timestamp
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the last update timestamp
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPathInMetadata(String pathInMetadata) {
        this.pathInMetadata = pathInMetadata;
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

    public void setRangeLimits(List<Number> rangeLimits) {
        this.rangeLimits = rangeLimits;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setProtected(Boolean isProtected) {
        this.isProtected = isProtected;
    }

    public void setCountable(Boolean countable) {
        this.countable = countable;
    }

    public void setOptionsLimit(Integer optionsLimit) {
        this.optionsLimit = optionsLimit;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
