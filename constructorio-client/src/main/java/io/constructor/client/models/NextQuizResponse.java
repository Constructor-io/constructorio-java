package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Next Quiz Response ... uses Gson/Reflection to load data in
 */
public class NextQuizResponse {

    @SerializedName("next_question")
    private QuizQuestion nextQuestion;

    @SerializedName("is_skippable")
    private Boolean isSkippable;

    @SerializedName("is_last_question")
    private Boolean isLastQuestion;

    @SerializedName("is_all_skippable")
    private Boolean isAllSkippable;

    @SerializedName("version_id")
    private String versionId;

    /**
     * @return the next_question
     */
    public QuizQuestion getNextQuestion() { return nextQuestion; }

    /**
     * @return the is_skippable
     */
    public Boolean getIsSkippable() { return isSkippable; }

    /**
     * @return the is_last_question
     */
    public Boolean getIsLastQuestion() { return isLastQuestion; }

    /**
     * @return the is_all_skippable
     */
    public Boolean getIsAllSkippable() { return isAllSkippable; }

    /**
     * @return the version_id
     */
    public String getVersionId() { return versionId; }
}
