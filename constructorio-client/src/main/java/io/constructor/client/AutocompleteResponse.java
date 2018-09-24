package io.constructor.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Autocomplete Response
 */
public class AutocompleteResponse {

    private Map<String, List<AutocompleteSuggestion>> sections;
    private String resultId;

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
      this.sections = new HashMap<String, List<AutocompleteSuggestion>>();

      JSONObject sectionsJSON = json.getJSONObject("sections");
      for(Object sectionKey : sectionsJSON.keySet()) {
        String sectionName = (String)sectionKey;
        List<AutocompleteSuggestion> items = new ArrayList<AutocompleteSuggestion>();
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
    public Map<String, List<AutocompleteSuggestion>> getSections() {
      return sections;
    }
}