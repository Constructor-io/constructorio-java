package io.constructor.client;

import static org.junit.Assert.assertTrue;

import io.constructor.client.models.BrowseFacetsResponse;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOBrowseFacetsTest {

  @Rule public ExpectedException thrown = ExpectedException.none();
  private String apiKey = System.getenv("TEST_REQUEST_API_KEY");
  private String apiToken = System.getenv("TEST_API_TOKEN");

  @Test
  public void BrowseFacetsShouldReturnAResult() throws Exception {
    ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    BrowseFacetsResponse response = constructor.browseFacets(request);

    assertTrue("browse facets results exist", response.getResponse().getFacets().size() > 0);
    assertTrue("browse facets result id exists", response.getResultId() != null);
  }

  @Test
  public void BrowseFacetsAsJSONShouldReturnAResult() throws Exception {
    ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    String response = constructor.browseFacetsAsJSON(request);

    assertTrue("browse facets results exist", response.length() > 0);
  }

  @Test
  public void BrowseFacetsShouldReturnAResultWithValidFmtOptions() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    request.getFormatOptions().put("show_hidden_facets", "True");
    request.getFormatOptions().put("show_protected_facets", "True");
    BrowseFacetsResponse response = constructor.browseFacets(request);

    assertTrue("browse facets results exist", response.getResponse().getFacets().size() > 0);
    assertTrue(
        "browse facets total_num_results exist",
        response.getResponse().getTotalNumberOfResults() > 0);
    assertTrue("browse facets result id exists", response.getResultId() != null);
  }

  @Test
  public void BrowseFacetsAsJSONShouldReturnAResultWithValidFmtOptions() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    request.getFormatOptions().put("show_hidden_facets", "True");
    request.getFormatOptions().put("show_protected_facets", "True");
    String response = constructor.browseFacetsAsJSON(request);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("browse facets results exist", response.length() > 0);
    assertTrue(
        "show_hidden_facets is set in request",
        jsonObj
            .getJSONObject("request")
            .getJSONObject("fmt_options")
            .getBoolean("show_hidden_facets"));
    assertTrue(
        "show_hidden_facets is set in request",
        jsonObj
            .getJSONObject("request")
            .getJSONObject("fmt_options")
            .getBoolean("show_protected_facets"));
  }

  @Test
  public void BrowseFacetsShouldReturnAResultWithFivePerPage() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    request.setResultsPerPage(5);
    request.getFormatOptions().put("show_hidden_facets", "True");
    request.getFormatOptions().put("show_protected_facets", "True");
    BrowseFacetsResponse response = constructor.browseFacets(request);

    assertTrue("browse facets results exist", response.getResponse().getFacets().size() > 0);
    assertTrue(
        "browse facets total_num_results exist",
        response.getResponse().getTotalNumberOfResults() > 0);
    assertTrue("browse facets result id exists", response.getResultId() != null);
  }

  @Test
  public void BrowseFacetsAsJSONShouldReturnAResultWithFivePerPage() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    request.setResultsPerPage(5);
    request.getFormatOptions().put("show_hidden_facets", "True");
    request.getFormatOptions().put("show_protected_facets", "True");
    String response = constructor.browseFacetsAsJSON(request);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("browse facets results exist", response.length() > 0);
    assertTrue(
        "num_results_per_page is set in request",
        jsonObj.getJSONObject("request").getInt("num_results_per_page") == 5);
    assertTrue(
        "show_hidden_facets is set in request",
        jsonObj
            .getJSONObject("request")
            .getJSONObject("fmt_options")
            .getBoolean("show_hidden_facets"));
    assertTrue(
        "show_hidden_facets is set in request",
        jsonObj
            .getJSONObject("request")
            .getJSONObject("fmt_options")
            .getBoolean("show_protected_facets"));
  }

  @Test
  public void BrowseFacetsShouldReturnAResultWithThirdPage() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    request.setPage(3);
    request.getFormatOptions().put("show_hidden_facets", "True");
    request.getFormatOptions().put("show_protected_facets", "True");
    BrowseFacetsResponse response = constructor.browseFacets(request);

    assertTrue("browse facets results exist", response.getResponse().getFacets().size() >= 0);
    assertTrue(
        "browse facets total_num_results exist",
        response.getResponse().getTotalNumberOfResults() > 0);
    assertTrue("browse facets result id exists", response.getResultId() != null);
  }

  @Test
  public void BrowseFacetsAsJSONShouldReturnAResultWithThirdPage() throws Exception {
    ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
    BrowseFacetsRequest request = new BrowseFacetsRequest();
    request.setPage(3);
    request.getFormatOptions().put("show_hidden_facets", "True");
    request.getFormatOptions().put("show_protected_facets", "True");
    String response = constructor.browseFacetsAsJSON(request);
    JSONObject jsonObj = new JSONObject(response);

    assertTrue("browse facets results exist", response.length() > 0);
    assertTrue(
        "num_results_per_page is set in request",
        jsonObj.getJSONObject("request").getInt("page") == 3);
    assertTrue(
        "show_hidden_facets is set in request",
        jsonObj
            .getJSONObject("request")
            .getJSONObject("fmt_options")
            .getBoolean("show_hidden_facets"));
    assertTrue(
        "show_hidden_facets is set in request",
        jsonObj
            .getJSONObject("request")
            .getJSONObject("fmt_options")
            .getBoolean("show_protected_facets"));
  }
}
