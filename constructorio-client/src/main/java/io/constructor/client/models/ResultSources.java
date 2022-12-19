package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io ResultSources ... uses Gson/Reflection to load data in
 */

public class ResultSources {

    @SerializedName("token_match")
    private ResultSourcesData tokenMatch;

    @SerializedName("embeddings_match")
    private ResultSourcesData embeddingsMatch;

    /**
     * @return the tokenMatch
     */
    public ResultSourcesData getTokenMatch() {
        return tokenMatch;
    }

    /**
     * @return the embeddingsMatch
     */
    public ResultSourcesData getEmbeddingsMatch() {
        return embeddingsMatch;
    }

    public void setTokenMatch(ResultSourcesData tokenMatch) {
        this.tokenMatch = tokenMatch;
    }

    public void setEmbeddingsMatch(ResultSourcesData embeddingsMatch) {
        this.embeddingsMatch = embeddingsMatch;
    }
}