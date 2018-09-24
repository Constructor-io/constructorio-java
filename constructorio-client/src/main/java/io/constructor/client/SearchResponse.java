package io.constructor.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Search Response
 */
public class SearchResponse {

    private String resultId;
    private ArrayList<SearchResult> results;
    private ArrayList<SearchFacet> facets;

    /**
     * Creates a search response
     *
     * @param json the JSON object to create the response from
     */
    public SearchResponse(JSONObject json) throws IllegalArgumentException {
      if (json == null) {
          throw new IllegalArgumentException("json is required");
      }

      this.resultId = json.getString("result_id");
      this.results = new ArrayList<SearchResult>();
      this.facets = new ArrayList<SearchFacet>();

      JSONObject responseJSON = json.getJSONObject("response");
      JSONArray resultsJSON = responseJSON.getJSONArray("results");
      for (int i = 0; i < resultsJSON.length(); i++) {
          JSONObject resultJSON = resultsJSON.getJSONObject(i);
          SearchResult result = new SearchResult(resultJSON);
          this.results.add(result);
      }

      JSONArray facetsJSON = responseJSON.getJSONArray("facets");
      for (int i = 0; i < facetsJSON.length(); i++) {
          JSONObject facetJSON = facetsJSON.getJSONObject(i);
          SearchFacet facet = new SearchFacet(facetJSON);
          this.facets.add(facet);
      }
    }

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the results
     */
    public ArrayList<SearchResult> getResults() {
      return results;
    }

    /**
     * @return the facets
     */
    public ArrayList<SearchFacet> getFacets() {
      return facets;
    }
}