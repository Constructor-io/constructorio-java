package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Status_Counts ... uses Gson/Reflection to load data in
 */
public class StatusCounts {

    @SerializedName("QUEUED")
    private int queued;

    @SerializedName("DONE")
    private int done;

    @SerializedName("IN_PROGRESS")
    private int inProgress;

    @SerializedName("FAILED")
    private int failed;

    /**
     * @return the QUEUED status count
     */
    public int getQueued() {
        return queued;
    }

    /**
     * @return the DONE status count
     */
    public int getDone() {
        return done;
    }

    /**
     * @return the IN_PROGRESS status count
     */
    public int getInProgress() { return inProgress; }

    /**
     * @return the FAILED status count
     */
    public int getFailed() { return failed; }

}
