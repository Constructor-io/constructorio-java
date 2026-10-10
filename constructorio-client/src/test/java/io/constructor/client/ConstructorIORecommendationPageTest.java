package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.RecommendationPagePod;
import io.constructor.client.models.RecommendationPageResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIORecommendationPageTest {

    private static final String MOCK_API_KEY = "key_mock_page_api";
    private static final String PAGE_RESULT_ID = "a1b2c3d4-0000-0000-0000-0000000000ff";
    private static MockWebServer mockServer;

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

    private ConstructorIO mockedConstructor() throws Exception {
        String body = Utils.getTestResource("response.recommendation_page.pdp_b2c.json");
        mockServer.enqueue(new MockResponse().setResponseCode(200).setBody(body));
        return new ConstructorIO("", MOCK_API_KEY, false, "127.0.0.1", mockServer.getPort());
    }

    @Test
    public void recommendationPageShouldRequestPageEndpointWithSharedParameters() throws Exception {
        ConstructorIO constructor = mockedConstructor();
        RecommendationPageRequest request = new RecommendationPageRequest("pdp_b2c");
        Map<String, List<String>> facets = new HashMap<String, List<String>>();
        facets.put("in_stock", Arrays.asList("true"));
        Map<String, String> filterMatchTypes = new HashMap<String, String>();
        filterMatchTypes.put("color", "any");
        request.setItemIds(Arrays.asList("product-123"));
        request.setNumResults(10);
        request.setFacets(facets);
        request.setFilterMatchTypes(filterMatchTypes);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserId("user-id");

        constructor.recommendationPage(request, userInfo);

        HttpUrl url = mockServer.takeRequest().getRequestUrl();
        assertEquals("/recommendations/v1/pages/pdp_b2c", url.encodedPath());
        assertEquals(MOCK_API_KEY, url.queryParameter("key"));
        assertEquals("c62a-2a09-faie", url.queryParameter("i"));
        assertEquals("3", url.queryParameter("s"));
        assertEquals("user-id", url.queryParameter("ui"));
        assertEquals("Products", url.queryParameter("section"));
        assertEquals("product-123", url.queryParameter("item_id"));
        assertEquals("10", url.queryParameter("num_results"));
        assertEquals("true", url.queryParameter("filters[in_stock]"));
        assertEquals("any", url.queryParameter("filter_match_types[color]"));
        for (String name : url.queryParameterNames()) {
            assertTrue("no pod overrides were sent", !name.startsWith("pod_overrides"));
        }
    }

    @Test
    public void recommendationPageShouldEncodePodOverridesInBracketNotation() throws Exception {
        ConstructorIO constructor = mockedConstructor();
        RecommendationPageRequest request = new RecommendationPageRequest("pdp_b2c");
        request.setItemIds(Arrays.asList("product-123"));
        request.setNumResults(10);

        RecommendationPagePodOverride similarItems = new RecommendationPagePodOverride();
        similarItems.setNumResults(0);

        RecommendationPagePodOverride completeTheLook = new RecommendationPagePodOverride();
        Map<String, List<String>> facets = new LinkedHashMap<String, List<String>>();
        facets.put("in_stock", Arrays.asList("true"));
        facets.put("color", Arrays.asList("red", "blue"));
        Map<String, String> filterMatchTypes = new HashMap<String, String>();
        filterMatchTypes.put("color", "all");
        Map<String, String> formatOptions = new HashMap<String, String>();
        formatOptions.put("groups_max_depth", "2");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.addGroupByRule("color", "data.color");
        completeTheLook.setNumResults(8);
        completeTheLook.setFacets(facets);
        completeTheLook.setFilterMatchTypes(filterMatchTypes);
        completeTheLook.setPreFilterExpression(
                "{\"or\":[{\"name\":\"brand\",\"value\":\"acme\"}]}");
        completeTheLook.setVariationsMap(variationsMap);
        completeTheLook.setFormatOptions(formatOptions);
        completeTheLook.setHiddenFields(Arrays.asList("inventory", "margin"));

        Map<String, RecommendationPagePodOverride> podOverrides =
                new LinkedHashMap<String, RecommendationPagePodOverride>();
        podOverrides.put("similar_items", similarItems);
        podOverrides.put("complete_the_look", completeTheLook);
        request.setPodOverrides(podOverrides);

        constructor.recommendationPage(request, null);

        RecordedRequest recordedRequest = mockServer.takeRequest();
        HttpUrl url = recordedRequest.getRequestUrl();
        String prefix = "pod_overrides[complete_the_look]";
        assertEquals("10", url.queryParameter("num_results"));
        assertEquals("0", url.queryParameter("pod_overrides[similar_items][num_results]"));
        assertEquals("8", url.queryParameter(prefix + "[num_results]"));
        assertEquals("true", url.queryParameter(prefix + "[filters][in_stock]"));
        assertEquals(
                Arrays.asList("red", "blue"),
                url.queryParameterValues(prefix + "[filters][color]"));
        assertEquals("all", url.queryParameter(prefix + "[filter_match_types][color]"));
        assertEquals(
                "{\"or\":[{\"name\":\"brand\",\"value\":\"acme\"}]}",
                url.queryParameter(prefix + "[pre_filter_expression]"));
        assertEquals(
                new com.google.gson.Gson().toJson(variationsMap),
                url.queryParameter(prefix + "[variations_map]"));
        assertEquals("2", url.queryParameter(prefix + "[fmt_options][groups_max_depth]"));
        assertEquals(
                Arrays.asList("inventory", "margin"),
                url.queryParameterValues(prefix + "[fmt_options][hidden_fields]"));
        assertTrue(
                "brackets are percent-encoded in the raw path",
                recordedRequest
                        .getPath()
                        .contains("pod_overrides%5Bsimilar_items%5D%5Bnum_results%5D=0"));
    }

    @Test
    public void recommendationPageShouldReturnPerPodResultIds() throws Exception {
        ConstructorIO constructor = mockedConstructor();
        RecommendationPageRequest request = new RecommendationPageRequest("pdp_b2c");
        request.setItemIds(Collections.singletonList("product-123"));

        RecommendationPageResponse response = constructor.recommendationPage(request, null);
        mockServer.takeRequest();

        List<RecommendationPagePod> pods = response.getResponse().getPods();
        assertEquals(PAGE_RESULT_ID, response.getResultId());
        assertEquals("pdp_b2c", response.getResponse().getPageId());
        assertEquals("pdp", response.getResponse().getPageType());
        assertEquals(2, pods.size());
        assertEquals("similar_items", pods.get(0).getPodId());
        assertEquals("a1b2c3d4-0000-0000-0000-000000000001", pods.get(0).getResultId());
        assertEquals("similar_items", pods.get(0).getResponse().getPod().getId());
        assertEquals(1, pods.get(0).getResponse().getResults().size());
        assertEquals("Red Running Shoe", pods.get(0).getResponse().getResults().get(0).getValue());
        assertEquals(
                49.99,
                pods.get(0).getResponse().getResults().get(0).getData().getMetadata().get("price"));
        assertEquals("complete_the_look", pods.get(1).getPodId());
        assertEquals("a1b2c3d4-0000-0000-0000-000000000002", pods.get(1).getResultId());
        assertEquals(0, pods.get(1).getResponse().getResults().size());
        for (RecommendationPagePod pod : pods) {
            assertNotEquals(PAGE_RESULT_ID, pod.getResultId());
        }
    }

    @Test
    public void recommendationPageRequestShouldRequirePageId() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("pageId is a required parameter of type string");
        new RecommendationPageRequest(null);
    }

    @Test
    public void recommendationPageShouldRejectVariationIdWithoutSingleItemId() throws Exception {
        ConstructorIO constructor =
                new ConstructorIO("", MOCK_API_KEY, false, "127.0.0.1", mockServer.getPort());
        RecommendationPageRequest request = new RecommendationPageRequest("pdp_b2c");
        request.setVariationId("v1");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("variationId requires exactly one itemId to be specified");
        constructor.recommendationPage(request, null);
    }

    @Test
    public void recommendationPageRequestDefaults() throws Exception {
        RecommendationPageRequest request = new RecommendationPageRequest("pdp_b2c");

        assertEquals("pdp_b2c", request.getPageId());
        assertEquals("Products", request.getSection());
        assertNull(request.getNumResults());
        assertTrue(request.getPodOverrides().isEmpty());
    }

    // The page endpoint is not yet enabled on the test index, and the test index
    // has no page configured (`/recommendations/v1/pages/pdp_b2c` returns 404 there)
    @Ignore("the page endpoint is not enabled and no page is configured on the test index")
    @Test
    public void recommendationPageShouldReturnPerPodResultIdsLive() throws Exception {
        ConstructorIO constructor =
                new ConstructorIO("", System.getenv("TEST_REQUEST_API_KEY"), true, null);
        RecommendationPageRequest request = new RecommendationPageRequest("pdp_b2c");
        request.setItemIds(Collections.singletonList("power_drill"));

        RecommendationPageResponse response = constructor.recommendationPage(request, null);

        assertTrue(response.getResponse().getPods().size() > 0);
        for (RecommendationPagePod pod : response.getResponse().getPods()) {
            assertNotEquals(response.getResultId(), pod.getResultId());
            assertEquals(pod.getPodId(), pod.getResponse().getPod().getId());
        }
    }
}
