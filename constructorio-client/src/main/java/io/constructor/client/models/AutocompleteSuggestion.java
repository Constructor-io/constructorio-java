package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Autocomplete Suggestion ... uses Gson/Reflection to load data in
 */
public class AutocompleteSuggestion {

    @SerializedName("value")
    private String value;

    @SerializedName("data")
    private ItemData data;

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
    public ItemData getData() {
      return data;
    }

    /**
     * @return the matchedTerms
     */
    public List<String> getMatchedTerms() {
      return matchedTerms;
    }
}