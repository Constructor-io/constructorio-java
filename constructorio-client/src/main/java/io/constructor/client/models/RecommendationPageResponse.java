package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Constructor.io Recommendation Page Response ... uses Gson/Reflection to load data in.
 *
 * <p>The top-level result id identifies the page request and is not a tracking id: send each pod's
 * own {@link RecommendationPagePod#getResultId()} with that pod's tracking events.
 */
public class RecommendationPageResponse {

    @SerializedName("result_id")
    private String resultId;

    @SerializedName("response")
    private RecommendationPageResponseInner response;

    @SerializedName("request")
    private Map<String, Object> request;

    /**
     * @return the id of the page request. Not a tracking id.
     */
    public String getResultId() {
        return resultId;
    }

    /**
     * @return the response
     */
    public RecommendationPageResponseInner getResponse() {
        return response;
    }

    /**
     * @return the page request as understood by the server
     */
    public Map<String, Object> getRequest() {
        return request;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public void setResponse(RecommendationPageResponseInner response) {
        this.response = response;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }
}
