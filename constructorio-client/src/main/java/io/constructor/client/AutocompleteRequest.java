package io.constructor.client;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructor.io Autocomplete Request
 */
public class AutocompleteRequest {

    private String query;
    private Map<String, Integer> resultsPerSection;
    private List<String> hiddenFields;
    private Map<String, List<String>> filters;

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
      this.hiddenFields = new ArrayList<String>();
      this.filters = new HashMap<String, List<String>>();
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
     * @param filters the filters to set
     */
    public void setFilters(Map<String, List<String>> filters) {
      this.filters = filters;
    }

    /**
     * @return the filters
     */
    public Map<String, List<String>> getFilters() {
      return filters;
    }
}