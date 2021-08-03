package io.constructor.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Constructor.io Search Request
 */
public class SearchRequest {

    private String query;
    private String section;
    private int page;
    private int resultsPerPage;
    private Map<String, List<String>>facets;
    private String groupId;
    private String sortBy;
    private boolean sortAscending;
    private String collectionId;
    private Map<String, String>formatOptions;
    private List<String> hiddenFields;

    /**
     * Creates a search request
     *
     * @param query the term to return search results for
     */
    public SearchRequest(String query) throws IllegalArgumentException {
      if (query == null) {
          throw new IllegalArgumentException("query is required");
      }

      this.query = query;
      this.section = "Products";
      this.page = 1;
      this.resultsPerPage = 30;
      this.facets = new HashMap<String, List<String>>();
      this.sortAscending = true;
      this.formatOptions = new HashMap<String, String>();
      this.hiddenFields = new ArrayList<String>();
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
      this.query = query;
    }

    /**
     * @return the query
     */
    public String getQuery() {
      return query;
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
     * @param collectionId the collectionId to set
     */
    public void setCollectionId(String collectionId) {
      this.collectionId = collectionId;
    }

    /**
     * @return the collectionId
     */
    public String getCollectionId() {
      return collectionId;
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
}