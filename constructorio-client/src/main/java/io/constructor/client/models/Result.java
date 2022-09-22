package io.constructor.client.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Result ... uses Gson/Reflection to load data in
 */
public class Result {

  @SerializedName("value")
  private String value;

  @SerializedName("data")
  private ResultData data;

  @SerializedName("matched_terms")
  private List<String> matchedTerms;

  @SerializedName("variations")
  private List<Result> variations;

  @SerializedName("variations_map")
  private Object variationsMap;

  @SerializedName("labels")
  private Map<String, Boolean> labels;

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @return the data
   */
  public ResultData getData() {
    return data;
  }

  /**
   * @return the matchedTerms
   */
  public List<String> getMatchedTerms() {
    return matchedTerms;
  }

  /**
   * @return the variations
   */
  public List<Result> getVariations() {
    return variations;
  }

  /**
   * @return the variationsMap
   */
  public Object getVariationsMap() {
    return variationsMap;
  }

  /**
   * @return the labels
   */
  public Map<String, Boolean> getLabels() { return labels; }
}