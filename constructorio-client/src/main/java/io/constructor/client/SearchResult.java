package io.constructor.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Search Result
 */
public class SearchResult {

    private String value;
    private Map<String, Object> data;
    private List<String> matchedTerms;

    /**
     * Creates a search response result
     *
     * @param json the JSON object to create the result from
     */
    public SearchResult(JSONObject json) throws IllegalArgumentException {
      if (json == null) {
          throw new IllegalArgumentException("json is required");
      }

      this.value = json.getString("value");
      this.data = new HashMap<String, Object>();
      this.matchedTerms = new ArrayList<String>();

      JSONArray matchedTermsJSON = json.getJSONArray("matched_terms");
      for (int i = 0; i < matchedTermsJSON.length(); i++) {
        String matchedValue = matchedTermsJSON.getString(i);
        matchedTerms.add(matchedValue);
      }

      JSONObject dataJSON = json.getJSONObject("data");
      for(Object dataKey : dataJSON.keySet()) {
        String dataName = (String)dataKey;
        Object dataValue = dataJSON.get(dataName);
        this.data.put(dataName, dataValue);
      }
    }

    /**
     * @return the value
     */
    public String getValue() {
      return value;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
      return data;
    }

    /**
     * @return the matchedTerms
     */
    public List<String> getMatchedTerms() {
      return matchedTerms;
    }
}