package io.constructor.client;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Facet ... uses Gson/Reflection to load data in
 */
public class SearchFacet {

  @SerializedName("name")
  private String name;

  @SerializedName("options")
  private List<SearchFacetOption> options;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the options
   */
  public List<SearchFacetOption> getOptions() {
    return options;
  }
}