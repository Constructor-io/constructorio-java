package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.RecommendationsResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIORecommendationsTest {

    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getRecommendationsShouldReturnAResultWithSingleItemId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill"));
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithMultipleItemIds() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithNumResults() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        request.setNumResults(5);
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        request.setNumResults(5);
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithVariationsMap() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.object);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        request.setVariationsMap(variationsMap);
        RecommendationsResponse response = constructor.recommendations(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);

        assertNotNull("variations map exists", response.getRequest().get("variations_map"));
        assertEquals(
                "variations map is correct",
                variationsMap.getdType(),
                variationsMapFromResponse.getdType());
        assertEquals(
                "variations map values is correct",
                variationsMap.getValues().get("size").aggregation,
                variationsMapFromResponse.getValues().get("size").aggregation);
        assertEquals(
                "variations map group by is correct",
                variationsMap.getGroupBy().get(0).field,
                variationsMapFromResponse.getGroupBy().get(0).field);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithFilters() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("filtered_items");
        HashMap<String, List<String>> facets = new HashMap<String, List<String>>();
        facets.put("Color", Arrays.asList("yellow", "red", "green"));
        request.setFacets(facets);
        request.setNumResults(5);
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() > 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithPreFilterExpression() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("filtered_items");
        String preFilterExpression =
                "{\"or\":[{\"and\":[{\"name\":\"group_id\",\"value\":\"electronics-group-id\"},{\"name\":\"Price\",\"range\":[\"-inf\",200.0]}]},{\"and\":[{\"name\":\"Type\",\"value\":\"Laptop\"},{\"not\":{\"name\":\"Price\",\"range\":[800.0,\"inf\"]}}]}]}";
        request.setPreFilterExpression(preFilterExpression);

        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        String preFilterExpressionFromRequestJsonString =
                new Gson().toJson(response.getRequest().get("pre_filter_expression"));

        assertEquals(preFilterExpression, preFilterExpressionFromRequestJsonString);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultTerm() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("query_recommendations");
        request.setTerm("test");

        RecommendationsResponse response = constructor.recommendations(request, userInfo);

        assertEquals(response.getRequest().get("term"), "test");
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithFmtOptionsAndHiddenFields()
            throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        request.setNumResults(5);
        request.getHiddenFields().add("testField");
        request.getFormatOptions().put("groups_max_depth", "3");

        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        Map<String, Object> fmtOptions =
                (Map<String, Object>) response.getRequest().get("fmt_options");

        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
        assertEquals(fmtOptions.get("groups_max_depth"), Double.valueOf("3.0"));
        assertTrue(((List<String>) fmtOptions.get("hidden_fields")).contains("testField"));
    }
}
