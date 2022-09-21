package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class QuizImages {

    @SerializedName("primary_url")
    public String primaryUrl;

    @SerializedName("primary_alt")
    public String primaryAlt;

    @SerializedName("secondary_url")
    public String secondaryUrl;

    @SerializedName("secondary_alt")
    public String secondaryAlt;

    /**
     * @return the primary url
     */
    public String getPrimaryUrl() { return primaryUrl; }

    /**
     * @return the primary alt
     */
    public String getPrimaryAlt() { return primaryAlt; }

    /**
     * @return the secondary url
     */
    public String getSecondaryUrl() { return secondaryUrl; }

    /**
     * @return the secondary alt
     */
    public String getSecondaryAlt() { return secondaryAlt; }

}
