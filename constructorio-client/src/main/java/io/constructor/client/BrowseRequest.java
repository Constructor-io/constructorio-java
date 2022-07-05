package io.constructor.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Constructor.io Browse Request
 */
public class BrowseRequest {

    private String filterName;
    private String filterValue;
    private String section;
    private int page;
    private int resultsPerPage;
    private Map<String, List<String>>facets;
    private String groupId;
    private String sortBy;
    private boolean sortAscending;
    private Map<String, String>formatOptions;
    private List<String> hiddenFields;
    private List<String> hiddenFacets;
    private VariationsMap variationsMap;
    
    /**
     * Creates a browse request
     *
     * @param filterName filter name to display results from
     * @param filterValue filter value to display results from
     */
    public BrowseRequest(String filterName, String filterValue) throws IllegalArgumentException {
      if (filterName == null) {
          throw new IllegalArgumentException("filterName is required");
      }
      if (filterValue == null) {
        throw new IllegalArgumentException("filterValue is required");
    }

      this.filterName = filterName;
      this.filterValue = filterValue;
      this.section = "Products";
      this.page = 1;
      this.resultsPerPage = 30;
      this.facets = new HashMap<String, List<String>>();
      this.sortAscending = true;
      this.formatOptions = new HashMap<String, String>();
      this.hiddenFields = new ArrayList<String>();
      this.hiddenFacets = new ArrayList<String>();
      this.variationsMap = null;
    }

    /**
     * @param filterName the filterName to set
     */
    public void setFilterName(String filterName) {
      this.filterName = filterName;
    }

    /**
     * @return the filterName
     */
    public String getFilterName() {
      return filterName;
    }

    /**
     * @param filterValue the filterValue to set
     */
    public void setFilterValue(String filterValue) {
      this.filterValue = filterValue;
    }

    /**
     * @return the filterValue
     */
    public String getFilterValue() {
      return filterValue;
    }

    /**
     * @return the section
     */
    public String getSection() {
      return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
      this.section = section;
    }

    /**
     * @return the page
     */
    public int getPage() {
      return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
      this.page = page;
    }

    /**
     * @return the resultsPerPage
     */
    public int getResultsPerPage() {
      return resultsPerPage;
    }

    /**
     * @param resultsPerPage the resultsPerPage to set
     */
    public void setResultsPerPage(int resultsPerPage) {
      this.resultsPerPage = resultsPerPage;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
      return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
      this.groupId = groupId;
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
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
      this.sortBy = sortBy;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
      return sortBy;
    }

    /**
     * @param sortAscending the sortAscending to set
     */
    public void setSortAscending(boolean sortAscending) {
      this.sortAscending = sortAscending;
    }

    /**
     * @return the sortAscending
     */
    public boolean getSortAscending() {
      return sortAscending;
    }

    /**
     * @param formatOptions the formatOptions to set
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

    /**
     * @param hiddenFacets the hiddenFacets to set
     */
    public void setHiddenFacets(List<String> hiddenFacets) {
      this.hiddenFacets = hiddenFacets;
    }

    /**
     * @return the hidden facets
     */
    public List<String> getHiddenFacets() {
      return hiddenFacets;
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
}