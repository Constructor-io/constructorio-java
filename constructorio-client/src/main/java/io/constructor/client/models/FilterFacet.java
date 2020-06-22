package io.constructor.client.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Filter Facet ... uses Gson/Reflection to load data in
 */
public class FilterFacet {

  @SerializedName("display_name")
  private String displayName;

  @SerializedName("name")
  private String name;

  @SerializedName("status")
  private Map<String, Object> status;

  @SerializedName("max")
  private Double max;

  @SerializedName("min")
  private Double min;

  @SerializedName("options")
  private List<FilterFacetOption> options;

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
  public Double getMax() {
    return max;
  }

  /**
   * @return the min
   */
  public Double getMin() {
    return min;
  }

  /**
   * @return the options
   */
  public List<FilterFacetOption> getOptions() {
    return options;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
}