package io.constructor.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Constructor.io Recommendation Page Request. Page-wide values apply to every pod on the page; use
 * {@link #setPodOverrides(Map)} to give one pod its own values.
 */
public class RecommendationPageRequest extends RecommendationPagePodOverride {

    private String pageId;
    private String term;
    private List<String> itemIds;
    private String variationId;
    private String section;
    private Map<String, RecommendationPagePodOverride> podOverrides;

    /**
     * Creates a recommendation page request
     *
     * @param pageId the page id to retrieve results for
     */
    public RecommendationPageRequest(String pageId) throws IllegalArgumentException {
        if (pageId == null) {
            throw new IllegalArgumentException("pageId is a required parameter of type string");
        }

        this.pageId = pageId;
        this.section = "Products";
        this.podOverrides = new LinkedHashMap<String, RecommendationPagePodOverride>();
    }

    /**
     * @param pageId the page id to set
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    /**
     * @return the page id
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * @param term the term to set (required for query recommendations pods)
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param itemIds the item id's to set (required for item-based pods)
     */
    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    /**
     * @return the item id's
     */
    public List<String> getItemIds() {
        return itemIds;
    }

    /**
     * @param variationId the variation id to set. Can be used with exactly one item id.
     */
    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    /**
     * @return the variation id
     */
    public String getVariationId() {
        return variationId;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param podOverrides per-pod values keyed by pod id. Each replaces (is not merged with) the
     *     page-wide value for that pod.
     */
    public void setPodOverrides(Map<String, RecommendationPagePodOverride> podOverrides) {
        this.podOverrides = podOverrides;
    }

    /**
     * @return the pod overrides
     */
    public Map<String, RecommendationPagePodOverride> getPodOverrides() {
        return podOverrides;
    }
}
