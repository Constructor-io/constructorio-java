package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Search Response Inner ... uses Gson/Reflection to load data in */
public class SearchResponseInner extends BaseResultsResponse {

    @SerializedName("redirect")
    private Redirect redirect;

    @SerializedName("refined_content")
    private List<RefinedContent> refinedContent;

    @SerializedName("features")
    private List<Feature> features;

    @SerializedName("related_searches")
    private List<RelatedSearch> relatedSearches;

    @SerializedName("related_browse_pages")
    private List<RelatedBrowsePage> relatedBrowsePages;

    /**
     * @return redirect data
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * @return refined content
     */
    public List<RefinedContent> getRefinedContent() {
        return refinedContent;
    }

    /**
     * @return features list
     */
    public List<Feature> getFeatures() {
        return features;
    }

    public void setRedirect(Redirect redirect) {
        this.redirect = redirect;
    }

    public void setRefinedContent(List<RefinedContent> refinedContent) {
        this.refinedContent = refinedContent;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    /**
     * @return list of related searches
     */
    public List<RelatedSearch> getRelatedSearches() {
        return relatedSearches;
    }

    /**
     * @return list of related browse pages
     */
    public List<RelatedBrowsePage> getRelatedBrowsePages() {
        return relatedBrowsePages;
    }

    /**
     * @param relatedSearches list of related searches to set
     */
    public void setRelatedSearches(List<RelatedSearch> relatedSearches) {
        this.relatedSearches = relatedSearches;
    }

    /**
     * @param relatedBrowsePages list of related browse pages to set
     */
    public void setRelatedBrowsePages(List<RelatedBrowsePage> relatedBrowsePages) {
        this.relatedBrowsePages = relatedBrowsePages;
    }
}
