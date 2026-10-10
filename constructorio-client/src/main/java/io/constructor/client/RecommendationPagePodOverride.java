package io.constructor.client;

import java.util.List;
import java.util.Map;

/**
 * Constructor.io Recommendation Page Pod Override. Values set here replace (are not merged with)
 * the page-wide values for one pod.
 */
public class RecommendationPagePodOverride {

    private Integer numResults;
    private Map<String, List<String>> facets;
    private Map<String, String> filterMatchTypes;
    private String preFilterExpression;
    private VariationsMap variationsMap;
    private Map<String, String> formatOptions;
    private List<String> hiddenFields;

    /**
     * @param numResults the number of results to return for this pod
     */
    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    /**
     * @return the number of results
     */
    public Integer getNumResults() {
        return numResults;
    }

    /**
     * @param facets the filters for this pod. Replaces all page-wide filters for this pod.
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
     * @param filterMatchTypes whether results must match "all", "any" or "none" of each filter's
     *     values, keyed by filter name
     */
    public void setFilterMatchTypes(Map<String, String> filterMatchTypes) {
        this.filterMatchTypes = filterMatchTypes;
    }

    /**
     * @return the filter match types
     */
    public Map<String, String> getFilterMatchTypes() {
        return filterMatchTypes;
    }

    /**
     * @param preFilterExpression the faceting expression to scope this pod's results (JSON-encoded
     *     query string)
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
     * @param variationsMap the variationsMap for this pod
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
     * @param formatOptions the format options for this pod
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
     * @param hiddenFields the hidden fields to return for this pod
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
