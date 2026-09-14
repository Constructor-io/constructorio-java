package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import io.constructor.client.models.BrowseFacetOptionsResponse;
import io.constructor.client.models.BrowseFacetsResponse;
import io.constructor.client.models.BrowseResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConstructorIOBrowseResponseHeadersTest {

    private static MockWebServer mockServer;
    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

    @BeforeClass
    public static void setup() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterClass
    public static void teardown() throws Exception {
        mockServer.shutdown();
    }

    private MockResponse rateLimitedSuccessResponse(String body) {
        return new MockResponse()
                .setResponseCode(200)
                .setBody(body)
                .addHeader("X-RateLimit-Limit", "100")
                .addHeader("X-RateLimit-Remaining", "99")
                .addHeader("X-RateLimit-Reset", "1620000000");
    }

    private MockResponse rateLimitedErrorResponse() {
        return new MockResponse()
                .setResponseCode(429)
                .setBody("{\"message\":\"Too Many Requests\"}")
                .addHeader("X-RateLimit-Limit", "100")
                .addHeader("X-RateLimit-Remaining", "0")
                .addHeader("X-RateLimit-Reset", "1620000060");
    }

    private void assertRateLimitHeaders(Map<String, List<String>> headers) {
        assertNotNull("headers should not be null", headers);
        assertEquals("rate limit header", "100", headers.get("x-ratelimit-limit").get(0));
        assertEquals(
                "rate limit remaining header", "99", headers.get("x-ratelimit-remaining").get(0));
        assertEquals(
                "rate limit reset header", "1620000000", headers.get("x-ratelimit-reset").get(0));
    }

    private void assertRateLimitHeadersOnError(Map<String, List<String>> headers) {
        assertNotNull("headers should not be null", headers);
        assertEquals("rate limit header", "100", headers.get("x-ratelimit-limit").get(0));
        assertEquals(
                "rate limit remaining header", "0", headers.get("x-ratelimit-remaining").get(0));
        assertEquals(
                "rate limit reset header", "1620000060", headers.get("x-ratelimit-reset").get(0));
    }

    @Test
    public void BrowseShouldReturnRateLimitHeaders() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        mockServer.enqueue(rateLimitedSuccessResponse(string));

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, null);

        mockServer.takeRequest();
        assertRateLimitHeaders(response.getHeaders());
    }

    @Test
    public void BrowseShouldReturnRateLimitHeadersOnError() throws Exception {
        mockServer.enqueue(rateLimitedErrorResponse());

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseRequest request = new BrowseRequest("Color", "Blue");

        try {
            constructor.browse(request, null);
            fail("Expected ConstructorException to be thrown");
        } catch (ConstructorException e) {
            mockServer.takeRequest();
            assertRateLimitHeadersOnError(e.getHeaders());
        }
    }

    @Test
    public void BrowseItemsShouldReturnRateLimitHeaders() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        mockServer.enqueue(rateLimitedSuccessResponse(string));

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseItemsRequest request = new BrowseItemsRequest(Arrays.asList("item1"));
        BrowseResponse response = constructor.browseItems(request, null);

        mockServer.takeRequest();
        assertRateLimitHeaders(response.getHeaders());
    }

    @Test
    public void BrowseItemsShouldReturnRateLimitHeadersOnError() throws Exception {
        mockServer.enqueue(rateLimitedErrorResponse());

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseItemsRequest request = new BrowseItemsRequest(Arrays.asList("item1"));

        try {
            constructor.browseItems(request, null);
            fail("Expected ConstructorException to be thrown");
        } catch (ConstructorException e) {
            mockServer.takeRequest();
            assertRateLimitHeadersOnError(e.getHeaders());
        }
    }

    @Test
    public void BrowseFacetsShouldReturnRateLimitHeaders() throws Exception {
        String string = Utils.getTestResource("response.browsefacets.json");
        mockServer.enqueue(rateLimitedSuccessResponse(string));

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseFacetsRequest request = new BrowseFacetsRequest();
        BrowseFacetsResponse response = constructor.browseFacets(request);

        mockServer.takeRequest();
        assertRateLimitHeaders(response.getHeaders());
    }

    @Test
    public void BrowseFacetsShouldReturnRateLimitHeadersOnError() throws Exception {
        mockServer.enqueue(rateLimitedErrorResponse());

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseFacetsRequest request = new BrowseFacetsRequest();

        try {
            constructor.browseFacets(request);
            fail("Expected ConstructorException to be thrown");
        } catch (ConstructorException e) {
            mockServer.takeRequest();
            assertRateLimitHeadersOnError(e.getHeaders());
        }
    }

    @Test
    public void BrowseFacetOptionsShouldReturnRateLimitHeaders() throws Exception {
        String string = Utils.getTestResource("response.browsefacetoptions.json");
        mockServer.enqueue(rateLimitedSuccessResponse(string));

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest("Color");
        BrowseFacetOptionsResponse response = constructor.browseFacetOptions(request);

        mockServer.takeRequest();
        assertRateLimitHeaders(response.getHeaders());
    }

    @Test
    public void BrowseFacetOptionsShouldReturnRateLimitHeadersOnError() throws Exception {
        mockServer.enqueue(rateLimitedErrorResponse());

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest("Color");

        try {
            constructor.browseFacetOptions(request);
            fail("Expected ConstructorException to be thrown");
        } catch (ConstructorException e) {
            mockServer.takeRequest();
            assertRateLimitHeadersOnError(e.getHeaders());
        }
    }
}
