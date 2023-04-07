package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class QuizResult {

    @SerializedName("filter_expression")
    private Map<String, Object> filterExpression;

    @SerializedName("results_url")
    private String resultsUrl;

    /**
     * @return the filter_expression
     */
    public Map<String, Object> getFilterExpression() {
        return filterExpression;
    }

    /**
     * @return the result_url
     */
    public String getResultsUrl() {
        return resultsUrl;
    }

    public void setFilterExpression(Map<String, Object> filterExpression) {
        this.filterExpression = filterExpression;
    }

    public void setResultsUrl(String resultsUrl) {
        this.resultsUrl = resultsUrl;
    }
}
