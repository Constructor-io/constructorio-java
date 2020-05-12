package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Pod ... uses Gson/Reflection to load data in
 */

public class ResultPod {

  @SerializedName("display_name")
  private String displayName;

  @SerializedName("id")
  private String id;

  /**
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return the values
   */
  public String getId() {
    return id;
  }
}