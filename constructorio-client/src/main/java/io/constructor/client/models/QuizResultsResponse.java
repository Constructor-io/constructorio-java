package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/** Constructor.io Quiz Results Response ... uses Gson/Reflection to load data in */
public class QuizResultsResponse {

    @SerializedName("quiz_version_id")
    private String quizVersionId;

    @SerializedName("quiz_session_id")
    private String quizSessionId;

    @SerializedName("quiz_id")
    private String quizId;

    @SerializedName("request")
    private Map<String, Object> request;

    @SerializedName("response")
    private QuizResult response;

    /**
     * @return the quiz_version_id for the quiz Version ID will bereturned with the first request
     *     and it should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public String getVersionId() {
        return quizVersionId;
    }

    /**
     * @return the quiz_session_id for the quiz. Session ID will be returned with the first request
     *     and it should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public String getSessionId() {
        return quizSessionId;
    }

    /**
     * @return the quiz_id
     */
    public String getQuizId() {
        return quizId;
    }

    /**
     * @return the result
     */
    public QuizResult getResponse() {
        return response;
    }

    /**
     * @return the request as understood by the server
     */
    public Map<String, Object> getRequest() {
        return request;
    }

    public void setVersionId(String quizVersionId) {
        this.quizVersionId = quizVersionId;
    }

    public void setQuizSessionId(String quizSessionId) {
        this.quizSessionId = quizSessionId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public void setResponse(QuizResult response) {
        this.response = response;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }
}
