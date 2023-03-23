package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Recommendations Response Inner ... uses Gson/Reflection to load data in */
public class RecommendationsResponseInner {

  @SerializedName("results")
  private List<Result> results;

  @SerializedName("total_num_results")
  private Integer totalNumberOfResults;

  @SerializedName("pod")
  private ResultPod pod;

  /**
   * @return the results
   */
  public List<Result> getResults() {
    return results;
  }

  /**
   * @return the totalNumberOfResults
   */
  public Integer getTotalNumberOfResults() {
    return totalNumberOfResults;
  }

  /**
   * @return the pod
   */
  public ResultPod getPod() {
    return pod;
  }

  public void setResults(List<Result> results) {
    this.results = results;
  }

  public void setTotalNumberOfResults(Integer totalNumberOfResults) {
    this.totalNumberOfResults = totalNumberOfResults;
  }

  public void setPod(ResultPod pod) {
    this.pod = pod;
  }
}
