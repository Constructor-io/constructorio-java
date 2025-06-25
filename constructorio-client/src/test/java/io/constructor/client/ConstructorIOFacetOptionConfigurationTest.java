package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.FacetConfiguration;
import io.constructor.client.models.FacetOptionConfiguration;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOFacetOptionConfigurationTest {
    private static final String PRODUCTS_SECTION = "Products";
    private static final String SEARCH_SUGGESTIONS_SECTION = "Search Suggestions";
    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private static ArrayList<FacetOptionToCleanup> facetOptionsToCleanup = new ArrayList<>();
    private static ArrayList<String> facetsToCleanup = new ArrayList<>();
    private ConstructorIO constructor;
    private FacetConfiguration baseFacetConfig;

    private static class FacetOptionToCleanup {
        String facetName;
        String optionValue;
        String section;

        FacetOptionToCleanup(String facetName, String optionValue, String section) {
            this.facetName = facetName;
            this.optionValue = optionValue;
            this.section = section;
        }
    }

    @Before
    public void setUp() throws Exception {
        constructor = new ConstructorIO(token, apiKey, true, null);
        baseFacetConfig =
                new Gson()
                        .fromJson(
                                Utils.getTestResource("facet.configuration.json"),
                                FacetConfiguration.class);
    }

    private void addFacetOptionToCleanupArray(
            String facetName, String optionValue, String section) {
        facetOptionsToCleanup.add(new FacetOptionToCleanup(facetName, optionValue, section));
    }

    private void addFacetOptionToCleanupArray(String facetName, String optionValue) {
        addFacetOptionToCleanupArray(facetName, optionValue, PRODUCTS_SECTION);
    }

    private void addFacetToCleanupArray(String facetName, String section) {
        facetsToCleanup.add(facetName + "|" + (section != null ? section : PRODUCTS_SECTION));
    }

    private void addFacetToCleanupArray(String facetName) {
        addFacetToCleanupArray(facetName, PRODUCTS_SECTION);
    }

    private FacetConfiguration createFacetConfigurationObject(String name, String section) {
        FacetConfiguration config = new FacetConfiguration();
        config.setName(name);
        config.setDisplayName(baseFacetConfig.getDisplayName());
        config.setType(baseFacetConfig.getType());
        return config;
    }

    private FacetOptionConfiguration createFacetOptionConfigurationObject(
            String value, String displayName, Integer position) {
        FacetOptionConfiguration config = new FacetOptionConfiguration();
        config.setValue(value);
        config.setDisplayName(displayName);
        config.setPosition(position);
        return config;
    }

    @AfterClass
    public static void cleanupFacetsWithOptions() throws ConstructorException {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        for (FacetOptionToCleanup facetOption : facetOptionsToCleanup) {
            try {
                constructor.deleteFacetOptionConfiguration(
                        facetOption.facetName, facetOption.optionValue, facetOption.section);
            } catch (ConstructorException e) {
                System.err.println(
                        "Warning: Failed to clean up facet option: " + facetOption.facetName);
            }
        }

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
    public void testCreateFacetOptionConfiguration() throws Exception {
        String facetName = "testFacet";
        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(
                        createFacetConfigurationObject(facetName, PRODUCTS_SECTION),
                        PRODUCTS_SECTION));
        addFacetToCleanupArray(facetName);

        // Create facet option configuration
        FacetOptionConfiguration option =
                createFacetOptionConfigurationObject("test-option", "Test Option", 1);
        option.setValueAlias("test-alias");
        Map<String, Object> data = new HashMap<>();
        data.put("foo", "bar");
        option.setData(data);
        option.setHidden(false);

        // Create and verify configuration
        String response =
                constructor.createFacetOptionConfiguration(
                        new FacetOptionConfigurationRequest(option, "testFacet", PRODUCTS_SECTION));
        JSONObject jsonObj = new JSONObject(response);

        assertEquals("test-option", jsonObj.get("value"));
        assertEquals("test-alias", jsonObj.get("value_alias"));
        assertEquals("Test Option", jsonObj.get("display_name"));
        assertEquals(Integer.valueOf(1), jsonObj.get("position"));
        assertEquals(false, jsonObj.get("hidden"));
        assertEquals("bar", jsonObj.getJSONObject("data").get("foo"));

        addFacetOptionToCleanupArray(facetName, "test-option");
    }

    @Test
    public void testCreateFacetOptionConfigurations() throws Exception {
        String facetName = "testFacet2";
        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(
                        createFacetConfigurationObject(facetName, PRODUCTS_SECTION),
                        PRODUCTS_SECTION));
        addFacetToCleanupArray(facetName);

        // Create facet option configurations
        FacetOptionConfiguration option =
                createFacetOptionConfigurationObject("test-option", "Test Option", 1);
        FacetOptionConfiguration option2 =
                createFacetOptionConfigurationObject("test-option-2", "Test Option 2", 2);
        option.setValueAlias("test-alias");
        option2.setHidden(true);
        Map<String, Object> data = new HashMap<>();
        data.put("foo", "bar");
        option.setData(data);
        option.setHidden(false);
        List<FacetOptionConfiguration> configurations = Arrays.asList(option, option2);

        // Create and verify configurations
        String response =
                constructor.createOrUpdateFacetOptionConfigurations(
                        new FacetOptionConfigurationsRequest(
                                configurations, facetName, PRODUCTS_SECTION));
        JSONArray jsonArr = new JSONArray(response);
        JSONObject jsonOption1 = (JSONObject) jsonArr.get(0);
        JSONObject jsonOption2 = (JSONObject) jsonArr.get(1);

        assertEquals("test-option", jsonOption1.get("value"));
        assertEquals("test-alias", jsonOption1.get("value_alias"));
        assertEquals("Test Option", jsonOption1.get("display_name"));
        assertEquals(Integer.valueOf(1), jsonOption1.get("position"));
        assertEquals(false, jsonOption1.get("hidden"));
        assertEquals("bar", jsonOption1.getJSONObject("data").get("foo"));
        assertEquals("test-option-2", jsonOption2.get("value"));
        assertEquals("Test Option 2", jsonOption2.get("display_name"));
        assertEquals(Integer.valueOf(2), jsonOption2.get("position"));
        assertEquals(true, jsonOption2.get("hidden"));

        addFacetOptionToCleanupArray(facetName, "test-option");
        addFacetOptionToCleanupArray(facetName, "test-option-2");
    }

    @Test
    public void testCreateFacetOptionConfigurationsWithNullValues() throws Exception {
        String facetName = "testFacetNullValues";
        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(
                        createFacetConfigurationObject(facetName, PRODUCTS_SECTION),
                        PRODUCTS_SECTION));
        addFacetToCleanupArray(facetName);

        // Create facet option configuration with null values
        FacetOptionConfiguration option = new FacetOptionConfiguration();
        option.setValue("test-option-null");
        option.setDisplayName(null);
        option.setPosition(null);
        option.setValueAlias(null);
        option.setHidden(null);
        option.setData(null);

        List<FacetOptionConfiguration> configurations = Arrays.asList(option);

        // Create and verify configuration
        String response =
                constructor.createOrUpdateFacetOptionConfigurations(
                        new FacetOptionConfigurationsRequest(
                                configurations, facetName, PRODUCTS_SECTION));
        JSONArray jsonArr = new JSONArray(response);
        JSONObject jsonOption = (JSONObject) jsonArr.get(0);

        assertEquals("test-option-null", jsonOption.get("value"));
        assertFalse("display_name should not be present", jsonOption.has("display_name"));
        assertFalse("position should not be present", jsonOption.has("position"));
        assertFalse("value_alias should not be present", jsonOption.has("value_alias"));
        assertFalse("hidden should not be present", jsonOption.has("hidden"));
        assertFalse("data should not be present", jsonOption.has("data"));

        addFacetOptionToCleanupArray(facetName, "test-option-null");
    }

    @Test(expected = ConstructorException.class)
    public void testCreateFacetOptionConfigurationWithNullRequest() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.createFacetOptionConfiguration(null);
    }

    @Test
    public void testCreateFacetOptionConfigurationWithDifferentSection() throws Exception {
        String facetName = "testFacet_SearchSuggestions";
        String optionValue = "test-option-different-section";

        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(
                        createFacetConfigurationObject(facetName, SEARCH_SUGGESTIONS_SECTION),
                        SEARCH_SUGGESTIONS_SECTION));
        addFacetToCleanupArray(facetName, SEARCH_SUGGESTIONS_SECTION);

        FacetOptionConfiguration option =
                createFacetOptionConfigurationObject(
                        optionValue, "Test Option Different Section", 1);
        String response =
                constructor.createFacetOptionConfiguration(
                        new FacetOptionConfigurationRequest(
                                option, facetName, SEARCH_SUGGESTIONS_SECTION));

        assertEquals(optionValue, new JSONObject(response).getString("value"));
        addFacetOptionToCleanupArray(facetName, optionValue, SEARCH_SUGGESTIONS_SECTION);
    }

    @Test
    public void testDeleteFacetOptionConfigurationWithName() throws Exception {
        String facetName = "testDeleteFacet";
        String optionValue = "test-option-to-delete";

        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(
                        createFacetConfigurationObject(facetName, PRODUCTS_SECTION),
                        PRODUCTS_SECTION));
        addFacetToCleanupArray(facetName);

        FacetOptionConfiguration option =
                createFacetOptionConfigurationObject(optionValue, "Test Option To Delete", 1);
        constructor.createFacetOptionConfiguration(
                new FacetOptionConfigurationRequest(option, facetName, PRODUCTS_SECTION));

        String response =
                constructor.deleteFacetOptionConfiguration(
                        facetName, optionValue, PRODUCTS_SECTION);
        assertEquals(optionValue, new JSONObject(response).getString("value"));
        addFacetOptionToCleanupArray(facetName, optionValue);
    }

    @Test
    public void testDeleteFacetOptionConfigurationWithRequest() throws Exception {
        String facetName = "testDeleteWithRequestFacet";
        String optionValue = "test-delete-with-request";

        constructor.createFacetConfiguration(
                new FacetConfigurationRequest(
                        createFacetConfigurationObject(facetName, PRODUCTS_SECTION),
                        PRODUCTS_SECTION));
        addFacetToCleanupArray(facetName);

        FacetOptionConfigurationRequest request =
                new FacetOptionConfigurationRequest(
                        createFacetOptionConfigurationObject(
                                optionValue, "Test Delete With Request", 1),
                        facetName,
                        PRODUCTS_SECTION);
        constructor.createFacetOptionConfiguration(request);

        String response = constructor.deleteFacetOptionConfiguration(request);
        assertEquals(optionValue, new JSONObject(response).getString("value"));
        addFacetOptionToCleanupArray(facetName, optionValue);
    }

    @Test(expected = ConstructorException.class)
    public void testDeleteNonExistentFacetOptionShouldThrowException() throws Exception {
        constructor.deleteFacetOptionConfiguration(
                "nonExistentFacet", "nonExistentOption", PRODUCTS_SECTION);
    }

    @Test
    public void testFacetOptionConfigurationDefaultValues() {
        FacetOptionConfiguration config = new FacetOptionConfiguration();
        assertNull("Position should default to null", config.getPosition());
    }
}
