package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/** Constructor.io Result ... uses Gson/Reflection to load data in */
public class Result {

    @SerializedName("value")
    private String value;

    @SerializedName("data")
    private ResultData data;

    @SerializedName("matched_terms")
    private List<String> matchedTerms;

    @SerializedName("variations")
    private List<Result> variations;

    @SerializedName("variations_map")
    private Object variationsMap;

    // The value is returned in a list, but practically it would only be a single value list
    @SerializedName("variation_slice")
    private Map<String, List<String>> variationSlice;

    @SerializedName("is_slotted")
    private boolean isSlotted;

    @SerializedName("labels")
    private Map<String, Object> labels;

    @SerializedName("strategy")
    private Map<String, String> strategy;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the data
     */
    public ResultData getData() {
        return data;
    }

    /**
     * @return the matchedTerms
     */
    public List<String> getMatchedTerms() {
        return matchedTerms;
    }

    /**
     * @return the variations
     */
    public List<Result> getVariations() {
        return variations;
    }

    /**
     * @return the variationsMap
     */
    public Object getVariationsMap() {
        return variationsMap;
    }

    /**
     * @return the labels
     */
    public Map<String, Object> getLabels() {
        return labels;
    }

    /**
     * @return the strategy
     */
    public Map<String, String> getStrategy() {
        return strategy;
    }

    /**
     * @return the variationSlice
     */
    public Map<String, List<String>> getVariationSlice() {
        return variationSlice;
    }

    /**
     * @return isSlotted boolean
     */
    public boolean getIsSlotted() {
        return isSlotted;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setData(ResultData data) {
        this.data = data;
    }

    public void setMatchedTerms(List<String> matchedTerms) {
        this.matchedTerms = matchedTerms;
    }

    public void setVariations(List<Result> variations) {
        this.variations = variations;
    }

    public void setVariationsMap(Object variationsMap) {
        this.variationsMap = variationsMap;
    }

    public void setLabels(Map<String, Object> labels) {
        this.labels = labels;
    }

    public void setStrategy(Map<String, String> strategy) {
        this.strategy = strategy;
    }

    public void setVariationSlice(Map<String, List<String>> variationSlice) {
        this.variationSlice = variationSlice;
    }

    public void setIsSlotted(boolean isSlotted) {
        this.isSlotted = isSlotted;
    }
}
