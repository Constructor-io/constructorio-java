package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class RefinedContent {

  @SerializedName("data")
  private RefinedContentData data;

  /**
   * @return refined content data
   */
  public RefinedContentData getData() {
    return data;
  }
}
