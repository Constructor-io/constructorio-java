package io.constructor.client;

/** Constructor.io Get Task Request */
public class TaskRequest {
    private String taskId;

    /**
     * Creates a Get Task request
     *
     * @param taskId the task_id to request
     */
    public TaskRequest(String taskId) {
        if (taskId == null || taskId.isEmpty()) {
            throw new IllegalArgumentException("task_id is required");
        }
        this.taskId = taskId;
    }

    /**
     * @param taskId the task_id to request
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the task_id to return
     */
    public String getTaskId() {
        return taskId;
    }
}
