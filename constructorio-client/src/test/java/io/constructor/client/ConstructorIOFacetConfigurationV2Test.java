package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.FacetConfigurationV2;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Test;

public class ConstructorIOFacetConfigurationV2Test {

    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private static ArrayList<String> facetsToCleanup = new ArrayList<>();

    private void addFacetToCleanupArray(String facetName, String section) {
        if (section == null) {
            section = ConstructorIO.DEFAULT_SECTION;
        }
        facetsToCleanup.add(facetName + "|" + section);
    }

    private void addFacetToCleanupArray(String facetName) {
        addFacetToCleanupArray(facetName, ConstructorIO.DEFAULT_SECTION);
    }

    @AfterClass
    public static void cleanupFacets() throws ConstructorException {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        for (String facet : facetsToCleanup) {
            String[] parts = facet.split("\\|");
            String facetName = parts[0];
            String section = parts[1];

            try {
                constructor.deleteFacetConfigurationV2(facetName, section);
            } catch (ConstructorException e) {
                System.err.println("Warning: Failed to clean up facet: " + facetName);
            }
        }
    }

    @Test
    public void testCreateFacetConfigurationV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfiguration =
                new Gson().fromJson(string, FacetConfigurationV2.class);

        facetConfiguration.setName("testFacetV2");
        facetConfiguration.setPathInMetadata("testFacetV2");
        FacetConfigurationV2Request request =
                new FacetConfigurationV2Request(facetConfiguration, ConstructorIO.DEFAULT_SECTION);

        String response = constructor.createFacetConfigurationV2(request);
        JSONObject jsonObj = new JSONObject(response);
        JSONObject loadedJsonObj = new JSONObject(string);

        assertEquals("testFacetV2", jsonObj.get("name"));
        assertEquals("testFacetV2", jsonObj.get("path_in_metadata"));
        assertEquals(loadedJsonObj.get("type"), jsonObj.get("type"));
        assertEquals(loadedJsonObj.get("display_name"), jsonObj.get("display_name"));
        assertEquals(loadedJsonObj.get("sort_order"), jsonObj.get("sort_order"));
        assertEquals(loadedJsonObj.get("sort_descending"), jsonObj.get("sort_descending"));
        assertEquals(loadedJsonObj.get("match_type"), jsonObj.get("match_type"));
        assertEquals(loadedJsonObj.get("position"), jsonObj.get("position"));
        assertEquals(loadedJsonObj.get("hidden"), jsonObj.get("hidden"));
        assertEquals(loadedJsonObj.get("protected"), jsonObj.get("protected"));
        assertEquals(loadedJsonObj.get("countable"), jsonObj.get("countable"));
        assertEquals(loadedJsonObj.get("options_limit"), jsonObj.get("options_limit"));
        addFacetToCleanupArray("testFacetV2");
    }

    @Test
    public void testCreateFacetConfigurationV2WithNullRequestThrowsException() {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        assertThrows(IllegalArgumentException.class, () -> {
            constructor.createFacetConfigurationV2(null);
        });
    }

    @Test
    public void testCreateFacetConfigurationV2WithNullConfigurationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FacetConfigurationV2Request(null, ConstructorIO.DEFAULT_SECTION);
        });
    }

    @Test
    public void testCreateFacetConfigurationV2WithDifferentSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 config = new Gson().fromJson(string, FacetConfigurationV2.class);
        config.setName("testFacetV2Section");
        config.setPathInMetadata("testFacetV2Section");

        FacetConfigurationV2Request request =
                new FacetConfigurationV2Request(config, "Search Suggestions");

        String response = constructor.createFacetConfigurationV2(request);
        JSONObject jsonObj = new JSONObject(response);

        assertEquals("testFacetV2Section", jsonObj.getString("name"));
        assertEquals("testFacetV2Section", jsonObj.getString("path_in_metadata"));
        addFacetToCleanupArray("testFacetV2Section", "Search Suggestions");
    }

    @Test
    public void testRetrieveFacetConfigurationV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfig = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig.setName("testRetrieveFacetV2");
        facetConfig.setPathInMetadata("testRetrieveFacetV2");

        constructor.createFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig, ConstructorIO.DEFAULT_SECTION));

        // Retrieve the facet
        String retrieveResponse =
                constructor.retrieveFacetConfigurationV2("testRetrieveFacetV2", ConstructorIO.DEFAULT_SECTION);
        JSONObject jsonObj = new JSONObject(retrieveResponse);

        assertEquals("testRetrieveFacetV2", jsonObj.get("name"));
        assertEquals("testRetrieveFacetV2", jsonObj.get("path_in_metadata"));
        addFacetToCleanupArray("testRetrieveFacetV2");
    }

    @Test
    public void testRetrieveFacetConfigurationsV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Retrieve all facets
        String retrieveResponse =
                constructor.retrieveFacetConfigurationsV2(ConstructorIO.DEFAULT_SECTION, null, null);
        JSONObject jsonObj = new JSONObject(retrieveResponse);

        assertTrue("Response should have facets array", jsonObj.has("facets"));
    }

    @Test
    public void testUpdateFacetConfigurationV2() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfig = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig.setName("testUpdateFacetV2");
        facetConfig.setPathInMetadata("testUpdateFacetV2");

        constructor.createFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig, ConstructorIO.DEFAULT_SECTION));

        // Update the facet
        facetConfig.setDisplayName("Updated Brand Name");
        String updateResponse = constructor.updateFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig, ConstructorIO.DEFAULT_SECTION));
        JSONObject jsonObj = new JSONObject(updateResponse);

        assertEquals("Updated Brand Name", jsonObj.get("display_name"));
        addFacetToCleanupArray("testUpdateFacetV2");
    }

    @Test
    public void testDeleteFacetConfigurationV2WithFacetNameAndSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfig = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig.setName("testDeleteFacetV2");
        facetConfig.setPathInMetadata("testDeleteFacetV2");

        constructor.createFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig, ConstructorIO.DEFAULT_SECTION));

        // Delete the facet
        String deleteResponse =
                constructor.deleteFacetConfigurationV2("testDeleteFacetV2", ConstructorIO.DEFAULT_SECTION);
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertEquals("testDeleteFacetV2", jsonObj.get("name"));
    }

    @Test
    public void testDeleteFacetConfigurationV2WithDefaultSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfig = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig.setName("testDefaultSectionFacetV2");
        facetConfig.setPathInMetadata("testDefaultSectionFacetV2");

        constructor.createFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig, ConstructorIO.DEFAULT_SECTION));

        // Delete the facet
        String deleteResponse = constructor.deleteFacetConfigurationV2("testDefaultSectionFacetV2");
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertEquals("testDefaultSectionFacetV2", jsonObj.get("name"));
    }

    @Test
    public void testDeleteFacetConfigurationV2WithFacetConfiguration() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfig = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig.setName("testDeleteWithFacetConfigurationV2");
        facetConfig.setPathInMetadata("testDeleteWithFacetConfigurationV2");

        FacetConfigurationV2Request request =
                new FacetConfigurationV2Request(facetConfig, ConstructorIO.DEFAULT_SECTION);
        constructor.createFacetConfigurationV2(request);

        // Delete the facet
        String deleteResponse = constructor.deleteFacetConfigurationV2(request);
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertEquals("testDeleteWithFacetConfigurationV2", jsonObj.get("name"));
    }

    @Test
    public void testDeleteNonExistentFacetV2ThrowsException() {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        assertThrows(ConstructorException.class, () -> {
            constructor.deleteFacetConfigurationV2("nonExistentFacetV2", ConstructorIO.DEFAULT_SECTION);
        });
    }

    @Test
    public void testUpdateFacetConfigurationsV2Bulk() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create two facets first
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfig1 = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig1.setName("testBulkFacetV2_1");
        facetConfig1.setPathInMetadata("testBulkFacetV2_1");

        FacetConfigurationV2 facetConfig2 = new Gson().fromJson(string, FacetConfigurationV2.class);
        facetConfig2.setName("testBulkFacetV2_2");
        facetConfig2.setPathInMetadata("testBulkFacetV2_2");

        constructor.createFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig1, ConstructorIO.DEFAULT_SECTION));
        constructor.createFacetConfigurationV2(
                new FacetConfigurationV2Request(facetConfig2, ConstructorIO.DEFAULT_SECTION));

        // Update both facets
        facetConfig1.setDisplayName("Bulk Updated 1");
        facetConfig2.setDisplayName("Bulk Updated 2");

        FacetConfigurationsV2Request bulkRequest = new FacetConfigurationsV2Request(
                Arrays.asList(facetConfig1, facetConfig2), ConstructorIO.DEFAULT_SECTION);

        String updateResponse = constructor.updateFacetConfigurationsV2(bulkRequest);
        JSONObject jsonObj = new JSONObject(updateResponse);

        assertTrue("Response should have facets array", jsonObj.has("facets"));
        addFacetToCleanupArray("testBulkFacetV2_1");
        addFacetToCleanupArray("testBulkFacetV2_2");
    }

    @Test
    public void testFacetConfigurationV2DefaultValues() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        assertNull("Position should default to null", config.getPosition());
        assertNull("Options limit should default to null", config.getOptionsLimit());
        assertNull("Path in metadata should default to null", config.getPathInMetadata());
    }
}
