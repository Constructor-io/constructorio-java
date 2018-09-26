package io.constructor.client.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Autocomplete Response ... uses Gson/Reflection to load data in
 */
public class AutocompleteResponse {

    @SerializedName("sections")
    private Map<String, List<Result>> sections;

    @SerializedName("result_id")
    private String resultId;

    /**
     * @return the resultId
     */
    public String getResultId() {
      return resultId;
    }

    /**
     * @return the sections
     */
    public Map<String, List<Result>> getSections() {
      return sections;
    }
}