package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import io.constructor.client.models.AutocompleteResponse;
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

public class ConstructorIOAutocompleteUrlEncodingTest {

    private static MockWebServer mockServer;
    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

    @Rule public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setup() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterClass
    public static void teardown() throws Exception {
        mockServer.shutdown();
    }

    @Test
    public void AutocompleteWithPlusShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r+co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = String.format("/autocomplete/r%%2Bco?key=%s&c=ciojava-7.6.0", apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteWithSpaceShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = String.format("/autocomplete/r%%20co?key=%s&c=ciojava-7.6.0", apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteWithSlashShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r/co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = String.format("/autocomplete/r%%2Fco?key=%s&c=ciojava-7.6.0", apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteWithSingleQuoteShouldBeAllowedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r'co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = String.format("/autocomplete/r'co?key=%s&c=ciojava-7.6.0", apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteShouldReturnRateLimitHeaders() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse =
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(string)
                        .addHeader("X-RateLimit-Limit", "100")
                        .addHeader("X-RateLimit-Remaining", "99")
                        .addHeader("X-RateLimit-Reset", "1620000000");
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("peanut");
        AutocompleteResponse response = constructor.autocomplete(request, null);

        mockServer.takeRequest();

        Map<String, List<String>> headers = response.getHeaders();
        assertNotNull("headers should not be null", headers);
        assertEquals("rate limit header", "100", headers.get("x-ratelimit-limit").get(0));
        assertEquals(
                "rate limit remaining header", "99", headers.get("x-ratelimit-remaining").get(0));
        assertEquals(
                "rate limit reset header", "1620000000", headers.get("x-ratelimit-reset").get(0));
    }

    @Test
    public void AutocompleteShouldReturnRateLimitHeadersOnError() throws Exception {
        MockResponse mockResponse =
                new MockResponse()
                        .setResponseCode(429)
                        .setBody("{\"message\":\"Too Many Requests\"}")
                        .addHeader("X-RateLimit-Limit", "100")
                        .addHeader("X-RateLimit-Remaining", "0")
                        .addHeader("X-RateLimit-Reset", "1620000060");
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("peanut");

        try {
            constructor.autocomplete(request, null);
            fail("Expected ConstructorException to be thrown");
        } catch (ConstructorException e) {
            mockServer.takeRequest();

            Map<String, List<String>> headers = e.getHeaders();
            assertNotNull("headers should not be null", headers);
            assertEquals("rate limit header", "100", headers.get("x-ratelimit-limit").get(0));
            assertEquals(
                    "rate limit remaining header",
                    "0",
                    headers.get("x-ratelimit-remaining").get(0));
            assertEquals(
                    "rate limit reset header",
                    "1620000060",
                    headers.get("x-ratelimit-reset").get(0));
        }
    }
}
