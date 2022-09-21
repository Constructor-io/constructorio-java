package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class QuizOption {

    @SerializedName("id")
    private int id;

    @SerializedName("value")
    private String value;

    @SerializedName("attribute")
    private QuizOptionAttribute attribute;

    /**
     * @return the id
     */
    public int getId() { return id; }

    /**
     * @return the value
     */
    public String getValue() { return value; }

    /**
     * @return the quiz option attribute
     */
    public QuizOptionAttribute getAttribute() { return attribute; }
}
