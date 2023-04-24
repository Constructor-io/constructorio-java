package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Browse Response Inner ... uses Gson/Reflection to load data in */
public class BrowseResponseInner extends BaseResultsResponse {

    @SerializedName("collection")
    private ItemCollection collection;

    /**
     * @return the item collection data
     */
    public ItemCollection getCollection() {
        return collection;
    }

    public void setCollection(ItemCollection collection) {
        this.collection = collection;
    }
}
