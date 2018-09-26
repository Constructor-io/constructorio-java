package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Item Facet ... uses Gson/Reflection to load data in
 */

public class ResultFacet {

  @SerializedName("name")
  private String name;

  @SerializedName("values")
  private List<String> values;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the values
   */
  public List<String> getValues() {
    return values;
  }
}