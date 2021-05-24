package io.constructor.client.models;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Item collection data ... uses Gson/Reflection to load data in
 */
public class ItemCollection {

    @SerializedName("id")
    private String id;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("data")
    private Map<String, Object> data;

    /**
     * @return the id
     */
    public String getId() {
      return id;
    }

    /**
     * @return the display name
     */
    public String getDisplayName() {
      return displayName;
    }

    /**
     * @return the collection data
     */
    public Map<String, Object> getData() {
      return data;
    }
}