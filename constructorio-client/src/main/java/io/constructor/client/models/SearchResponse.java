package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Constructor.io Search Response ... uses Gson/Reflection to load data in */
public class SearchResponse {

    @SerializedName("result_id")
    private String resultId;

    @SerializedName("response")
    private SearchResponseInner response;

    @SerializedName("request")
    private Map<String, Object> request;

    private transient Map<String, List<String>> headers = Collections.emptyMap();

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

    /**
     * @return the HTTP response headers
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public void setResponse(SearchResponseInner response) {
        this.response = response;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = (headers != null) ? headers : Collections.emptyMap();
    }
}
