package io.constructor.client.models;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class QuizResult {

    @SerializedName("filter_expression")
    private Map<String, Object> filterExpression;

    @SerializedName("browse_url")
    private String browseUrl;

    /**
     * @return the filter_expression
     */
    public Map<String, Object> getFilterExpression() { return filterExpression; }

    /**
     * @return the browse_url
     */
    public String getBrowseUrl() { return browseUrl; }
}
