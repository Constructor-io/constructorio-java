package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class ConstructorIONotificationEmailTest {

    private static MockWebServer mockServer;
    private String apiKey = "test-api-key";
    private static final String MOCK_RESPONSE_BODY =
            "{\"task_id\": 1, \"task_status_path\": \"/task/1\"}";

    @Rule public ExpectedException thrown = ExpectedException.none();
    @Rule public TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setup() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterClass
    public static void teardown() throws Exception {
        mockServer.shutdown();
    }

    private ConstructorIO createConstructorIO() {
        return new ConstructorIO("test-token", apiKey, false, "127.0.0.1", mockServer.getPort());
    }

    private int countOccurrences(String str, String sub) {
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    // --- createOrReplaceItems ---

    @Test
    public void createOrReplaceItemsWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};

        constructor.createOrReplaceItems(items, "Products", false, "test@constructor.io");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    @Test
    public void createOrReplaceItemsWithMultipleEmailsShouldIncludeAllEmailsInUrl()
            throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};
        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");

        constructor.createOrReplaceItems(items, "Products", false, emails);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- deleteItems ---

    @Test
    public void deleteItemsWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};

        constructor.deleteItems(items, "Products", false, "test@constructor.io");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    @Test
    public void deleteItemsWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};
        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");

        constructor.deleteItems(items, "Products", false, emails);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- updateItems ---

    @Test
    public void updateItemsWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};

        constructor.updateItems(items, "Products", false, "test@constructor.io");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    @Test
    public void updateItemsWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};
        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");

        constructor.updateItems(items, "Products", false, emails);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- createOrReplaceVariations ---

    @Test
    public void createOrReplaceVariationsWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorVariation[] variations = {Utils.createProductVariation("item-1")};

        constructor.createOrReplaceVariations(variations, "Products", false, "test@constructor.io");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    @Test
    public void createOrReplaceVariationsWithMultipleEmailsShouldIncludeAllEmailsInUrl()
            throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorVariation[] variations = {Utils.createProductVariation("item-1")};
        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");

        constructor.createOrReplaceVariations(variations, "Products", false, emails);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- deleteVariations ---

    @Test
    public void deleteVariationsWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorVariation[] variations = {Utils.createProductVariation("item-1")};

        constructor.deleteVariations(variations, "Products", false, "test@constructor.io");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    @Test
    public void deleteVariationsWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorVariation[] variations = {Utils.createProductVariation("item-1")};
        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");

        constructor.deleteVariations(variations, "Products", false, emails);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- updateVariations ---

    @Test
    public void updateVariationsWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorVariation[] variations = {Utils.createProductVariation("item-1")};

        constructor.updateVariations(variations, "Products", false, "test@constructor.io");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    @Test
    public void updateVariationsWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorVariation[] variations = {Utils.createProductVariation("item-1")};
        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");

        constructor.updateVariations(variations, "Products", false, emails);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- replaceCatalog ---

    @Test
    public void replaceCatalogWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();

        File csvFile = tempFolder.newFile("items.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("id,name\n1,test\n");
        writer.close();

        Map<String, File> files = new HashMap<String, File>();
        files.put("items", csvFile);
        CatalogRequest req = new CatalogRequest(files, "Products");
        req.setNotificationEmail(Arrays.asList("a@constructor.io", "b@constructor.io"));

        constructor.replaceCatalog(req);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    @Test
    public void replaceCatalogWithSingleEmailShouldIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();

        File csvFile = tempFolder.newFile("items.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("id,name\n1,test\n");
        writer.close();

        Map<String, File> files = new HashMap<String, File>();
        files.put("items", csvFile);
        CatalogRequest req = new CatalogRequest(files, "Products");
        req.setNotificationEmail("test@constructor.io");

        constructor.replaceCatalog(req);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain notification_email",
                path.contains("notification_email=test%40constructor.io"));
    }

    // --- updateCatalog ---

    @Test
    public void updateCatalogWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();

        File csvFile = tempFolder.newFile("items.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("id,name\n1,test\n");
        writer.close();

        Map<String, File> files = new HashMap<String, File>();
        files.put("items", csvFile);
        CatalogRequest req = new CatalogRequest(files, "Products");
        req.setNotificationEmail(Arrays.asList("a@constructor.io", "b@constructor.io"));

        constructor.updateCatalog(req);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- patchCatalog ---

    @Test
    public void patchCatalogWithMultipleEmailsShouldIncludeAllEmailsInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();

        File csvFile = tempFolder.newFile("items.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("id,name\n1,test\n");
        writer.close();

        Map<String, File> files = new HashMap<String, File>();
        files.put("items", csvFile);
        CatalogRequest req = new CatalogRequest(files, "Products");
        req.setNotificationEmail(Arrays.asList("a@constructor.io", "b@constructor.io"));

        constructor.patchCatalog(req);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should contain first notification_email",
                path.contains("notification_email=a%40constructor.io"));
        assertTrue(
                "URL should contain second notification_email",
                path.contains("notification_email=b%40constructor.io"));
        assertTrue(
                "URL should contain two notification_email params",
                countOccurrences(path, "notification_email=") == 2);
    }

    // --- Edge cases ---

    @Test
    public void createOrReplaceItemsWithNullEmailsShouldNotIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};

        constructor.createOrReplaceItems(items, "Products", false, (List<String>) null);

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should not contain notification_email", !path.contains("notification_email"));
    }

    @Test
    public void createOrReplaceItemsWithNoEmailShouldNotIncludeEmailInUrl() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(MOCK_RESPONSE_BODY));
        ConstructorIO constructor = createConstructorIO();
        ConstructorItem[] items = {Utils.createProductItem()};

        constructor.createOrReplaceItems(items, "Products");

        RecordedRequest request = mockServer.takeRequest();
        String path = request.getPath();
        assertTrue(
                "URL should not contain notification_email", !path.contains("notification_email"));
    }
}
