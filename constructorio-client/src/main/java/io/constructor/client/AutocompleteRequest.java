package io.constructor.client;

/**
 * Constructor.io Autocomplete Request
 */
public class AutocompleteRequest {

    private String query;

    /**
     * Creates an autocomplete request
     *
     * @param query the term to return suggestions for
     */
    public AutocompleteRequest(String query) throws IllegalArgumentException {
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