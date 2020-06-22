package io.constructor.client.models;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Filter Facet Option ... uses Gson/Reflection to load data in
 */
public class FilterFacetOption {

  @SerializedName("count")
  private Integer count;

  @SerializedName("data")
  private Map<String, Object> data;

  @SerializedName("display_name")
  private String displayName;

  @SerializedName("status")
  private String status;

  @SerializedName("value")
  private String value;

  /**
   * @return the counts
   */
  public Integer getCount() {
    return count;
  }

  /**
   * @return the data
   */
  public Map<String, Object> getData() {
    return data;
  }

  /**
   * @return the displayName
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }
}