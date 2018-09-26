package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Item Group ... uses Gson/Reflection to load data in
 */

public class ResultGroup {

  @SerializedName("display_name")
  private String displayName;

  @SerializedName("group_id")
  private String groupId;

  @SerializedName("path")
  private String path;

  /**
   * @return the displayName
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * @return the groupId
   */
  public String getGroupId() {
    return groupId;
  }

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }
}