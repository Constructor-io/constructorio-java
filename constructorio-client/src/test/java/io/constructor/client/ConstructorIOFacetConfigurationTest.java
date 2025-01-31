package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.FacetConfiguration;
import java.util.ArrayList;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOFacetConfigurationTest {

    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private static ArrayList<String> facetsToCleanup = new ArrayList<>();

    private void addFacetToCleanupArray(String facetName, String section) {
        if (section == null) {
            section = "Products";
        }
        facetsToCleanup.add(facetName + "|" + section);
    }

    private void addFacetToCleanupArray(String facetName) {
        addFacetToCleanupArray(facetName, "Products");
    }

    @AfterClass
    public static void cleanupFacets() throws ConstructorException {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        for (String facet : facetsToCleanup) {
            String[] parts = facet.split("\\|");
            String facetName = parts[0];
            String section = parts[1];

            try {
                constructor.deleteFacetConfiguration(facetName, section);
            } catch (ConstructorException e) {
                System.err.println("Warning: Failed to clean up facet: " + facetName);
            }
        }
    }

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void CreateFacetConfigurationShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration facetConfiguration =
                new Gson().fromJson(string, FacetConfiguration.class);

        facetConfiguration.setName("testFacet");
        FacetConfigurationRequest request =
                new FacetConfigurationRequest(facetConfiguration, "Products");

        String response = constructor.createFacetConfiguration(request);
        JSONObject jsonObj = new JSONObject(response);
        JSONObject loadedJsonObj = new JSONObject(string);

        assertEquals(jsonObj.get("name"), "testFacet");
        assertEquals(jsonObj.get("type"), loadedJsonObj.get("type"));
        assertEquals(jsonObj.get("display_name"), loadedJsonObj.get("display_name"));
        assertEquals(jsonObj.get("sort_order"), loadedJsonObj.get("sort_order"));
        assertEquals(jsonObj.get("sort_descending"), loadedJsonObj.get("sort_descending"));
        assertEquals(jsonObj.get("match_type"), loadedJsonObj.get("match_type"));
        assertEquals(jsonObj.get("position"), loadedJsonObj.get("position"));
        assertEquals(jsonObj.get("hidden"), loadedJsonObj.get("hidden"));
        assertEquals(jsonObj.get("protected"), loadedJsonObj.get("protected"));
        assertEquals(jsonObj.get("countable"), loadedJsonObj.get("countable"));
        assertEquals(jsonObj.get("options_limit"), loadedJsonObj.get("options_limit"));
        addFacetToCleanupArray("testFacet");
    }

    @Test(expected = ConstructorException.class)
    public void testCreateFacetConfigurationWithNullRequest() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        constructor.createFacetConfiguration(null);
    }

    @Test(expected = ConstructorException.class)
    public void testCreateFacetConfigurationWithEmptySection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        FacetConfiguration config = new FacetConfiguration();
        config.setName("emptySection");
        FacetConfigurationRequest request = new FacetConfigurationRequest(config, "");

        constructor.createFacetConfiguration(request);
        addFacetToCleanupArray("emptySection");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFacetConfigurationWithNullConfiguration() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        FacetConfigurationRequest request = new FacetConfigurationRequest(null, "Products");

        constructor.createFacetConfiguration(request);
    }

    @Test
    public void testCreateFacetConfigurationWithDifferentSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration config = new Gson().fromJson(string, FacetConfiguration.class);

        FacetConfigurationRequest request =
                new FacetConfigurationRequest(config, "Search Suggestions");

        String response = constructor.createFacetConfiguration(request);
        JSONObject jsonObj = new JSONObject(response);

        assertEquals("brand", jsonObj.getString("name"));
        addFacetToCleanupArray("brand", "Search Suggestions");
    }

    @Test
    public void testDeleteFacetConfigurationWithFacetNameAndSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration facetConfig = new Gson().fromJson(string, FacetConfiguration.class);
        facetConfig.setName("testDeleteFacet");

        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(facetConfig, "Products"));

        // Delete the facet
        String deleteResponse = constructor.deleteFacetConfiguration("testDeleteFacet", "Products");
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertTrue("Deleted facet name matches", jsonObj.get("name").equals("testDeleteFacet"));
    }

    @Test
    public void testDeleteFacetConfigurationWithDefaultSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration facetConfig = new Gson().fromJson(string, FacetConfiguration.class);
        facetConfig.setName("testDefaultSectionFacet");

        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(facetConfig, "Products"));

        // Delete the facet
        String deleteResponse = constructor.deleteFacetConfiguration("testDefaultSectionFacet");
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertTrue(
                "Deleted facet name matches",
                jsonObj.get("name").equals("testDefaultSectionFacet"));
    }

    @Test
    public void testDeleteFacetConfigurationWithFacetConfiguration() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a facet first
        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration facetConfig = new Gson().fromJson(string, FacetConfiguration.class);
        facetConfig.setName("testDeleteWithFacetConfiguration");

        FacetConfigurationRequest request = new FacetConfigurationRequest(facetConfig, "Products");
        constructor.createFacetConfiguration(request);

        // Delete the facet
        String deleteResponse = constructor.deleteFacetConfiguration(request);
        JSONObject jsonObj = new JSONObject(deleteResponse);

        assertTrue(
                "Deleted facet name matches",
                jsonObj.get("name").equals("testDeleteWithFacetConfiguration"));
    }

    @Test(expected = ConstructorException.class)
    public void testDeleteNonExistentFacetShouldThrowException() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.deleteFacetConfiguration("nonExistentFacet", "Products");
    }
}
