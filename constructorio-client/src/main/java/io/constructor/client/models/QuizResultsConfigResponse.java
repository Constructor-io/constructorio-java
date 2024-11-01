package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/** Constructor.io Quiz Question Response ... uses Gson/Reflection to load data in */
public class QuizResultsConfigResponse {

    @SerializedName("quiz_version_id")
    private String quizVersionId;

    @SerializedName("quiz_id")
    private String quizId;

    @SerializedName("results_config")
    private Map<String, Object> resultsConfig;

    /**
     * @return the quizVersionId. The quiz version id will be returned with the first request and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.com/reference/configuration-quizzes
     */
    public String getQuizVersionId() {
        return quizVersionId;
    }

    /**
     * @return the quizId
     */
    public String getQuizId() {
        return quizId;
    }

    /**
     * @return the quiz results configuration will include the title, description, and response
     *     summary of the results page.
     */
    public Map<String, Object> getResultsConfig() {
        return resultsConfig;
    }

    public void setQuizVersionId(String quizVersionId) {
        this.quizVersionId = quizVersionId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public void setResultsConfig(Map<String, Object> resultsConfig) {
        this.resultsConfig = resultsConfig;
    }
}
