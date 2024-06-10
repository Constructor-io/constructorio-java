package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Item Group ... uses Gson/Reflection to load data in */
public class ResultGroup {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("group_id")
    private String groupId;

    @SerializedName("path")
    private String path;

    @SerializedName("path_list")
    private List<ResultGroupPathListItem> pathList;

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

    /**
     * @return the pathList
     */
    public List<ResultGroupPathListItem> getPathList() {
        return pathList;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPathList(List<ResultGroupPathListItem> pathList) {
        this.pathList = pathList;
    }
}
