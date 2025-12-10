package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOCatalogTest {

    private String token = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private File csvFolder = new File("src/test/resources/csv");
    private File itemsFile = new File("src/test/resources/csv/items.csv");
    private File variationsFile = new File("src/test/resources/csv/variations.csv");
    private File itemGroupsFile = new File("src/test/resources/csv/item_groups.csv");
    private String baseUrl =
            "https://raw.githubusercontent.com/Constructor-io/integration-examples/main/catalog/";

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws Exception {
        URL itemsUrl = new URL(baseUrl + "items.csv");
        FileUtils.copyURLToFile(itemsUrl, itemsFile);

        URL variationsUrl = new URL(baseUrl + "variations.csv");
        FileUtils.copyURLToFile(variationsUrl, variationsFile);

        URL itemGroupsUrl = new URL(baseUrl + "item_groups.csv");
        FileUtils.copyURLToFile(itemGroupsUrl, itemGroupsFile);
    }

    @After
    public void teardown() throws Exception {
        itemsFile.delete();
        variationsFile.delete();
        itemGroupsFile.delete();
        csvFolder.delete();
    }

    @Test
    public void ReplaceCatalogWithNoFilesShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();
        CatalogRequest req = new CatalogRequest(files, "Products");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "At least one file of \"items\", \"variations\", \"item_groups\" is required.");
        constructor.replaceCatalog(req);
    }

    @Test
    public void ReplaceCatalogWithItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        req.setNotificationEmail("test@constructor.io");

        String response = constructor.replaceCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void ReplaceCatalogWithItemsAndSectionShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
    public void ReplaceCatalogWithItemsAndVariationsAndItemGroupsFilesShouldReturnTaskInfo()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();
        CatalogRequest req = new CatalogRequest(files, "Products");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "At least one file of \"items\", \"variations\", \"item_groups\" is required.");
        constructor.updateCatalog(req);
    }

    @Test
    public void UpdateCatalogWithItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        req.setNotificationEmail("test@constructor.io");

        String response = constructor.updateCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void UpdateCatalogWithItemsAndSectionShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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
    public void UpdateCatalogWithItemsAndVariationsAndItemGroupsFilesShouldReturnTaskInfo()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
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

    @Test
    public void PatchCatalogWithNoFilesShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();
        CatalogRequest req = new CatalogRequest(files, "Products");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "At least one file of \"items\", \"variations\", \"item_groups\" is required.");
        constructor.patchCatalog(req);
    }

    @Test
    public void PatchCatalogWithItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithItemsAndNotificationEmailShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        req.setNotificationEmail("test@constructor.io");

        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithItemsAndSectionShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        req.setSection("Content");

        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithItemsAndForceShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        req.setForce(true);

        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithItemsAndOnMissingShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        req.setOnMissing(CatalogRequest.OnMissing.CREATE);

        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithItemsAndVariationsFilesShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));
        files.put("variations", new File("src/test/resources/csv/variations.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithItemsAndVariationsAndItemGroupsFilesShouldReturnTaskInfo()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));
        files.put("variations", new File("src/test/resources/csv/variations.csv"));
        files.put("item_groups", new File("src/test/resources/csv/item_groups.csv"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    // JSONL Format Tests

    @Test
    public void ReplaceCatalogWithJsonlItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/jsonl/items.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.replaceCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void UpdateCatalogWithJsonlItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/jsonl/items.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.updateCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithJsonlItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/jsonl/items.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    // Invalid Extension Tests

    @Test
    public void ReplaceCatalogWithInvalidExtensionShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/invalid/items.txt"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("Invalid file type for 'items'");
        thrown.expectMessage("must have .csv or .jsonl extension");
        constructor.replaceCatalog(req);
    }

    @Test
    public void UpdateCatalogWithNoExtensionShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/invalid/items"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("Invalid file for 'items'");
        thrown.expectMessage("must have .csv or .jsonl extension");
        constructor.updateCatalog(req);
    }

    @Test
    public void PatchCatalogWithInvalidExtensionShouldError() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/invalid/items.txt"));

        CatalogRequest req = new CatalogRequest(files, "Products");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("Invalid file type for 'items'");
        thrown.expectMessage("must have .csv or .jsonl extension");
        constructor.patchCatalog(req);
    }

    // Edge Case Tests

    @Test
    public void ReplaceCatalogWithMixedFileTypesShouldSucceed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));
        files.put("variations", new File("src/test/resources/jsonl/variations.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.replaceCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void UpdateCatalogWithJsonlVariationsAndItemGroupsShouldSucceed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("variations", new File("src/test/resources/jsonl/variations.jsonl"));
        files.put("item_groups", new File("src/test/resources/jsonl/item_groups.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.updateCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithAllJsonlFilesShouldSucceed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/jsonl/items.jsonl"));
        files.put("variations", new File("src/test/resources/jsonl/variations.jsonl"));
        files.put("item_groups", new File("src/test/resources/jsonl/item_groups.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    // JSON Format Tests

    @Test
    public void ReplaceCatalogWithJsonItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/json/items.json"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.replaceCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void UpdateCatalogWithJsonItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/json/items.json"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.updateCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void PatchCatalogWithJsonItemsFileShouldReturnTaskInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/json/items.json"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.patchCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }

    @Test
    public void ReplaceCatalogWithMixedCsvJsonJsonlShouldSucceed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        Map<String, File> files = new HashMap<String, File>();

        files.put("items", new File("src/test/resources/csv/items.csv"));
        files.put("variations", new File("src/test/resources/json/variations.json"));
        files.put("item_groups", new File("src/test/resources/jsonl/item_groups.jsonl"));

        CatalogRequest req = new CatalogRequest(files, "Products");
        String response = constructor.replaceCatalog(req);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("task_id exists", jsonObj.has("task_id") == true);
        assertTrue("task_status_path exists", jsonObj.has("task_status_path") == true);
    }
}
