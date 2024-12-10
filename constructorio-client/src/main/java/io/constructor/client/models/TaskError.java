package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Task Error... uses Gson/Reflection to load data in */
public class TaskError {

    @SerializedName("message")
    private String message;

    /**
     * @return the error message
     */
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
