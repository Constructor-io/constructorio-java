package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class RefinedContent {

  @SerializedName("data")
  private Map<String, String> data;

  /**
   * @return refined content data
   */
  public Map<String, String> getData() {
    return data;
  }
}
