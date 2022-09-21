package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class QuizOptionAttribute {

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private String value;

    @SerializedName("images")
    private QuizImages images;

    /**
     * @return the quiz option name
     */
    public String getName() { return name; }

    /**
     * @return the quiz option value.
     */
    public String getValue() { return value; }

    /**
     * @return the quiz option value.
     */
    public QuizImages getImages() { return images; }
}
