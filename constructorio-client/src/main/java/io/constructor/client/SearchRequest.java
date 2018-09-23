package io.constructor.client;

/**
 * Constructor.io Search Request
 */
public class SearchRequest {

    private String query;

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
}