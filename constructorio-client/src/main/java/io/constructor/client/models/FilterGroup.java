package io.constructor.client.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Filter Group ... uses Gson/Reflection to load data in
 */
public class FilterGroup {

    @SerializedName("children")
    private List<FilterGroup> children;

    @SerializedName("parents")
    private List<FilterGroup> parents;

    @SerializedName("count")
    private Integer count;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("group_id")
    private String groupId;

    @SerializedName("data")
    private Map<String, Object> data;

    /**
     * @return the children
     */
    public List<FilterGroup> getChildren() {
      return children;
    }

    /**
     * @return the parents
     */
    public List<FilterGroup> getParents() {
      return parents;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
      return count;
    }

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
     * @return the data
     */
    public Map<String, Object> getData() {
      return data;
    }
}