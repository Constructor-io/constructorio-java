package io.constructor.client;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Autocomplete Suggestion ... uses Gson/Reflection to load data in
 */
public class AutocompleteSuggestion {

    @SerializedName("value")
    private String value;

    @SerializedName("data")
    private Map<String, Object> data;

    @SerializedName("matched_terms")
    private List<String> matchedTerms;

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