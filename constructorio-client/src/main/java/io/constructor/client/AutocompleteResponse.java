package io.constructor.client;

import java.util.HashMap;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Autocomplete Response
 */
public class AutocompleteResponse {

    private HashMap<String, ArrayList<AutocompleteSuggestion>> sections;
    private String resultId;
    private String clientVersion;

    /**
     * Creates an autocomplete response
     *
     * @param json the JSON object to create the response from
     */
    public AutocompleteResponse(JSONObject json) throws IllegalArgumentException {
      if (json == null) {
          throw new IllegalArgumentException("json is required");
      }

      this.resultId = json.getString("result_id");
      this.sections = new HashMap<String, ArrayList<AutocompleteSuggestion>>();

      JSONObject sectionsJSON = json.getJSONObject("sections");
      for(Object sectionKey : sectionsJSON.keySet()) {
        String sectionName = (String)sectionKey;
        ArrayList<AutocompleteSuggestion> items = new ArrayList<AutocompleteSuggestion>();
        JSONArray resultsJSON = sectionsJSON.getJSONArray(sectionName);
        for (int i = 0; i < resultsJSON.length(); i++) {
            JSONObject suggestionJSON = resultsJSON.getJSONObject(i);
            AutocompleteSuggestion suggestion = new AutocompleteSuggestion(suggestionJSON);
            items.add(suggestion);
        }
        sections.put(sectionName, items);
      }
    }

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the sections
     */
    public HashMap<String, ArrayList<AutocompleteSuggestion>> getSections() {
      return sections;
    }
}