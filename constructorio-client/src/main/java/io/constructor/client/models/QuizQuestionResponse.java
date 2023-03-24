package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Quiz Question Response ... uses Gson/Reflection to load data in */
public class QuizQuestionResponse {

    @SerializedName("next_question")
    private QuizQuestion nextQuestion;

    @SerializedName("is_last_question")
    private Boolean isLastQuestion;

    @SerializedName("version_id")
    private String versionId;

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
     * @return the version_id
     */
    public String getVersionId() {
        return versionId;
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
}
