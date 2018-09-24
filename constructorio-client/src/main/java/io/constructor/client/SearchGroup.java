package io.constructor.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Search Group
 */
public class SearchGroup {

    private ArrayList<SearchGroup> children;
    private int count;
    private String displayName;
    private String groupId;

    /**
     * Creates a search group
     *
     * @param query the term to return suggestions for
     */
    public SearchGroup(JSONObject json) throws IllegalArgumentException {
      if (json == null) {
          throw new IllegalArgumentException("json is required");
      }

      this.children = new ArrayList<SearchGroup>();
      this.count = json.getInt("count");
      this.displayName = json.getString("display_name");
      this.groupId = json.getString("group_id");

      JSONArray childrenJSON = json.getJSONArray("children");
      for (int i = 0; i < childrenJSON.length(); i++) {
        JSONObject childJSON = childrenJSON.getJSONObject(i);
        SearchGroup child = new SearchGroup(childJSON);
        this.children.add(child);
      }
    }

    /**
     * @return the children
     */
    public ArrayList<SearchGroup> getChildren() {
      return children;
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