package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Quiz Results Response ... uses Gson/Reflection to load data in
 */
public class QuizResultsResponse {

    @SerializedName("version_id")
    private String versionId;

    @SerializedName("result")
    private QuizResult result;

    /**
     * @return the version_id
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * @return the result
     */
    public QuizResult getResult() { return result; }
}