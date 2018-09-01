package io.constructor.client;

import java.util.HashMap;
import java.util.ArrayList;

import com.google.gson.Gson;

public class ConstructorItem {

    public String itemName;
    public String autocompleteSection;
    public Integer suggestedScore;
    public ArrayList<String> keywords;
    public String url;
    public String imageUrl;
    public String id;
    public String description;
    public HashMap <String, String> facets;
    public HashMap <String, String> metadata;
    public ArrayList<String> groupIds;

    /**
     * Creates an autocomplete item.  Optional public fields are in the <a href="https://constructor.io/docs/#add-an-item">API documentation</a>
     *
     * @param itemName the item that you're adding.
     * @param autocompleteSection  the autocomplete section you are adding the item to
     */
    public ConstructorItem(String itemName, String autocompleteSection) throws IllegalArgumentException {
        super();

        if (itemName == null) {
            throw new IllegalArgumentException("itemName is required");
        }
        if (autocompleteSection == null) {
            throw new IllegalArgumentException("autocompleteSection is required");
        }

        this.itemName = itemName;
        this.autocompleteSection = autocompleteSection;
        this.suggestedScore = 0;
        this.keywords = new ArrayList<String>();
        this.url = "";
        this.imageUrl = "";
        this.description = "";
        this.id = "";
        this.facets = new HashMap <String, String>();
        this.metadata = new HashMap <String, String>();
        this.groupIds = new ArrayList<String>();
    }

    /**
     * Returns the JSON form of an autocomplete item
     */
    public String toJson() {
        HashMap<String, Object> params = new HashMap<String, Object>();

        if (itemName == null) {
            throw new IllegalArgumentException("itemName is required");
        }
        if (autocompleteSection == null) {
            throw new IllegalArgumentException("autocompleteSection is required");
        }

        params.put("item_name", this.itemName);
        params.put("autocomplete_section", this.autocompleteSection);
        params.put("suggested_score", this.suggestedScore);
        params.put("keywords", this.keywords);
        params.put("url", this.url);
        params.put("image_url", this.imageUrl);
        params.put("description", this.description);
        params.put("id", this.id);
        params.put("facets", this.facets);
        params.put("metadata", this.metadata);
        params.put("group_ids", this.groupIds);
        Gson gson = new Gson();
        return gson.toJson(params);
    }
}
