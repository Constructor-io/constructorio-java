package io.constructor.client;

import java.util.HashMap;

/**
 * Constructor.io Search Request
 */
public class SearchRequest {

    private String term = "";
    private Integer page = 1;
    private Integer resultsPerPage = 24;
    private String groupId = "";
    private HashMap<String, String> facets = new HashMap<String, String>();

    /**
     * Creates a search request
     *
     * @param term the term to search for
     */
    public SearchRequest(String term) throws IllegalArgumentException {
      if (term == null) {
          throw new IllegalArgumentException("term is required");
      }

      this.term = term;
    }

    /**
     * @return the facets
     */
    public HashMap<String, String> getFacets() {
      return facets;
    }

    /**
     * @param facets the facets to set
     */
    public void setFacets(HashMap<String, String> facets) {
      this.facets = facets;
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
     * @return the page
     */
    public Integer getPage() {
      return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
      this.page = page;
    }

    /**
     * @return the resultsPerPage
     */
    public Integer getResultsPerPage() {
      return resultsPerPage;
    }

    /**
     * @param resultsPerPage the resultsPerPage to set
     */
    public void setResultsPerPage(Integer resultsPerPage) {
      this.resultsPerPage = resultsPerPage;
    }

    /**
     * @return the term
     */
    public String getTerm() {
      return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
      this.term = term;
    }
}