package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Quiz Question Response ... uses Gson/Reflection to load data in
 */
public class QuizQuestionResponse {

    @SerializedName("next_question")
    private QuizQuestion quizQuestion;

    @SerializedName("is_last_question")
    private Boolean isLastQuestion;

    @SerializedName("version_id")
    private String versionId;

    /**
     * @return the next_question
     */
    public QuizQuestion getQuizQuestion() { return quizQuestion; }

    /**
     * @return the is_last_question
     */
    public Boolean getIsLastQuestion() { return isLastQuestion; }

    /**
     * @return the version_id
     */
    public String getVersionId() { return versionId; }
}
