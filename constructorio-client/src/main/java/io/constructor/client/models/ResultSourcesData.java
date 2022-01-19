package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io ResultSources Data ... uses Gson/Reflection to load data in
 */
public class ResultSourcesData {

  @SerializedName("count")
  private Integer count;

  /**
   * @return the count
   */
  public Integer getCount() {
    return count;
  }
}