package io.constructor.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Constructor.io Recommendations Request */
public class RecommendationsRequest {

    private String podId;
    private String term;
    private int numResults;
    private List<String> itemIds;
    private Map<String, List<String>> facets;
    private String section;
    private String preFilterExpression;
    private VariationsMap variationsMap;
    private Map<String, String> formatOptions;
    private List<String> hiddenFields;

    /**
     * Creates a recommendations request
     *
     * @param podId the pod id to retrieve results from
     */
    public RecommendationsRequest(String podId) throws IllegalArgumentException {
        if (podId == null) {
            throw new IllegalArgumentException("podId is a required parameter of type string");
        }

        this.podId = podId;
        this.numResults = 10;
        this.itemIds = null;
        this.term = null;
        this.section = "Products";
        this.variationsMap = null;
        this.preFilterExpression = null;
        this.facets = new HashMap<String, List<String>>();
        this.formatOptions = new HashMap<String, String>();
        this.hiddenFields = new ArrayList<String>();
    }

    /**
     * @param podId the pod id to set
     */
    public void setPodId(String podId) {
        this.podId = podId;
    }

    /**
     * @return the pod id
     */
    public String getPodId() {
        return podId;
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
     * @param numResults the num results to set
     */
    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    /**
     * @return the num results
     */
    public int getNumResults() {
        return numResults;
    }

    /**
     * @param itemIds the item id's to set
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
     * @param variationsMap the variationsMap to set
     */
    public void setVariationsMap(VariationsMap variationsMap) {
        this.variationsMap = variationsMap;
    }

    /**
     * @return the variations map
     */
    public VariationsMap getVariationsMap() {
        return variationsMap;
    }

    /**
     * @param facets the facets to set
     */
    public void setFacets(Map<String, List<String>> facets) {
        this.facets = facets;
    }

    /**
     * @return the facets
     */
    public Map<String, List<String>> getFacets() {
        return facets;
    }

    /**
     * @param preFilterExpression the faceting expression to scope recommendation results
     *     (JSON-encoded query string). Please refer to
     *     https://docs.constructor.com/reference/shared-filter-expressions
     */
    public void setPreFilterExpression(String preFilterExpression) {
        this.preFilterExpression = preFilterExpression;
    }

    /**
     * @return the prefilter expression
     */
    public String getPreFilterExpression() {
        return preFilterExpression;
    }

    /**
     * @param formatOptions the formatOptions to set. Please refer to
     *     https://docs.constructor.com/reference/v1-recommendations-get-pod-results for details
     */
    public void setFormatOptions(Map<String, String> formatOptions) {
        this.formatOptions = formatOptions;
    }

    /**
     * @return the format options
     */
    public Map<String, String> getFormatOptions() {
        return formatOptions;
    }

    /**
     * @param hiddenFields the hiddenFields to set
     */
    public void setHiddenFields(List<String> hiddenFields) {
        this.hiddenFields = hiddenFields;
    }

    /**
     * @return the hidden fields
     */
    public List<String> getHiddenFields() {
        return hiddenFields;
    }
}
