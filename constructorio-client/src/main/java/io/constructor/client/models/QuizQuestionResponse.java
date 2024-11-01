package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Quiz Question Response ... uses Gson/Reflection to load data in */
public class QuizQuestionResponse {

    @SerializedName("next_question")
    private QuizQuestion nextQuestion;

    @SerializedName("quiz_version_id")
    private String quizVersionId;

    @SerializedName("quiz_session_id")
    private String quizSessionId;

    @SerializedName("quiz_id")
    private String quizId;

    /**
     * @return the nextQuestion
     */
    public QuizQuestion getNextQuestion() {
        return nextQuestion;
    }

    /**
     * @return the quizVersionId. The quiz version id will be returned with the first request and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.com/reference/configuration-quizzes
     */
    public String getQuizVersionId() {
        return quizVersionId;
    }

    /**
     * @return the quizSessionId. The quiz session id will be returned with the first request and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.com/reference/configuration-quizzes
     */
    public String getQuizSessionId() {
        return quizSessionId;
    }

    /**
     * @return the quizId
     */
    public String getQuizId() {
        return quizId;
    }

    public void setNextQuestion(QuizQuestion nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    public void setQuizVersionId(String quizVersionId) {
        this.quizVersionId = quizVersionId;
    }

    public void setQuizSessionId(String quizSessionId) {
        this.quizSessionId = quizSessionId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
