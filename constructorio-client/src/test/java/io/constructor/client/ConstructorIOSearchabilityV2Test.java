package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.SearchabilityV2;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;

public class ConstructorIOSearchabilityV2Test {

    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private static ArrayList<String> searchabilitiesToCleanup = new ArrayList<>();

    private void addSearchabilityToCleanupArray(String name, String section) {
        if (section == null) {
            section = ConstructorIO.DEFAULT_SECTION;
        }
        searchabilitiesToCleanup.add(name + "|" + section);
    }

    private void addSearchabilityToCleanupArray(String name) {
        addSearchabilityToCleanupArray(name, ConstructorIO.DEFAULT_SECTION);
    }

    @AfterClass
    public static void cleanupSearchabilities() throws ConstructorException {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        for (String searchability : searchabilitiesToCleanup) {
            String[] parts = searchability.split("\\|");
            String name = parts[0];
            String section = parts[1];

            try {
                constructor.deleteSearchabilityV2(name, section);
            } catch (ConstructorException e) {
                System.err.println("Warning: Failed to clean up searchability: " + name);
            }
        }
    }

    @Test
    public void testRetrieveSearchabilitiesV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Retrieve all searchabilities
        String retrieveResponse = constructor.retrieveSearchabilitiesV2();
        JSONObject jsonObj = new JSONObject(retrieveResponse);

        assertTrue("Response should have searchabilities array", jsonObj.has("searchabilities"));
        assertTrue("Response should have total_count", jsonObj.has("total_count"));
    }

    @Test
    public void testRetrieveSearchabilitiesV2WithFilters() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SearchabilitiesV2GetRequest request =
                new SearchabilitiesV2GetRequest(ConstructorIO.DEFAULT_SECTION);
        request.setPage(1);
        request.setNumResultsPerPage(10);

        String retrieveResponse = constructor.retrieveSearchabilitiesV2(request);
        JSONObject jsonObj = new JSONObject(retrieveResponse);

        assertTrue("Response should have searchabilities array", jsonObj.has("searchabilities"));
    }

    @Test
    public void testCreateOrUpdateSearchabilityV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testSearchabilityV2");

        SearchabilityV2Request request =
                new SearchabilityV2Request(
                        searchability, "testSearchabilityV2", ConstructorIO.DEFAULT_SECTION);

        String response = constructor.createOrUpdateSearchabilityV2(request);
        JSONObject jsonObj = new JSONObject(response);

        assertEquals("testSearchabilityV2", jsonObj.get("name"));
        assertEquals(true, jsonObj.get("fuzzy_searchable"));
        assertEquals(false, jsonObj.get("exact_searchable"));
        assertEquals(true, jsonObj.get("displayable"));
        addSearchabilityToCleanupArray("testSearchabilityV2");
    }

    @Test
    public void testCreateOrUpdateSearchabilityV2WithSkipRebuild() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testSearchabilityV2SkipRebuild");

        SearchabilityV2Request request =
                new SearchabilityV2Request(
                        searchability,
                        "testSearchabilityV2SkipRebuild",
                        ConstructorIO.DEFAULT_SECTION);
        request.setSkipRebuild(true);

        String response = constructor.createOrUpdateSearchabilityV2(request);
        JSONObject jsonObj = new JSONObject(response);

        assertEquals("testSearchabilityV2SkipRebuild", jsonObj.get("name"));
        addSearchabilityToCleanupArray("testSearchabilityV2SkipRebuild");
    }

    @Test
    public void testRetrieveSearchabilityV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a searchability first
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testRetrieveSearchabilityV2");

        SearchabilityV2Request createRequest =
                new SearchabilityV2Request(
                        searchability,
                        "testRetrieveSearchabilityV2",
                        ConstructorIO.DEFAULT_SECTION);
        constructor.createOrUpdateSearchabilityV2(createRequest);

        // Retrieve the searchability
        String retrieveResponse =
                constructor.retrieveSearchabilityV2(
                        "testRetrieveSearchabilityV2", ConstructorIO.DEFAULT_SECTION);
        JSONObject jsonObj = new JSONObject(retrieveResponse);

        assertEquals("testRetrieveSearchabilityV2", jsonObj.get("name"));
        addSearchabilityToCleanupArray("testRetrieveSearchabilityV2");
    }

    @Test
    public void testRetrieveSearchabilityV2WithDefaultSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a searchability first
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testRetrieveSearchabilityV2Default");

        SearchabilityV2Request createRequest =
                new SearchabilityV2Request(
                        searchability,
                        "testRetrieveSearchabilityV2Default",
                        ConstructorIO.DEFAULT_SECTION);
        constructor.createOrUpdateSearchabilityV2(createRequest);

        // Retrieve the searchability with default section
        String retrieveResponse =
                constructor.retrieveSearchabilityV2("testRetrieveSearchabilityV2Default");
        JSONObject jsonObj = new JSONObject(retrieveResponse);

        assertEquals("testRetrieveSearchabilityV2Default", jsonObj.get("name"));
        addSearchabilityToCleanupArray("testRetrieveSearchabilityV2Default");
    }

    @Test
    public void testDeleteSearchabilityV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a searchability first
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testDeleteSearchabilityV2");

        SearchabilityV2Request createRequest =
                new SearchabilityV2Request(
                        searchability, "testDeleteSearchabilityV2", ConstructorIO.DEFAULT_SECTION);
        constructor.createOrUpdateSearchabilityV2(createRequest);

        // Delete the searchability
        String deleteResponse =
                constructor.deleteSearchabilityV2(
                        "testDeleteSearchabilityV2", ConstructorIO.DEFAULT_SECTION);
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertEquals("testDeleteSearchabilityV2", jsonObj.get("name"));
    }

    @Test
    public void testDeleteSearchabilityV2WithDefaultSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a searchability first
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testDeleteSearchabilityV2Default");

        SearchabilityV2Request createRequest =
                new SearchabilityV2Request(
                        searchability,
                        "testDeleteSearchabilityV2Default",
                        ConstructorIO.DEFAULT_SECTION);
        constructor.createOrUpdateSearchabilityV2(createRequest);

        // Delete the searchability with default section
        String deleteResponse =
                constructor.deleteSearchabilityV2("testDeleteSearchabilityV2Default");
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertEquals("testDeleteSearchabilityV2Default", jsonObj.get("name"));
    }

    @Test
    public void testDeleteSearchabilityV2WithRequest() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a searchability first
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);
        searchability.setName("testDeleteSearchabilityV2Request");

        SearchabilityV2Request createRequest =
                new SearchabilityV2Request(
                        searchability,
                        "testDeleteSearchabilityV2Request",
                        ConstructorIO.DEFAULT_SECTION);
        constructor.createOrUpdateSearchabilityV2(createRequest);

        // Delete the searchability using request object
        SearchabilityV2Request deleteRequest =
                new SearchabilityV2Request(
                        "testDeleteSearchabilityV2Request", ConstructorIO.DEFAULT_SECTION);
        String deleteResponse = constructor.deleteSearchabilityV2(deleteRequest);
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertEquals("testDeleteSearchabilityV2Request", jsonObj.get("name"));
    }

    @Test
    public void testCreateOrUpdateSearchabilitiesV2Bulk() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability1 = new Gson().fromJson(string, SearchabilityV2.class);
        searchability1.setName("testBulkSearchabilityV2_1");

        SearchabilityV2 searchability2 = new Gson().fromJson(string, SearchabilityV2.class);
        searchability2.setName("testBulkSearchabilityV2_2");
        searchability2.setFuzzySearchable(false);
        searchability2.setExactSearchable(true);

        SearchabilitiesV2Request bulkRequest =
                new SearchabilitiesV2Request(
                        Arrays.asList(searchability1, searchability2),
                        ConstructorIO.DEFAULT_SECTION);

        String response = constructor.createOrUpdateSearchabilitiesV2(bulkRequest);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("Response should have searchabilities array", jsonObj.has("searchabilities"));
        addSearchabilityToCleanupArray("testBulkSearchabilityV2_1");
        addSearchabilityToCleanupArray("testBulkSearchabilityV2_2");
    }

    @Test
    public void testDeleteSearchabilitiesV2Bulk() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create searchabilities first
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability1 = new Gson().fromJson(string, SearchabilityV2.class);
        searchability1.setName("testBulkDeleteSearchabilityV2_1");

        SearchabilityV2 searchability2 = new Gson().fromJson(string, SearchabilityV2.class);
        searchability2.setName("testBulkDeleteSearchabilityV2_2");

        SearchabilitiesV2Request createRequest =
                new SearchabilitiesV2Request(
                        Arrays.asList(searchability1, searchability2),
                        ConstructorIO.DEFAULT_SECTION);
        constructor.createOrUpdateSearchabilitiesV2(createRequest);

        // Delete searchabilities
        SearchabilitiesV2DeleteRequest deleteRequest =
                new SearchabilitiesV2DeleteRequest(
                        Arrays.asList(
                                "testBulkDeleteSearchabilityV2_1",
                                "testBulkDeleteSearchabilityV2_2"),
                        ConstructorIO.DEFAULT_SECTION);

        String response = constructor.deleteSearchabilitiesV2(deleteRequest);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("Response should have searchabilities array", jsonObj.has("searchabilities"));
    }

    @Test(expected = ConstructorException.class)
    public void testDeleteNonExistentSearchabilityV2ThrowsException() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.deleteSearchabilityV2(
                "nonExistentSearchabilityV2", ConstructorIO.DEFAULT_SECTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilityV2RequestWithNullNameThrowsException() {
        new SearchabilityV2Request((String) null, ConstructorIO.DEFAULT_SECTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilityV2RequestWithEmptyNameThrowsException() {
        new SearchabilityV2Request("   ", ConstructorIO.DEFAULT_SECTION);
    }

    @Test
    public void testSearchabilityV2DefaultValues() {
        SearchabilityV2 searchability = new SearchabilityV2();
        assertNull("Name should default to null", searchability.getName());
        assertNull("Fuzzy searchable should default to null", searchability.getFuzzySearchable());
        assertNull("Exact searchable should default to null", searchability.getExactSearchable());
        assertNull("Displayable should default to null", searchability.getDisplayable());
        assertNull("Hidden should default to null", searchability.getHidden());
    }
}
