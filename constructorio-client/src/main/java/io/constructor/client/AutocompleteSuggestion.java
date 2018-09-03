package io.constructor.client;

import java.util.HashMap;
import java.util.ArrayList;

public class AutocompleteSuggestion {

    private String value;
    private HashMap<String, Object> data;
    private ArrayList<String> matchedValues;

    /**
     * Creates an autocomplete response suggestion
     *
     * @param value the value to display
     * @param data metadata associated with the suggestion
     * @param matchedValues the matches that prompted the suggestion
     */
    public AutocompleteSuggestion(String value, HashMap<String, Object>data, ArrayList<String> matchedValues) throws IllegalArgumentException {
      super();

      if (value == null) {
          throw new IllegalArgumentException("value is required");
      }

      if (data == null) {
        throw new IllegalArgumentException("data is required");
      }

      if (matchedValues == null) {
        throw new IllegalArgumentException("matchedValues is required");
      }


      this.value = value;
      this.data = data;
      this.matchedValues = matchedValues;
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
    public HashMap<String, Object> getData() {
      return data;
    }

    /**
     * @return the matchedValues
     */
    public ArrayList<String> getMatchedValues() {
      return matchedValues;
    }
}