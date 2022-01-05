package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Task ... uses Gson/Reflection to load data in
 */
public class Task {

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("submission_time")
    private String submissionTime;

    @SerializedName("last_update")
    private String lastUpdate;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("filename")
    private String filename;

    @SerializedName("protocol")
    private String protocol;

    @SerializedName("result")
    private Object result;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the status
     */
    public String getStatus() { return status; }

    /**
     * @return the submission_time
     */
    public String getSubmissionTime() { return submissionTime; }

    /**
     * @return the last_update
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the start_time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }

}