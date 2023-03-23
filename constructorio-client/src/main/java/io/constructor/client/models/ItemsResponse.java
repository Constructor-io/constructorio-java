package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import io.constructor.client.ConstructorItem;
import java.util.List;

/** Constructor.io Items Response ... uses Gson/Reflection to load data in */
public class ItemsResponse {

  @SerializedName("total_count")
  private int totalCount;

  @SerializedName("items")
  private List<ConstructorItem> items;

  /**
   * @return the totalCount
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * @return the items
   */
  public List<ConstructorItem> getItems() {
    return items;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public void setItems(List<ConstructorItem> items) {
    this.items = items;
  }
}
