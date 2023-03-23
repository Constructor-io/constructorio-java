package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/** Constructor.io Recommendations Response ... uses Gson/Reflection to load data in */
public class RecommendationsResponse {

  @SerializedName("result_id")
  private String resultId;

  @SerializedName("response")
  private RecommendationsResponseInner response;

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
  public RecommendationsResponseInner getResponse() {
    return response;
  }

  /**
   * @return the request as understood by the server
   */
  public Map<String, Object> getRequest() {
    return request;
  }

  public void setResultId(String resultId) {
    this.resultId = resultId;
  }

  public void setResponse(RecommendationsResponseInner response) {
    this.response = response;
  }

  public void setRequest(Map<String, Object> request) {
    this.request = request;
  }
}
