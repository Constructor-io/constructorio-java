package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Redirect ... uses Gson/Reflection to load data in
 */

public class Redirect {

  @SerializedName("data")
  private RedirectData data;

  @SerializedName("matched_terms")
  private List<String> matchedTerms;

  @SerializedName("matched_user_segments")
  private List<String> matchedUserSegments;

  /**
   * @return the data
   */
  public RedirectData getData() {
    return data;
  }

  /**
   * @return the matchedTerms
   */
  public List<String> getMatchedTerms() {
    return matchedTerms;
  }

  /**
   * @return the matchedUserSegments
   */
  public List<String> getMatchedUserSegments() {
    return matchedUserSegments;
  }
}