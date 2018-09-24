package io.constructor.client;

import org.json.JSONObject;

/**
 * Constructor.io Search Facet
 */
public class SearchFacetOption {

  private int count;
  private String value;
  
  /**
   * Creates a search facet option
   */
  SearchFacetOption(JSONObject json) throws IllegalArgumentException {
    if (json == null) {
        throw new IllegalArgumentException("json is required");
    }

    this.count = json.getInt("count");
    this.value = json.getString("value");
  }

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