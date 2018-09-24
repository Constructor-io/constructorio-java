package io.constructor.client;

/**
 * Constructor.io Search Request
 */
public class SearchRequest {

    private String query;
    private String section = "Products";
    private int page = 1;
    private int resultsPerPage = 30;
    private String groupId;
    
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
}