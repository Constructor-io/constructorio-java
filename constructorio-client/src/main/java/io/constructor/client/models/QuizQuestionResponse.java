package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Quiz Question Response ... uses Gson/Reflection to load data in */
public class QuizQuestionResponse {

    @SerializedName("next_question")
    private QuizQuestion nextQuestion;

    @SerializedName("is_last_question")
    private Boolean isLastQuestion;

    @SerializedName("quiz_version_id")
    private String quizVersionId;

    @SerializedName("quiz_session_id")
    private String quizSessionId;

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
     * @return the quizVersionId. The quiz version id will be returned with the first request and it should
     *     be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public String getQuizVersionId() {
        return quizVersionId;
    }

    /**
     * @return the quizSessionId. The quiz session id will be returned with the first request
     *     and it should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public String getQuizSessionId() {
        return quizSessionId;
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
