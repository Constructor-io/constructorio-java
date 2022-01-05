package io.constructor.client;

import io.constructor.client.models.AllTasksResponse;
import io.constructor.client.models.Task;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConstructorIOTaskTest {

    private String apiKey = System.getenv("TEST_API_KEY");
    private String apiToken = System.getenv("TEST_API_TOKEN");
    private File csvFolder = new File("src/test/resources/csv");
    private File itemsFile = new File("src/test/resources/csv/items.csv");
    private String baseUrl = "https://raw.githubusercontent.com/Constructor-io/integration-examples/main/catalog/";
    private int task_id = 0;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws Exception{
        URL itemsUrl = new URL(baseUrl + "items.csv");
        FileUtils.copyURLToFile(itemsUrl, itemsFile);

        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.replaceCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        task_id = jsonObj.getInt("task_id");
    }

    @After
    public void teardown() throws Exception{
        itemsFile.delete();
        csvFolder.delete();
    }

    @Test
    public void TaskShouldRetrieveTaskGivenTaskId() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        TaskRequest request = new TaskRequest(String.valueOf(task_id));
        Task response = constructor.task(request);

        assertEquals("Returns the same Task Id", task_id, response.getId());
    }

    @Test
    public void TaskAsJSONShouldRetrieveTaskGivenTaskId() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        TaskRequest request = new TaskRequest(String.valueOf(task_id));
        String response = constructor.taskAsJson(request);
        JSONObject jsonObject = new JSONObject(response);
        int respTaskId = jsonObject.getInt("id");

        assertEquals("Returns the same Task Id", task_id, respTaskId);
    }

    @Test
    public void TaskRequestWithInvalidTokenShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO("notavalidtoken", apiKey, true, null);
        TaskRequest request = new TaskRequest(String.valueOf(task_id));

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one at app.constructor.io/dashboard");
        Task response = constructor.task(request);
    }

    @Test
    public void TaskAsJSONRequestWithInvalidTokenShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO("notavalidtoken", apiKey, true, null);
        TaskRequest request = new TaskRequest(String.valueOf(task_id));

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one at app.constructor.io/dashboard");
        String response = constructor.taskAsJson(request);
    }

    @Test
    public void TaskRequestWithInvalidApiKeyShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, "notavalidkey", true, null);
        TaskRequest request = new TaskRequest(String.valueOf(task_id));

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 400] We have no record of this key. You can find your key at app.constructor.io/dashboard.");
        Task response = constructor.task(request);
    }

    @Test
    public void TaskAsJSONRequestWithInvalidApiKeyShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, "notavalidkey", true, null);
        TaskRequest request = new TaskRequest(String.valueOf(task_id));

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 400] We have no record of this key. You can find your key at app.constructor.io/dashboard.");
        String response = constructor.taskAsJson(request);
    }

}