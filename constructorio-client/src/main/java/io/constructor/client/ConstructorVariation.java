package io.constructor.client;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Constructor.io Variation
 */
public class ConstructorVariation {

    private String name;
    private Float suggestedScore;
    private List<String> keywords;
    private String url;
    private String imageUrl;
    private String id;
    private String itemId;
    private String description;
    private Map<String, Object> facets;
    private Map<String, Object> metadata;
    private List<String> groupIds;

    /**
     * Creates a variation.  Optional public fields are in the <a href="https://docs.constructor.io/rest-api.html#add-an-item">API documentation</a>
     *
     * @param id the id of the variation that you are adding.
     * @param itemId the id of the item this variation is attached to.
     * @param name the name of the variation that you are adding.
     */
    public ConstructorVariation(String id, String itemId, String name) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }

        if (itemId == null) {
            throw new IllegalArgumentException("itemId is required");
        }

        this.name = name;
        this.id = id;
        this.itemId = itemId;
        this.suggestedScore = null;
        this.keywords = null;
        this.url = null;
        this.imageUrl = null;
        this.description = null;
        this.facets = null;
        this.metadata = null;
        this.groupIds = null;
    }

    public ConstructorVariation(String id, String itemId) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }

        if (itemId == null) {
            throw new IllegalArgumentException("itemId is required");
        }

        this.id = id;
        this.itemId = itemId;
        this.name = null;
        this.suggestedScore = null;
        this.keywords = null;
        this.url = null;
        this.imageUrl = null;
        this.description = null;
        this.facets = null;
        this.metadata = null;
        this.groupIds = null;
    }

    /**
     * Returns the HashMap form of a variation for converting to JSON
     */
    public Map<String, Object> toMap() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        if (this.metadata != null) {
            dataMap.putAll(this.metadata);
        }

        Map<String, Object> params = new HashMap<String, Object>();


        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }

        params.put("id", this.id);
        params.put("item_id", this.itemId);
        params.put("name", this.name);
        params.put("suggested_score", this.suggestedScore);
        dataMap.put("keywords", this.keywords);
        dataMap.put("url", this.url);
        dataMap.put("image_url", this.imageUrl);
        dataMap.put("facets", this.facets);
        dataMap.put("group_ids", this.groupIds);
        dataMap.put("description", this.description);
        params.put("data", dataMap);
       
        return params;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
 
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the suggestedScore
     */
    public Float getSuggestedScore() {
        return suggestedScore;
    }

    /**
     * @param suggestedScore the suggestedScore to set
     */
    public void setSuggestedScore(Float suggestedScore) {
        this.suggestedScore = suggestedScore;
    }

    /**
     * @return the keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the facets
     */
    public Map<String, Object> getFacets() {
        return facets;
    }
    
    /**
     * @param facets the facets to set
     */
    public void setFacets(Map<String, Object> facets) {
        this.facets = facets;
    }

    /**
     * @return the metadata
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the groupIds
     */
    public List<String> getGroupIds() {
        return groupIds;
    }

    /**
     * @param groupIds the groupIds to set
     */
    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }
}
