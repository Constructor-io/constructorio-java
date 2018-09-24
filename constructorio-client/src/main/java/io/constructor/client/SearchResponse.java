package io.constructor.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Search Response
 */
public class SearchResponse {

    private String resultId;
    private ArrayList<SearchResult> searchResults;

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
      this.searchResults = new ArrayList<SearchResult>();

      JSONObject responseJSON = json.getJSONObject("response");
      JSONArray resultsJSON = responseJSON.getJSONArray("results");
      for (int i = 0; i < resultsJSON.length(); i++) {
          JSONObject resultJSON = resultsJSON.getJSONObject(i);
          SearchResult searchResult = new SearchResult(resultJSON);
          this.searchResults.add(searchResult);
      }
    }

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the searchResults
     */
    public ArrayList<SearchResult> getSearchResults() {
      return searchResults;
    }
}