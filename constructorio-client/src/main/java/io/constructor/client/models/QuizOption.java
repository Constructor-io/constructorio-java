package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class QuizOption {

    @SerializedName("id")
    private int id;

    @SerializedName("value")
    private String value;

    /**
     * @return the id
     */
    public int getId() { return id; }

    /**
     * @return the value
     */
    public String getValue() { return value; }
}
