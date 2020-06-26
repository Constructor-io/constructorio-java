package io.constructor.client.models;

import java.util.List;

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
}