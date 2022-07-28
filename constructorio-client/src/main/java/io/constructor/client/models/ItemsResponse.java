package io.constructor.client.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.constructor.client.ConstructorItem;


/**
 * Constructor.io Items Response ... uses Gson/Reflection to load data in
 */
public class ItemsResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("items")
    private List<ConstructorItem> items;

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return the items
     */
    public List<ConstructorItem> getItems() {
        return items;
    }
}
