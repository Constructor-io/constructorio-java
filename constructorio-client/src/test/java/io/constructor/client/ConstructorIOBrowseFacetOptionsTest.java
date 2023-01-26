package io.constructor.client;

import static org.junit.Assert.assertTrue;

import io.constructor.client.models.BrowseFacetOptionsResponse;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOBrowseFacetOptionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");
    private String apiToken = System.getenv("TEST_API_TOKEN");
    private String facetName = "Color";

    @Test
    public void BrowseFacetOptionsShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest(facetName);
        BrowseFacetOptionsResponse response = constructor.browseFacetOptions(request);

        assertTrue("browse facets results exist", response.getResponse().getFacets().size() > 0);
        assertTrue("browse facets result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseFacetOptionsAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest(facetName);
        String response = constructor.browseFacetOptionsAsJSON(request);

        assertTrue("browse facets results exist", response.length() > 0);
    }

    @Test
    public void BrowseFacetOptionsShouldReturnAResultWithValidFmtOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest(facetName);
        request.getFormatOptions().put("show_hidden_facets", "True");
        request.getFormatOptions().put("show_protected_facets", "True");
        BrowseFacetOptionsResponse response = constructor.browseFacetOptions(request);

        assertTrue("browse facets results exist", response.getResponse().getFacets().size() > 0);
        assertTrue("browse facets result id exists", response.getResultId() != null);
    }


    @Test
    public void BrowseFacetOptionsAsJSONShouldReturnAResultWithValidFmtOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest(facetName);
        request.getFormatOptions().put("show_hidden_facets", "True");
        request.getFormatOptions().put("show_protected_facets", "True");
        String response = constructor.browseFacetOptionsAsJSON(request);
        JSONObject jsonObj = new JSONObject(response);

        assertTrue("browse facets results exist", response.length() > 0);
        assertTrue("show_hidden_facets is set in request", jsonObj.getJSONObject("request").getJSONObject("fmt_options").getBoolean("show_hidden_facets"));
        assertTrue("show_hidden_facets is set in request", jsonObj.getJSONObject("request").getJSONObject("fmt_options").getBoolean("show_protected_facets"));
    }
}
