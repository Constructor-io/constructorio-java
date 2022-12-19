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
    public int getId() { return id; }

    /**
     * @param id the id
     */
    public void setId(int id) { this.id = id; }

    /**
     * @return the type
     */
    public String getType() { return type; }

    /**
     * @param type the type
     */
    public void setType(String type) { this.type = type; }

    /**
     * @return the status
     */
    public String getStatus() { return status; }

    /**
     * @param status the status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * @return the submission_time
     */
    public String getSubmissionTime() { return submissionTime; }

    /**
     * @param submissionTime the submission_time
     */
    public void setSubmissionTime(String submissionTime) { this.submissionTime = submissionTime; }

    /**
     * @return the last_update
     */
    public String getLastUpdate() { return lastUpdate; }

    /**
     * @param lastUpdate the last_update
     */
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }

    /**
     * @return the filename
     */
    public String getFilename() { return filename; }

    /**
     * @param filename the filename
     */
    public void setFilename(String filename) { this.filename = filename; }

    /**
     * @return the start_time
     */
    public String getStartTime() { return startTime; }

    /**
     * @param startTime the start_time
     */
    public void setStartTime(String startTime) { this.startTime = startTime; }

    /**
     * @return the protocol
     */
    public String getProtocol() { return protocol; }

    /**
     * @param protocol the protocol
     */
    public void setProtocol(String protocol) { this.protocol = protocol; }

    /**
     * @return the result
     */
    public Object getResult() { return result; }

    /**
     * @param result the result
     */
    public void setResult(Object result) { this.result = result; }

}