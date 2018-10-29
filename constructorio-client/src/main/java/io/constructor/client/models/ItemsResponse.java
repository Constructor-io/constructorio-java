package io.constructor.client.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Autocomplete Response ... uses Gson/Reflection to load data in
 */
public class ItemsResponse {

  @SerializedName("items")
  private List<Item> items;

  @SerializedName("total_count")
  private Integer totalCount;

  /**
   * @return the totalCount
   */
  public Integer getTotalCount() {
    return totalCount;
  }

  /**
   * @return the items
   */
  public List<Item> getItems() {
    return items;
  }
}