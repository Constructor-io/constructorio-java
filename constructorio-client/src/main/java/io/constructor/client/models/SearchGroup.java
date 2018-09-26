package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Group ... uses Gson/Reflection to load data in
 */
public class SearchGroup {

    @SerializedName("children")
    private List<SearchGroup> children;

    @SerializedName("parents")
    private List<SearchGroup> parents;

    @SerializedName("count")
    private int count;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("group_id")
    private String groupId;

    /**
     * @return the children
     */
    public List<SearchGroup> getChildren() {
      return children;
    }

    /**
     * @return the parents
     */
    public List<SearchGroup> getParents() {
      return parents;
    }

    /**
     * @return the count
     */
    public int getCount() {
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
}