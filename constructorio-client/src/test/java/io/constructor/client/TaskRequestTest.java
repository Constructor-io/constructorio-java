package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskRequestTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullTaskIdShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new TaskRequest(null);
    }

    @Test
    public void newShouldReturnTaskRequest() throws Exception {
        String taskId = "320";
        TaskRequest request = new TaskRequest(taskId);
        assertEquals(request.getTaskId(), taskId);
    }

    @Test
    public void settersShouldSet() throws Exception {
        String taskId = "320";
        TaskRequest request = new TaskRequest(taskId);
        assertEquals(request.getTaskId(), taskId);

        request.setTaskId("322");
        assertEquals(request.getTaskId(), "322");
    }
}
