package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Related Browse Page - uses Gson/Reflection to load data in */
public class RelatedBrowsePage {

    @SerializedName("filter_name")
    private String filterName;

    @SerializedName("filter_value")
    private String filterValue;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("image_url")
    private String imageUrl;

    /**
     * @return the filter name
     */
    public String getFilterName() {
        return filterName;
    }

    /**
     * @return the filter value
     */
    public String getFilterValue() {
        return filterValue;
    }

    /**
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param filterName the filter name to set
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * @param filterValue the filter value to set
     */
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    /**
     * @param displayName the display name to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @param imageUrl the image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
