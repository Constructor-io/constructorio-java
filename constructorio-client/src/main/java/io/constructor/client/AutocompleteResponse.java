package io.constructor.client;

import java.util.HashMap;
import java.util.ArrayList;

public class AutocompleteResponse {

    private HashMap<String, ArrayList<AutocompleteSuggestion>> sections;
    private String resultId;

    /**
     * Creates an autocomplete response
     *
     * @param resultId the identifier of the result set
     * @param sections the sections of the result set
     */
    public AutocompleteResponse(String resultId, HashMap<String, ArrayList<AutocompleteSuggestion>> sections) throws IllegalArgumentException {
      super();

      if (resultId == null) {
          throw new IllegalArgumentException("resultId is required");
      }

      if (sections == null) {
        throw new IllegalArgumentException("sections is required");
      }

      this.resultId = resultId;
      this.sections = sections;
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