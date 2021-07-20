package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOCatalogTest {
  
  String token = System.getenv("TEST_API_TOKEN");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void ReplaceCatalogWithNoFilesShouldError() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();
    CatalogRequest req = new CatalogRequest(files, "Products");

    thrown.expect(ConstructorException.class);
    thrown.expectMessage("At least one file of \"items\", \"variations\", \"item_groups\" is required.");
    constructor.replaceCatalog(req);
  }

  @Test
  public void ReplaceCatalogWithItemsFileShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));
    
    CatalogRequest req = new CatalogRequest(files, "Products");
    String response = constructor.replaceCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void ReplaceCatalogWithItemsAndNotificationEmailShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));
    
    CatalogRequest req = new CatalogRequest(files, "Products");

    req.setNotificationEmail("jimmy@constructor.io");

    String response = constructor.replaceCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void ReplaceCatalogWithItemsAndSectionShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");

    req.setSection("Content");

    String response = constructor.replaceCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void ReplaceCatalogWithItemsAndForceShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();
    files.put("items", new File("src/test/resources/csv/items.csv"));
    
    CatalogRequest req = new CatalogRequest(files, "Products");

    req.setForce(true);

    String response = constructor.replaceCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void ReplaceCatalogWithItemsAndVariationsFilesShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));
    files.put("variations", new File("src/test/resources/csv/variations.csv"));
    
    CatalogRequest req = new CatalogRequest(files, "Products");
    String response = constructor.replaceCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void ReplaceCatalogWithItemsAndVariationsAndItemGroupsFilesShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));
    files.put("variations", new File("src/test/resources/csv/variations.csv"));
    files.put("item_groups", new File("src/test/resources/csv/item_groups.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");
    String response = constructor.replaceCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void UpdateCatalogWithNoFilesShouldError() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();
    CatalogRequest req = new CatalogRequest(files, "Products");

    thrown.expect(ConstructorException.class);
    thrown.expectMessage("At least one file of \"items\", \"variations\", \"item_groups\" is required.");
    constructor.updateCatalog(req);
  }

  @Test
  public void UpdateCatalogWithItemsFileShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");
    String response = constructor.updateCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void UpdateCatalogWithItemsAndNotificationEmailShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");

    req.setNotificationEmail("jimmy@constructor.io");

    String response = constructor.updateCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void UpdateCatalogWithItemsAndSectionShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");

    req.setSection("Content");

    String response = constructor.updateCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void UpdateCatalogWithItemsAndForceShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();
    files.put("items", new File("src/test/resources/csv/items.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");

    req.setForce(true);

    String response = constructor.updateCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void UpdateCatalogWithItemsAndVariationsFilesShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));
    files.put("variations", new File("src/test/resources/csv/variations.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");
    String response = constructor.updateCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }

  @Test
  public void UpdateCatalogWithItemsAndVariationsAndItemGroupsFilesShouldReturnTaskInfo() throws Exception {
    ConstructorIO constructor = new ConstructorIO(token, "key_dsE9a33xJ0tt0tCS", true, null);
    Map<String, File> files = new HashMap<String, File>();

    files.put("items", new File("src/test/resources/csv/items.csv"));
    files.put("variations", new File("src/test/resources/csv/variations.csv"));
    files.put("item_groups", new File("src/test/resources/csv/item_groups.csv"));

    CatalogRequest req = new CatalogRequest(files, "Products");
    String response = constructor.updateCatalog(req);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("task_id exists", jsonObj.has("task_id") == true);
    assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
  }
}
