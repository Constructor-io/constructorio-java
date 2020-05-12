package io.constructor.client.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Recommendations Response Inner ... uses Gson/Reflection to load data in
 */
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
}