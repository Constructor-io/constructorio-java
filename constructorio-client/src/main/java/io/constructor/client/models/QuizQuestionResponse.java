package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Quiz Question Response ... uses Gson/Reflection to load data in */
public class QuizQuestionResponse {

    @SerializedName("next_question")
    private QuizQuestion nextQuestion;

    @SerializedName("is_last_question")
    private Boolean isLastQuestion;

    @SerializedName("quiz_version_id")
    private String versionId;

    @SerializedName("quiz_session_id")
    private String sessionId;

    @SerializedName("quiz_id")
    private String quizId;

    /**
     * @return the next_question
     */
    public QuizQuestion getNextQuestion() {
        return nextQuestion;
    }

    /**
     * @return the is_last_question
     */
    public Boolean getIsLastQuestion() {
        return isLastQuestion;
    }

    /**
     * @return the quiz_version_id. Version ID will be returned with the first request and it should
     *     be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * @return the quiz_session_id. Session ID will be returned with the first request and it should
     *     be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @return the quiz_id
     */
    public String getQuizId() {
        return quizId;
    }

    public void setNextQuestion(QuizQuestion nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    public void setIsLastQuestion(Boolean isLastQuestion) {
        this.isLastQuestion = isLastQuestion;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
