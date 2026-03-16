package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Related Search - uses Gson/Reflection to load data in
 */
public class RelatedSearch {

    @SerializedName("query")
    private String query;

    /**
     * @return the related search query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the related search query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
