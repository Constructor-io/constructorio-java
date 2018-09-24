package io.constructor.client;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Search Response
 */
public class SearchResponse {

    private String resultId;
    private List<SearchFacet> facets;
    private List<SearchGroup> groups;
    private List<SearchResult> results;
    private int totalNumberOfResults;

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
      this.facets = new ArrayList<SearchFacet>();
      this.groups = new ArrayList<SearchGroup>();
      this.results = new ArrayList<SearchResult>();

      JSONObject responseJSON = json.getJSONObject("response");

      JSONArray facetsJSON = responseJSON.getJSONArray("facets");
      for (int i = 0; i < facetsJSON.length(); i++) {
          JSONObject facetJSON = facetsJSON.getJSONObject(i);
          SearchFacet facet = new SearchFacet(facetJSON);
          this.facets.add(facet);
      }

      JSONArray groupsJSON = responseJSON.getJSONArray("groups");
      for (int i = 0; i < groupsJSON.length(); i++) {
          JSONObject groupJSON = groupsJSON.getJSONObject(i);
          SearchGroup group = new SearchGroup(groupJSON);
          this.groups.add(group);
      }

      JSONArray resultsJSON = responseJSON.getJSONArray("results");
      for (int i = 0; i < resultsJSON.length(); i++) {
          JSONObject resultJSON = resultsJSON.getJSONObject(i);
          SearchResult result = new SearchResult(resultJSON);
          this.results.add(result);
      }

      this.totalNumberOfResults = responseJSON.getInt("total_num_results");
    }

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the facets
     */
    public List<SearchFacet> getFacets() {
      return facets;
    }

    /**
     * @return the groups
     */
    public List<SearchGroup> getGroups() {
      return groups;
    }

    /**
     * @return the results
     */
    public List<SearchResult> getResults() {
      return results;
    }

    /**
     * @return the totalNumberOfResults
     */
    public int getTotalNumberOfResults() {
      return totalNumberOfResults;
    }
}