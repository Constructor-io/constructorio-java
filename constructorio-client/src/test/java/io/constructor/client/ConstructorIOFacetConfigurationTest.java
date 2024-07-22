package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.FacetConfiguration;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;

public class ConstructorIOFacetConfigurationTest {

    private String token = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_CATALOG_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws Exception {
        // TODO: Add facet configuration cleanup after deleteFacetConfiguration function
        // has been implemented
    }

    @After
    public void teardown() throws Exception {
        // TODO: Add facet configuration cleanup after deleteFacetConfiguration function
        // has been implemented
    }

    @Test
    public void CreateFacetConfigurationShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration facetConfiguration = new Gson().fromJson(string, FacetConfiguration.class);

        facetConfiguration.setName("testFacet");
        FacetConfigurationRequest request = new FacetConfigurationRequest(facetConfiguration, "Products");

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
        FacetConfigurationRequest request = new FacetConfigurationRequest(config,"");

        constructor.createFacetConfiguration(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFacetConfigurationWithNullConfiguration() throws
    Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

    FacetConfigurationRequest request = new FacetConfigurationRequest(null,
    "Products");

    constructor.createFacetConfiguration(request);
    }

    @Test
    public void testCreateFacetConfigurationWithDifferentSection() throws
    Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration config = new Gson().fromJson(string, FacetConfiguration.class);

    FacetConfigurationRequest request = new FacetConfigurationRequest(config,
    "Search Suggestions");

    String response = constructor.createFacetConfiguration(request);
    JSONObject jsonObj = new JSONObject(response);

    assertEquals("brand", jsonObj.getString("name"));
    }
}
