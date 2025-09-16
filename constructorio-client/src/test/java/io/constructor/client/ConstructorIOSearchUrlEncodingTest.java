package io.constructor.client;

import static org.junit.Assert.assertEquals;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOSearchUrlEncodingTest {

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
    public void SearchWithPlusShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r+co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath =
                String.format(
                        "/search/r%%2Bco?key=%s&c=ciojava-7.0.0&section=Products&num_results_per_page=30",
                        apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void SearchWithSpaceShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath =
                String.format(
                        "/search/r%%20co?key=%s&c=ciojava-7.0.0&section=Products&num_results_per_page=30",
                        apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void SearchWithSlashShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r/co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath =
                String.format(
                        "/search/r%%2Fco?key=%s&c=ciojava-7.0.0&section=Products&num_results_per_page=30",
                        apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void SearchWithSingleQuoteShouldBeAllowedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor =
                new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r'co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath =
                String.format(
                        "/search/r'co?key=%s&c=ciojava-7.0.0&section=Products&num_results_per_page=30",
                        apiKey);
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }
}
