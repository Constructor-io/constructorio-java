package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/** Constructor.io Autocomplete Response ... uses Gson/Reflection to load data in */
public class AutocompleteResponse {

  @SerializedName("sections")
  private Map<String, List<Result>> sections;

  @SerializedName("result_id")
  private String resultId;

  @SerializedName("request")
  private Map<String, Object> request;

  /**
   * @return the resultId
   */
  public String getResultId() {
    return resultId;
  }

  /**
   * @return the sections
   */
  public Map<String, List<Result>> getSections() {
    return sections;
  }

  /**
   * @return the request as understood by the server
   */
  public Map<String, Object> getRequest() {
    return request;
  }

  public void setSections(Map<String, List<Result>> sections) {
    this.sections = sections;
  }

  public void setResultId(String resultId) {
    this.resultId = resultId;
  }

  public void setRequest(Map<String, Object> request) {
    this.request = request;
  }
}
