package io.constructor.client.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Facet ... uses Gson/Reflection to load data in
 */
public class SearchFacet {

  @SerializedName("display_name")
  private String displayName;

  @SerializedName("name")
  private String name;

  @SerializedName("status")
  private Map<String, Object> status;

  @SerializedName("max")
  private Integer max;

  @SerializedName("min")
  private Integer min;

  @SerializedName("options")
  private List<SearchFacetOption> options;

  @SerializedName("type")
  private String type;

  /**
   * @return the displayName
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the status
   */
  public Map<String, Object> getStatus() {
    return status;
  }

  /**
   * @return the max
   */
  public Integer getMax() {
    return max;
  }

  /**
   * @return the min
   */
  public Integer getMin() {
    return min;
  }

  /**
   * @return the options
   */
  public List<SearchFacetOption> getOptions() {
    return options;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
}