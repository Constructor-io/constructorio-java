package io.constructor.client.models;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Recommendations Response ... uses Gson/Reflection to load data in
 */
public class RecommendationsResponse {

    @SerializedName("result_id")
    private String resultId;

    @SerializedName("response")
    private RecommendationsResponseInner response;

    @SerializedName("request")
    private Map<String, Object> metadata;

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the response
     */
    public RecommendationsResponseInner getResponse() {
      return response;
    }
}