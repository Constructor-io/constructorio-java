package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.constructor.client.models.Task;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTaskResponseWithQueuedTaskShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.task.queued.json");
        Task response = ConstructorIO.createTaskResponse(string);

        assertEquals("id exists", 0, response.getId());
        assertEquals("status exists", "QUEUED", response.getStatus());
        assertEquals(
                "submission_time exists", "2020-04-24T15:06:27Z", response.getSubmissionTime());
        assertNull(response.getProtocol());
        assertNull(response.getLastUpdate());
        assertNull(response.getFilename());
        assertNull(response.getStartTime());
        assertNull(response.getType());
        assertNull(response.getResult());
    }

    @Test
    public void createTaskResponseWithErrorTaskShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.task.error.json");
        Task response = ConstructorIO.createTaskResponse(string);
        JsonObject jsonObj = new Gson().toJsonTree(response.getError()).getAsJsonObject();

        assertEquals("id exists", 100721281, response.getId());
        assertEquals("status exists", "FAILED", response.getStatus());
        assertEquals(
                "Error message exists",
                "IngestionError: The items file you have uploaded is empty. Please fix it and try"
                        + " again.",
                jsonObj.get("message").getAsString());
    }

    @Test
    public void createTaskResponseWithDoneTaskShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.task.done.json");
        Task response = ConstructorIO.createTaskResponse(string);
        JsonObject jsonObj = new Gson().toJsonTree(response.getResult()).getAsJsonObject();

        assertEquals("id exists", 0, response.getId());
        assertEquals("status exists", "DONE", response.getStatus());
        assertEquals(
                "submission_time exists", "2020-04-24T15:06:27Z", response.getSubmissionTime());
        assertEquals("last_update exists", "2020-04-24T15:08:27Z", response.getLastUpdate());
        assertEquals("start_time exists", "2020-04-24T15:07:27Z", response.getStartTime());
        assertNotNull("changelog exists", jsonObj.getAsJsonObject("changelog"));
        assertNotNull(
                "sections exists",
                jsonObj.getAsJsonObject("changelog").getAsJsonObject("sections"));
        assertNotNull(
                "Products exists",
                jsonObj.getAsJsonObject("changelog")
                        .getAsJsonObject("sections")
                        .getAsJsonObject("Products"));
        assertEquals(
                "items_updated exists",
                10,
                jsonObj.getAsJsonObject("changelog")
                        .getAsJsonObject("sections")
                        .getAsJsonObject("Products")
                        .get("items_updated")
                        .getAsInt());
        assertEquals(
                "items_deleted exists",
                0,
                jsonObj.getAsJsonObject("changelog")
                        .getAsJsonObject("sections")
                        .getAsJsonObject("Products")
                        .get("items_deleted")
                        .getAsInt());
        assertEquals(
                "variations_updated exists",
                20,
                jsonObj.getAsJsonObject("changelog")
                        .getAsJsonObject("sections")
                        .getAsJsonObject("Products")
                        .get("variations_updated")
                        .getAsInt());
        assertEquals(
                "variations_deleted exists",
                5,
                jsonObj.getAsJsonObject("changelog")
                        .getAsJsonObject("sections")
                        .getAsJsonObject("Products")
                        .get("variations_deleted")
                        .getAsInt());
        assertEquals(
                "index_built exists",
                true,
                jsonObj.getAsJsonObject("changelog").get("index_built").getAsBoolean());
    }
}
