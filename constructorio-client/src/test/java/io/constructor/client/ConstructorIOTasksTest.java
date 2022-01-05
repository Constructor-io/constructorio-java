package io.constructor.client;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import io.constructor.client.models.AllTasksResponse;
import io.constructor.client.models.Task;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.*;

public class ConstructorIOTasksTest {

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
    public void AllTasksAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllTasksRequest request = new AllTasksRequest();
        String response = constructor.allTasksAsJson(request);
        assertFalse("Returns a response", response.isEmpty());
    }

    @Test
    public void AllTasksShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllTasksRequest request = new AllTasksRequest();
        AllTasksResponse response = constructor.allTasks(request);
        assertNotNull("Returns a response", response);
    }

    @Test
    public void AllTasksShouldReturnAListOfTasks() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllTasksRequest request = new AllTasksRequest();
        AllTasksResponse response = constructor.allTasks(request);

        Task task = response.getTasks().stream().filter(new Predicate<Task>() {
            @Override
            public boolean test(Task t) {
                return t.getId() == task_id;
            }
        }).findAny().orElse(null);

        assertTrue("At least one task exists", response.getTotalCount() >= 1);
        assertTrue("Previously uploaded task_id exists", task != null);
    }

    @Test
    public void AllTasksAsJSONShouldReturnAListOfTasks() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllTasksRequest request = new AllTasksRequest();
        String response = constructor.allTasksAsJson(request);
        JSONObject jsonObj = new JSONObject(response);

        JSONArray tasksArray = jsonObj.getJSONArray("tasks");
        JSONObject task = null;

        for(int i = 0; i < tasksArray.length(); i++)
        {
            JSONObject obj = tasksArray.getJSONObject(i);
            if (obj.getInt("id") == task_id)
            {
                task = obj;
            }
        }

        assertTrue("At least one task exists", jsonObj.getInt("total_count") >= 1);
        assertTrue("Previously uploaded task_id exists", task != null);
    }

    @Test
    public void AllTasksShouldReturnErrorWithInvalidApiKey() throws Exception {
        try {
            ConstructorIO constructor = new ConstructorIO(apiToken, "notanapikey", true, null);
            AllTasksRequest request = new AllTasksRequest();
            AllTasksResponse response = constructor.allTasks(request);
        } catch (ConstructorException e)
        {
            assertEquals("[HTTP 401] You have supplied an invalid `key` or `autocomplete_key`. You can find your key at app.constructor.io/dashboard/accounts/api_integration.", e.getCause().getMessage());
        }
    }

    @Test
    public void AllTasksShouldReturnErrorWithInvalidApiToken() throws Exception {
        try {
            ConstructorIO constructor = new ConstructorIO("notanapitoken", apiKey, true, null);
            AllTasksRequest request = new AllTasksRequest();
            AllTasksResponse response = constructor.allTasks(request);
        } catch (ConstructorException e)
        {
            assertEquals("[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one at app.constructor.io/dashboard", e.getCause().getMessage());
        }
    }

    @Test
    public void AllTasksAsJSONShouldReturnErrorWithInvalidApiKey() throws Exception {
        try {
            ConstructorIO constructor = new ConstructorIO(apiToken, "notanapikey", true, null);
            AllTasksRequest request = new AllTasksRequest();
            String response = constructor.allTasksAsJson(request);
        } catch (ConstructorException e)
        {
            assertEquals("[HTTP 401] You have supplied an invalid `key` or `autocomplete_key`. You can find your key at app.constructor.io/dashboard/accounts/api_integration.", e.getCause().getMessage());
        }
    }

    @Test
    public void AllTasksAsJSONShouldReturnErrorWithInvalidApiToken() throws Exception {
        try {
            ConstructorIO constructor = new ConstructorIO("notanapitoken", apiKey, true, null);
            AllTasksRequest request = new AllTasksRequest();
            String response = constructor.allTasksAsJson(request);
        } catch (ConstructorException e)
        {
            assertEquals("[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one at app.constructor.io/dashboard", e.getCause().getMessage());
        }
    }

}