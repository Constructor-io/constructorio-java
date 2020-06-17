package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Browse Response ... uses Gson/Reflection to load data in
 */
public class BrowseResponse {

    @SerializedName("result_id")
    private String resultId;

    @SerializedName("response")
    private BrowseResponseInner response;
    
    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the response
     */
    public BrowseResponseInner getResponse() {
      return response;
    }
}