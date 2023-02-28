package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io All Redirects Response ... uses Gson/Reflection to load data in
 */
public class AllRedirectsResponse {

    @SerializedName("redirect_rules")
    private List<RedirectRule> redirectRules;

    @SerializedName("total_count")
    private int totalCount;

    /**
     * @return the redirect_rules
     */
    public List<RedirectRule> getRedirectRules() {
        return redirectRules;
    }

    /**
     * @return the total_count
     */
    public int getTotalCount() {
        return totalCount;
    }

    public void setRedirectRules(List<RedirectRule> redirectRules) { this.redirectRules = redirectRules;}

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}