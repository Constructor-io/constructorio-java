package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Sort Option ... uses Gson/Reflection to load data in */
public class SortOption {

    public enum SortOrder {
        @SerializedName("ascending")
        ascending,
        @SerializedName("descending")
        descending,
    }

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("sort_by")
    private String sortBy;

    @SerializedName("sort_order")
    private SortOrder sortOrder;

    @SerializedName("path_in_metadata")
    private String pathInMetadata;

    @SerializedName("position")
    private Integer position;

    @SerializedName("hidden")
    private Boolean hidden;

    /** No-arg constructor for Gson deserialization only */
    private SortOption() {}

    public SortOption(String sortBy, SortOrder sortOrder) {
        if (sortBy == null) {
            throw new IllegalArgumentException("sortBy is required");
        }

        if (sortOrder == null) {
            throw new IllegalArgumentException("sortOrder is required");
        }

        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    /**
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the display name to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the sort by field
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sort by field to set
     */
    public void setSortBy(String sortBy) {
        if (sortBy == null) {
            throw new IllegalArgumentException("sortBy is required");
        }

        this.sortBy = sortBy;
    }

    /**
     * @return the sort order (ascending or descending)
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sort order to set (ascending or descending)
     */
    public void setSortOrder(SortOrder sortOrder) {
        if (sortOrder == null) {
            throw new IllegalArgumentException("sortOrder is required");
        }

        this.sortOrder = sortOrder;
    }

    /**
     * @return the path in metadata
     */
    public String getPathInMetadata() {
        return pathInMetadata;
    }

    /**
     * @param pathInMetadata the path in metadata to set
     */
    public void setPathInMetadata(String pathInMetadata) {
        this.pathInMetadata = pathInMetadata;
    }

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return whether the sort option is hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * @param hidden whether the sort option is hidden
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
