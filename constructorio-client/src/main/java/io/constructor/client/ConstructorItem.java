package io.constructor.client;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Constructor.io Item
 */
public class ConstructorItem {

    private String itemName;
    private Integer suggestedScore;
    private ArrayList<String> keywords;
    private String url;
    private String imageUrl;
    private String id;
    private String description;
    private HashMap <String, String> facets;
    private HashMap <String, String> metadata;
    private ArrayList<String> groupIds;

    /**
     * Creates an autocomplete item.  Optional public fields are in the <a href="https://docs.constructor.io/rest-api.html#add-an-item">API documentation</a>
     *
     * @param itemName the name of the item that you are adding.
     */
    public ConstructorItem(String itemName) throws IllegalArgumentException {
        if (itemName == null) {
            throw new IllegalArgumentException("itemName is required");
        }

        this.itemName = itemName;
        this.suggestedScore = null;
        this.keywords = null;
        this.url = null;
        this.imageUrl = null;
        this.description = null;
        this.id = null;
        this.facets = null;
        this.metadata = null;
        this.groupIds = null;
    }

    /**
     * Returns the HashMap form of an autocomplete item for converting to JSON
     */
    public  HashMap<String, Object> toHashMap() {
        HashMap<String, Object> params = new HashMap<String, Object>();

        if (itemName == null) {
            throw new IllegalArgumentException("itemName is required");
        }

        params.put("item_name", this.itemName);
        params.put("suggested_score", this.suggestedScore);
        params.put("keywords", this.keywords);
        params.put("url", this.url);
        params.put("image_url", this.imageUrl);
        params.put("description", this.description);
        params.put("id", this.id);
        params.put("facets", this.facets);
        params.put("metadata", this.metadata);
        params.put("group_ids", this.groupIds);
       
        return params;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }
 
    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    /**
     * @return the suggestedScore
     */
    public Integer getSuggestedScore() {
        return suggestedScore;
    }

    /**
     * @param suggestedScore the suggestedScore to set
     */
    public void setSuggestedScore(Integer suggestedScore) {
        this.suggestedScore = suggestedScore;
    }

    /**
     * @return the keywords
     */
    public ArrayList<String> getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(ArrayList<String> keywords) {
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
     * @return the facets
     */
    public HashMap<String, String> getFacets() {
        return facets;
    }
    
    /**
     * @param facets the facets to set
     */
    public void setFacets(HashMap<String, String> facets) {
        this.facets = facets;
    }

    /**
     * @return the metadata
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }
    
    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(HashMap<String, String> metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the groupIds
     */
    public ArrayList<String> getGroupIds() {
        return groupIds;
    }

    /**
     * @param groupIds the groupIds to set
     */
    public void setGroupIds(ArrayList<String> groupIds) {
        this.groupIds = groupIds;
    }
}
