package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizQuestion {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("options")
    private List<QuizOption> options;

    /**
     * @return the id
     */
    public int getId() { return id; }

    /**
     * @return the title
     */
    public String getTitle() { return title; }

    /**
     * @return the type
     */
    public String getType() { return type; }

    /**
     * @return the options
     */
    public List<QuizOption> getOptions() { return options; }

}
