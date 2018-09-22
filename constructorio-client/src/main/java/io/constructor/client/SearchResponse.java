package io.constructor.client;

import org.json.JSONObject;

/**
 * Constructor.io Search Response
 */
public class SearchResponse {

    private String resultId;

    /**
     * Creates an autocomplete response
     *
     * @param json the JSON object to create the response from
     */
    public SearchResponse(JSONObject json) throws IllegalArgumentException {
      if (json == null) {
          throw new IllegalArgumentException("json is required");
      }

      this.resultId = json.getString("result_id");
    }

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }
}