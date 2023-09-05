package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Browse Response Inner ... uses Gson/Reflection to load data in */
public class BrowseResponseInner extends BaseResultsResponse {

    @SerializedName("collection")
    private ItemCollection collection;

    @SerializedName("refined_content")
    private List<RefinedContent> refinedContent;

    /**
     * @return the item collection data
     */
    public ItemCollection getCollection() {
        return collection;
    }

    /**
     * @return refined content
     */
    public List<RefinedContent> getRefinedContent() {
        return refinedContent;
    }

    public void setCollection(ItemCollection collection) {
        this.collection = collection;
    }

    public void setRefinedContent(List<RefinedContent> refinedContent) {
        this.refinedContent = refinedContent;
    }
}
