package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Recommendation Page Response Inner ... uses Gson/Reflection to load data in */
public class RecommendationPageResponseInner {

    @SerializedName("page_id")
    private String pageId;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("page_type")
    private String pageType;

    @SerializedName("pods")
    private List<RecommendationPagePod> pods;

    /**
     * @return the page id
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * @return the page display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the page type
     */
    public String getPageType() {
        return pageType;
    }

    /**
     * @return the pods, in the page's configured order
     */
    public List<RecommendationPagePod> getPods() {
        return pods;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public void setPods(List<RecommendationPagePod> pods) {
        this.pods = pods;
    }
}
