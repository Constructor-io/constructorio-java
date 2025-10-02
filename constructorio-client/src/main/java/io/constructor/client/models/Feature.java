package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class Feature {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("feature_name")
    private String featureName;

    @SerializedName("enabled")
    private Boolean enabled;

    @SerializedName("variant")
    private Variant variant;

    /**
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the feature name
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * @return enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @return the variant
     */
    public Variant getVariant() {
        return variant;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
}
