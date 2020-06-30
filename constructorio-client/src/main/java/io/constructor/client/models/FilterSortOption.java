package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Sort Option ... uses Gson/Reflection to load data in
 */
public class FilterSortOption {
  
  @SerializedName("display_name")
  private String displayName;

  @SerializedName("sort_by")
  private String sortBy;

  @SerializedName("sort_order")
  private String sortOrder;
  
  @SerializedName("status")
  private String status;

  /**
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return the sort by
   */
  public String getSortBy() {
    return sortBy;
  }

  /**
   * @return the sort order
   */
  public String getSortOrder() {
    return sortOrder;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }
  
}