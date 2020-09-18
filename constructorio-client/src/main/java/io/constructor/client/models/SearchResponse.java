package io.constructor.client.models;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Response ... uses Gson/Reflection to load data in
 */
public class SearchResponse {

    @SerializedName("result_id")
    private String resultId;

    @SerializedName("response")
    private SearchResponseInner response;

    @SerializedName("request")
    private Map<String, Object> request;

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

    /**
     * @return the request as understood by the server
     */
    public Map<String, Object> getRequest() {
      return request;
    }
}