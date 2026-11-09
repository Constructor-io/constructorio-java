package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOTrackSearchResultsLoadedTest {

    private MockWebServer mockServer;
    private String apiKey = "test-api-key";

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @After
    public void teardown() throws Exception {
        mockServer.shutdown();
    }

    private ConstructorIO createConstructorIO() {
        return new ConstructorIO("", apiKey, false, "127.0.0.1", mockServer.getPort());
    }

    @Test
    public void trackSearchResultsLoadedShouldSendEventWithResultId() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(204));
        ConstructorIO constructor = createConstructorIO();
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserId("user-1");
        userInfo.setUserSegments(Arrays.asList("mobile", "us"));
        userInfo.setForwardedFor("10.0.0.1");
        userInfo.setUserAgent("test-agent");
        SearchResultsLoadedRequest req = new SearchResultsLoadedRequest("peanut", 167);
        req.setResultId("result-id-123");
        req.setItems(
                Arrays.asList(
                        new SearchResultsLoadedRequest.Item("KMH876"),
                        new SearchResultsLoadedRequest.Item("KMH140", "KMH140-red")));
        req.setResultPage(2);
        req.setSection("Products");
        req.setUrl("https://example.com/search?q=peanut");

        assertTrue(constructor.trackSearchResultsLoaded(req, userInfo));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        HttpUrl url = request.getRequestUrl();
        assertEquals("/v2/behavioral_action/search_result_load", url.encodedPath());
        assertEquals(apiKey, url.queryParameter("key"));
        assertEquals("ciojava-7.8.0", url.queryParameter("c"));
        assertEquals("c62a-2a09-faie", url.queryParameter("i"));
        assertEquals("3", url.queryParameter("s"));
        assertEquals("user-1", url.queryParameter("ui"));
        assertEquals(Arrays.asList("mobile", "us"), url.queryParameterValues("us"));
        assertEquals("true", url.queryParameter("beacon"));
        assertTrue(Long.parseLong(url.queryParameter("_dt")) > 0);
        assertEquals("10.0.0.1", request.getHeader("x-forwarded-for"));
        assertEquals("test-agent", request.getHeader("User-Agent"));
        assertTrue(request.getHeader("Content-Type").startsWith("application/json"));

        JSONObject body = new JSONObject(request.getBody().readUtf8());
        assertEquals("peanut", body.getString("search_term"));
        assertEquals(167, body.getInt("result_count"));
        assertEquals("result-id-123", body.getString("result_id"));
        assertEquals(2, body.getInt("result_page"));
        assertEquals("Products", body.getString("section"));
        assertEquals("https://example.com/search?q=peanut", body.getString("url"));
        assertEquals("c62a-2a09-faie", body.getString("i"));
        assertEquals(3, body.getInt("s"));
        assertEquals(apiKey, body.getString("key"));
        assertTrue(body.getBoolean("beacon"));
        assertEquals(url.queryParameter("_dt"), String.valueOf(body.getLong("_dt")));

        JSONArray items = body.getJSONArray("items");
        assertEquals(2, items.length());
        assertEquals("KMH876", items.getJSONObject(0).getString("item_id"));
        assertFalse(items.getJSONObject(0).has("variation_id"));
        assertEquals("KMH140", items.getJSONObject(1).getString("item_id"));
        assertEquals("KMH140-red", items.getJSONObject(1).getString("variation_id"));
    }

    @Test
    public void trackSearchResultsLoadedShouldOmitUnsetOptionalFields() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(204));
        ConstructorIO constructor = createConstructorIO();
        SearchResultsLoadedRequest req = new SearchResultsLoadedRequest("peanut", 0);

        assertTrue(constructor.trackSearchResultsLoaded(req, new UserInfo(1, "client")));

        RecordedRequest request = mockServer.takeRequest();
        JSONObject body = new JSONObject(request.getBody().readUtf8());
        assertEquals("N/A", body.getString("url"));
        assertFalse(body.has("result_id"));
        assertFalse(body.has("items"));
        assertFalse(body.has("result_page"));
        assertFalse(body.has("section"));
        assertFalse(body.has("ui"));
        assertFalse(body.has("us"));
    }

    @Test
    public void trackSearchResultsLoadedShouldSendAtMost100Items() throws Exception {
        mockServer.enqueue(new MockResponse().setResponseCode(204));
        ConstructorIO constructor = createConstructorIO();
        List<SearchResultsLoadedRequest.Item> items =
                new ArrayList<SearchResultsLoadedRequest.Item>();
        for (int i = 0; i < 150; i++) {
            items.add(new SearchResultsLoadedRequest.Item("item-" + i));
        }
        SearchResultsLoadedRequest req = new SearchResultsLoadedRequest("peanut", 150);
        req.setItems(items);

        constructor.trackSearchResultsLoaded(req, new UserInfo(1, "client"));

        JSONObject body = new JSONObject(mockServer.takeRequest().getBody().readUtf8());
        assertEquals(100, body.getJSONArray("items").length());
    }

    @Test
    public void trackSearchResultsLoadedShouldThrowWithoutUserInfo() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("userInfo with a clientId and sessionId is required");
        createConstructorIO()
                .trackSearchResultsLoaded(new SearchResultsLoadedRequest("peanut", 1), null);
    }

    @Test
    public void trackSearchResultsLoadedShouldThrowWithoutClientIdOrSessionId() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("userInfo with a clientId and sessionId is required");
        createConstructorIO()
                .trackSearchResultsLoaded(
                        new SearchResultsLoadedRequest("peanut", 1), new UserInfo());
    }

    @Test
    public void trackSearchResultsLoadedShouldThrowOnServerError() throws Exception {
        mockServer.enqueue(
                new MockResponse().setResponseCode(400).setBody("{\"message\":\"bad request\"}"));
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 400] bad request");
        createConstructorIO()
                .trackSearchResultsLoaded(
                        new SearchResultsLoadedRequest("peanut", 1), new UserInfo(1, "client"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void newSearchResultsLoadedRequestShouldThrowWithoutTerm() {
        new SearchResultsLoadedRequest(null, 1);
    }
}
