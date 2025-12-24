package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Searchability V2 model.
 *
 * <p>This class represents the v2 searchability configuration. Uses Gson/Reflection to load data
 * in.
 */
public class SearchabilityV2 {

    @SerializedName("name")
    private String name;

    @SerializedName("fuzzy_searchable")
    private Boolean fuzzySearchable;

    @SerializedName("exact_searchable")
    private Boolean exactSearchable;

    @SerializedName("displayable")
    private Boolean displayable;

    @SerializedName("hidden")
    private Boolean hidden;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return whether terms can be fuzzy searchable */
    public Boolean getFuzzySearchable() {
        return fuzzySearchable;
    }

    /** @return whether terms can be exact searchable */
    public Boolean getExactSearchable() {
        return exactSearchable;
    }

    /** @return whether the field is displayable in the response */
    public Boolean getDisplayable() {
        return displayable;
    }

    /** @return whether the field is hidden by default */
    public Boolean getHidden() {
        return hidden;
    }

    /** @return the creation timestamp */
    public String getCreatedAt() {
        return createdAt;
    }

    /** @return the last update timestamp */
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFuzzySearchable(Boolean fuzzySearchable) {
        this.fuzzySearchable = fuzzySearchable;
    }

    public void setExactSearchable(Boolean exactSearchable) {
        this.exactSearchable = exactSearchable;
    }

    public void setDisplayable(Boolean displayable) {
        this.displayable = displayable;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
