package io.constructor.client;

import java.util.Map;
import java.util.HashMap;

/**
 * Constructor.io Autocomplete Request
 */
public class AutocompleteRequest {

    private String query;
    private Map<String, Integer> resultsPerSection;

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
      this.resultsPerSection = new HashMap<String, Integer>();
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
     * @param resultsPerSection the resultsPerSection to set
     */
    public void setResultsPerSection(Map<String, Integer> resultsPerSection) {
      this.resultsPerSection = resultsPerSection;
    }

    /**
     * @return the resultsPerSection
     */
    public Map<String, Integer> getResultsPerSection() {
      return resultsPerSection;
    }
}