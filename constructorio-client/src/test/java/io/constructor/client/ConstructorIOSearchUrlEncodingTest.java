package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ConstructorIOSearchUrlEncodingTest {

    private static MockWebServer mockServer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r+co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/search/r%2Bco?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.6.0&section=Products&page=1&num_results_per_page=30";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void SearchWithSpaceShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/search/r%20co?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.6.0&section=Products&page=1&num_results_per_page=30";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void SearchWithSlashShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r/co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/search/r%2Fco?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.6.0&section=Products&page=1&num_results_per_page=30";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void SearchWithSingleQuoteShouldBeAllowedInUrl() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        SearchRequest request = new SearchRequest("r'co");
        constructor.search(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/search/r'co?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.6.0&section=Products&page=1&num_results_per_page=30";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }
}