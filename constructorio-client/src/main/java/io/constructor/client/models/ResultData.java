package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/** Constructor.io Result Data ... uses Gson/Reflection to load data in */
public class ResultData {

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("groups")
    private List<ResultGroup> groups;

    @SerializedName("facets")
    private List<ResultFacet> facets;

    @SerializedName("variation_id")
    private String variationId;

    @SerializedName("metadata")
    private Map<String, Object> metadata;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @return the groups
     */
    public List<ResultGroup> getGroups() {
        return groups;
    }

    /**
     * @return the facets
     */
    public List<ResultFacet> getFacets() {
        return facets;
    }

    /**
     * @return the variation id
     */
    public String getVariationId() {
        return variationId;
    }

    /**
     * @return the metadata
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setGroups(List<ResultGroup> groups) {
        this.groups = groups;
    }

    public void setFacets(List<ResultFacet> facets) {
        this.facets = facets;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
