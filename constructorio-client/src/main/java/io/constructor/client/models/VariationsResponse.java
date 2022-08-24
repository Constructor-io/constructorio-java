package io.constructor.client.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.constructor.client.ConstructorVariation;


/**
 * Constructor.io Variations Response ... uses Gson/Reflection to load data in
 */
public class VariationsResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("variations")
    private List<ConstructorVariation> variations;

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return the variations
     */
    public List<ConstructorVariation> getVariations() {
        return variations;
    }
}
