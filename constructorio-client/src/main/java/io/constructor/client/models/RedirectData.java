package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Redirect Data ... uses Gson/Reflection to load data in
 */
public class RedirectData {

  @SerializedName("url")
  private String url;

  @SerializedName("rule_id")
  private Integer ruleId;

  @SerializedName("match_id")
  private Integer matchId;

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the ruleId
   */
  public Integer getRuleId() {
    return ruleId;
  }

  /**
   * @return the matchId
   */
  public Integer getMatchId() {
    return matchId;
  }
}