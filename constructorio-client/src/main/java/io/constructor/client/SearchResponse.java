package io.constructor.client;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Response ... uses Gson/Reflection to load data in
 */
public class SearchResponse {

    @SerializedName("result_id")
    private String resultId;

    @SerializedName("response")
    private SearchResponseInner response;
    
    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the response
     */
    public SearchResponseInner getResponse() {
      return response;
    }
}