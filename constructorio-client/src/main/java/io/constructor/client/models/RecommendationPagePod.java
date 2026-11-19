package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/** One pod of a Constructor.io Recommendation Page Response */
public class RecommendationPagePod {

    @SerializedName("pod_id")
    private String podId;

    @SerializedName("request")
    private Map<String, Object> request;

    @SerializedName("response")
    private RecommendationsResponseInner response;

    @SerializedName("result_id")
    private String resultId;

    /**
     * @return the pod id
     */
    public String getPodId() {
        return podId;
    }

    /**
     * @return the pod's effective request: page-wide parameters with this pod's overrides applied
     */
    public Map<String, Object> getRequest() {
        return request;
    }

    /**
     * @return the pod's response, in the same shape as a single-pod recommendations response
     */
    public RecommendationsResponseInner getResponse() {
        return response;
    }

    /**
     * @return the pod's result id. Send this with the pod's recommendation view and click events.
     */
    public String getResultId() {
        return resultId;
    }

    public void setPodId(String podId) {
        this.podId = podId;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    public void setResponse(RecommendationsResponseInner response) {
        this.response = response;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }
}
