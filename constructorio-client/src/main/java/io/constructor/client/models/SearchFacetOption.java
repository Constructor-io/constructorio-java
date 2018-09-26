package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Facet Option ... uses Gson/Reflection to load data in
 */
public class SearchFacetOption {

  @SerializedName("count")
  private int count;

  @SerializedName("value")
  private String value;

  /**
   * @return the count
   */
  public int getCount() {
    return count;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }
}