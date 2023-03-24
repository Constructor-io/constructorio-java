package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Redirect Rule Match ... uses Gson/Reflection to load data in */
public class RedirectRuleMatch {
    @SerializedName("id")
    private int id;

    @SerializedName("match_type")
    private String matchType;

    @SerializedName("pattern")
    private String pattern;

    /**
     * @return the redirect match pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @return the redirect match type
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * @return the id of the redirect match
     */
    public int getId() {
        return id;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public void setId(int id) {
        this.id = id;
    }
}
