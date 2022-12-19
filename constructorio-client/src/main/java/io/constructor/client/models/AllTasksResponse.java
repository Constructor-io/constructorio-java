package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io All Tasks Response ... uses Gson/Reflection to load data in
 */
public class AllTasksResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("tasks")
    private List<Task> tasks;

    @SerializedName("status_counts")
    private StatusCounts statusCounts;

    /**
     * @return the total_count
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @return the status_counts
     */
    public StatusCounts getStatusCounts() {
        return statusCounts;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setStatusCounts(StatusCounts statusCounts) {
        this.statusCounts = statusCounts;
    }
}