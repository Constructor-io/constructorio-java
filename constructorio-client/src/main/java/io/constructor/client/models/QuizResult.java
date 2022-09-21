package io.constructor.client.models;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class QuizResult {

    @SerializedName("filter_expression")
    private Map<String, Object> filterExpression;

    @SerializedName("result_url")
    private String resultUrl;

    /**
     * @return the filter_expression
     */
    public Map<String, Object> getFilterExpression() { return filterExpression; }

    /**
     * @return the result_url
     */
    public String getResultUrl() { return resultUrl; }
}
