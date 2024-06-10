package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Item Group ... uses Gson/Reflection to load data in */
public class ResultGroupPathListItem {
    @SerializedName("id")
    private String id;

    @SerializedName("display_name")
    private String displayName;

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the groupId
     */
    public String getId() {
        return id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setGroupId(String id) {
        this.id = id;
    }
}
