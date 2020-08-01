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

public class ConstructorIOAutocompleteUrlEncodingTest {

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
    public void AutocompleteWithPlusShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r+co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/autocomplete/r%2Bco?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.3.1";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteWithSpaceShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/autocomplete/r%20co?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.3.1";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteWithSlashShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r/co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/autocomplete/r%2Fco?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.3.1";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }

    @Test
    public void AutocompleteWithSingleQuoteShouldBeEncodedInUrl() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody(string);
        mockServer.enqueue(mockResponse);

        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", false, "127.0.0.1", mockServer.getPort());
        AutocompleteRequest request = new AutocompleteRequest("r'co");
        constructor.autocomplete(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        String expectedPath = "/autocomplete/r'co?key=key_K2hlXt5aVSwoI1Uw&c=ciojava-5.3.1";
        String actualPath = recordedRequest.getPath();
        assertEquals("recorded request is encoded correctly", actualPath, expectedPath);
    }
}