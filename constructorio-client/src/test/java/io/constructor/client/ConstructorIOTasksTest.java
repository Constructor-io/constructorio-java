package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.AllTasksResponse;
import io.constructor.client.models.Task;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.commons.io.FileUtils;
import org.hamcrest.core.StringContains;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOTasksTest {

  private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
  private static String apiToken = System.getenv("TEST_API_TOKEN");
  private static File csvFolder = new File("src/test/resources/csv");
  private static File itemsFile = new File("src/test/resources/csv/items.csv");
  private static String baseUrl =
      "https://raw.githubusercontent.com/Constructor-io/integration-examples/main/catalog/";
  private static int task_id = 0;

  @Rule public ExpectedException thrown = ExpectedException.none();

  @BeforeClass
  public static void init() throws Exception {
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

  @AfterClass
  public static void teardown() throws Exception {
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

    request.setResultsPerPage(5);
    request.setPage(1);

    AllTasksResponse response = constructor.allTasks(request);

    Task task =
        response.getTasks().stream()
            .filter(
                new Predicate<Task>() {
                  @Override
                  public boolean test(Task t) {
                    return t.getId() == task_id;
                  }
                })
            .findAny()
            .orElse(null);

    assertTrue("At least one task exists", response.getTotalCount() >= 1);
    assertNotNull("Previously uploaded task_id exists", task);
  }

  @Test
  public void AllTasksAsJSONShouldReturnAListOfTasks() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();
    String response = constructor.allTasksAsJson(request);
    JSONObject jsonObj = new JSONObject(response);

    JSONArray tasksArray = jsonObj.getJSONArray("tasks");
    JSONObject task = null;

    for (int i = 0; i < tasksArray.length(); i++) {
      JSONObject obj = tasksArray.getJSONObject(i);
      if (obj.getInt("id") == task_id) {
        task = obj;
      }
    }

    assertTrue("At least one task exists", jsonObj.getInt("total_count") >= 1);
    assertNotNull("Previously uploaded task_id exists", task);
  }

  @Test
  public void AllTasksShouldReturnAListOfTasksWhenStartAndEndDateIsPassed() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    LocalDate endDate = LocalDate.now();
    LocalDate startDate = endDate.minusDays(30);
    request.setStartDate(startDate.toString());
    request.setEndDate(endDate.toString());

    AllTasksResponse response = constructor.allTasks(request);

    assertTrue("At least one task exists", response.getTotalCount() >= 1);
  }

  @Test
  public void AllTasksAsJsonShouldReturnAListOfTasksWhenStartAndEndDateIsPassed() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    LocalDate endDate = LocalDate.now();
    LocalDate startDate = endDate.minusDays(30);
    request.setStartDate(startDate.toString());
    request.setEndDate(endDate.toString());

    String response = constructor.allTasksAsJson(request);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("At least one task exists", jsonObj.getInt("total_count") >= 1);
  }

  @Test
  public void AllTasksShouldReturnAListOfTasksWhenStatusIsPassed() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    request.setStatus("DONE");

    AllTasksResponse response = constructor.allTasks(request);

    assertTrue("At least one task exists", response.getTotalCount() >= 1);
  }

  @Test
  public void AllTasksAsJsonShouldReturnAListOfTasksWhenStatusIsPassed() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    request.setStatus("DONE");

    String response = constructor.allTasksAsJson(request);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("At least one task exists", jsonObj.getInt("total_count") >= 1);
  }

  @Test
  public void AllTasksShouldReturnAListOfTasksWhenTypeIsPassed() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    request.setType("ingestion");

    AllTasksResponse response = constructor.allTasks(request);

    assertTrue("At least one task exists", response.getTotalCount() >= 1);
  }

  @Test
  public void AllTasksAsJsonShouldReturnAListOfTasksWhenTypeIsPassed() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    request.setType("ingestion");

    String response = constructor.allTasksAsJson(request);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("At least one task exists", jsonObj.getInt("total_count") >= 1);
  }

  @Test
  public void AllTasksShouldReturnErrorWithInvalidApiKey() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, "notanapikey", true, null);
    AllTasksRequest request = new AllTasksRequest();

    thrown.expect(ConstructorException.class);
    thrown.expectMessage(
        "[HTTP 401] You have supplied an invalid `key` or `autocomplete_key`. You can find your key"
            + " at app.constructor.io/dashboard/accounts/api_integration.");
    AllTasksResponse response = constructor.allTasks(request);
  }

  @Test
  public void AllTasksShouldReturnErrorWithInvalidApiToken() throws Exception {
    ConstructorIO constructor = new ConstructorIO("notanapitoken", apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    thrown.expect(ConstructorException.class);
    thrown.expectMessage(
        "[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one"
            + " at app.constructor.io/dashboard");
    AllTasksResponse response = constructor.allTasks(request);
  }

  @Test
  public void AllTasksAsJSONShouldReturnErrorWithInvalidApiKey() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, "notanapikey", true, null);
    AllTasksRequest request = new AllTasksRequest();

    thrown.expect(ConstructorException.class);
    thrown.expectMessage(
        StringContains.containsString(
            "[HTTP 401] You have supplied an invalid `key` or `autocomplete_key`."));
    String response = constructor.allTasksAsJson(request);
  }

  @Test
  public void AllTasksAsJSONShouldReturnErrorWithInvalidApiToken() throws Exception {
    ConstructorIO constructor = new ConstructorIO("notanapitoken", apiKey, true, null);
    AllTasksRequest request = new AllTasksRequest();

    thrown.expect(ConstructorException.class);
    thrown.expectMessage(
        "[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one"
            + " at app.constructor.io/dashboard");
    String response = constructor.allTasksAsJson(request);
  }
}
