package io.constructor.client;

import io.constructor.client.models.AllTasksResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class AllTasksResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createAllTaskResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.alltasks.json");
        AllTasksResponse response = ConstructorIO.createAllTasksResponse(string);

        assertEquals("20 tasks exists", 20, response.getTasks().size());
        assertEquals("total_count exists", 4733, response.getTotalCount());
        assertEquals("status_count - QUEUED exists", 6, response.getStatusCounts().getQueued());
        assertEquals("status_count - IN_PROGRESS exists", 0, response.getStatusCounts().getInProgress());
        assertEquals("status_count - DONE exists", 20, response.getStatusCounts().getDone());
        assertEquals("status_count - FAILED exists", 0, response.getStatusCounts().getFailed());
    }

}
