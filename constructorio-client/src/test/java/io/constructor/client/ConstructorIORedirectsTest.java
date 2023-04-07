package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.AllRedirectsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIORedirectsTest {

    private String apiToken = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_CATALOG_API_KEY");

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void AllRedirectsShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        AllRedirectsResponse response = constructor.allRedirects(request);

        assertTrue("Redirect rules exist", response.getRedirectRules().size() > 0);
        assertTrue("Redirect rules exist", response.getTotalCount() > 0);
        assertTrue("Redirect is not null", response.getRedirectRules().get(0) != null);
    }

    @Test
    public void AllRedirectsAsJsonShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        String stringResponse = constructor.allRedirectsAsJson(request);
        AllRedirectsResponse response =
                new Gson().fromJson(stringResponse, AllRedirectsResponse.class);

        assertTrue("Redirect rules exist", response.getRedirectRules().size() > 0);
        assertTrue("Redirect rules exist", response.getTotalCount() > 0);
        assertTrue("Redirect is not null", response.getRedirectRules().get(0) != null);
    }

    @Test
    public void AllRedirectsShouldReturnAResultWhenPageAndResultsPerPageArePassed()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setPage(3);
        request.setResultsPerPage(100);
        AllRedirectsResponse response = constructor.allRedirects(request);

        assertTrue("Redirect rules exist", response.getRedirectRules().size() > 0);
        assertTrue("Redirect rules exist", response.getTotalCount() > 0);
        assertTrue("Redirect is not null", response.getRedirectRules().get(0) != null);
    }

    @Test
    public void AllRedirectsAsJsonShouldReturnAResultWhenPageAndResultsPerPageArePassed()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setPage(3);
        request.setResultsPerPage(100);
        String stringResponse = constructor.allRedirectsAsJson(request);
        AllRedirectsResponse response =
                new Gson().fromJson(stringResponse, AllRedirectsResponse.class);

        assertTrue("Redirect rules exist", response.getRedirectRules().size() > 0);
        assertTrue("Redirect rules exist", response.getTotalCount() > 0);
        assertTrue("Redirect is not null", response.getRedirectRules().get(0) != null);
    }

    @Test
    public void AllRedirectsShouldReturnAResultWhenOffsetAndResultsPerPageArePassed()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setOffset(100);
        request.setResultsPerPage(100);
        AllRedirectsResponse response = constructor.allRedirects(request);

        assertTrue("Redirect rules exist", response.getRedirectRules().size() > 0);
        assertTrue("Redirect rules exist", response.getTotalCount() > 0);
        assertTrue("Redirect is not null", response.getRedirectRules().get(0) != null);
    }

    @Test
    public void AllRedirectsAsJsonShouldReturnAResultWhenOffsetAndResultsPerPageArePassed()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setOffset(100);
        request.setResultsPerPage(100);
        String stringResponse = constructor.allRedirectsAsJson(request);
        AllRedirectsResponse response =
                new Gson().fromJson(stringResponse, AllRedirectsResponse.class);

        assertTrue("Redirect rules exist", response.getRedirectRules().size() > 0);
        assertTrue("Redirect rules exist", response.getTotalCount() > 0);
        assertTrue("Redirect is not null", response.getRedirectRules().get(0) != null);
    }

    @Test
    public void AllRedirectsShouldReturnAResultWhenStatusIsPassed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setStatus("expired");
        AllRedirectsResponse response = constructor.allRedirects(request);

        assertTrue("Redirect rules do not exist", response.getRedirectRules().size() == 0);
        assertTrue("Redirect rules do not exist", response.getTotalCount() == 0);
        assertTrue("Redirects are null", response.getRedirectRules().size() == 0);
    }

    @Test
    public void AllRedirectsAsJsonShouldReturnAResultWhenStatusIsPassed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setStatus("expired");
        String stringResponse = constructor.allRedirectsAsJson(request);
        AllRedirectsResponse response =
                new Gson().fromJson(stringResponse, AllRedirectsResponse.class);

        assertTrue("Redirect rules do not exist", response.getRedirectRules().size() == 0);
        assertTrue("Redirect rules do not exist", response.getTotalCount() == 0);
        assertTrue("Redirects are null", response.getRedirectRules().size() == 0);
    }

    @Test
    public void AllRedirectsShouldReturnAResultWhenQueryIsPassed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setQuery("another rickroll");
        AllRedirectsResponse response = constructor.allRedirects(request);

        assertTrue("Redirect rules do not exist", response.getRedirectRules().size() == 1);
        assertTrue("Redirect rules do not exist", response.getTotalCount() == 1);
        assertTrue("Redirects are null", response.getRedirectRules().size() == 1);
        assertTrue(
                "Redirect matches query",
                response.getRedirectRules()
                        .get(0)
                        .getMatches()
                        .get(0)
                        .getPattern()
                        .equals("another rickroll"));
    }

    @Test
    public void AllRedirectsAsJsonShouldReturnAResultWhenQueryIsPassed() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        AllRedirectsRequest request = new AllRedirectsRequest();
        request.setQuery("another rickroll");
        String stringResponse = constructor.allRedirectsAsJson(request);
        AllRedirectsResponse response =
                new Gson().fromJson(stringResponse, AllRedirectsResponse.class);

        assertTrue("Redirect rules do not exist", response.getRedirectRules().size() == 1);
        assertTrue("Redirect rules do not exist", response.getTotalCount() == 1);
        assertTrue("Redirects are null", response.getRedirectRules().size() == 1);
        assertTrue(
                "Redirect matches query",
                response.getRedirectRules()
                        .get(0)
                        .getMatches()
                        .get(0)
                        .getPattern()
                        .equals("another rickroll"));
    }
}
