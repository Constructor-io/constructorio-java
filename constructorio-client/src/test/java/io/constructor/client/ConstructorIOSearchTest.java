package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.FilterFacet;
import io.constructor.client.models.FilterGroup;
import io.constructor.client.models.SearchResponse;

public class ConstructorIOSearchTest {

    private String apiKey = System.getenv("TEST_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void SearchAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item");
        String response = constructor.searchAsJSON(request, userInfo);
        assertTrue("search results exist", response.length() > 1);
    }

    @Test
    public void SearchShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item1");
        request.setResultsPerPage(5);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 5);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 9);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithLastPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item1");
        request.setPage(1);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 9);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 9);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithSearchSuggestionsSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        request.setSection("Search Suggestions");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 4);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 4);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item1");
        request.setGroupId("All");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 2);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 2);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithColorFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item");
        request.getFacets().put("Color", Arrays.asList("Blue"));
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 1);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 1);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item1");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 9);
        assertEquals("search result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 1);
        assertEquals("search result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 2);
        assertEquals("search result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "item1 variation");
        assertEquals("search result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "20001");
        assertEquals("search result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://test.com/p/20001");
        assertTrue("search total results count should be greater than 0", (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search result [sort options] exists", response.getResponse().getSortOptions().size(), 2);
        assertEquals("search result sort option [display name] exists", response.getResponse().getSortOptions().get(1).getDisplayName(), "DESC");
        assertEquals("search result sort option [sort by] exists", response.getResponse().getSortOptions().get(1).getSortBy(), "relevance");
        assertEquals("search result sort option [sort order] exists", response.getResponse().getSortOptions().get(1).getSortOrder(), "descending");
        assertEquals("search result sort option [status] exists", response.getResponse().getSortOptions().get(1).getStatus(), "selected");
        assertTrue("search total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        SearchRequest request = new SearchRequest("item");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 9);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 9);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        SearchRequest request = new SearchRequest("item");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 9);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 9);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithRedirect() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("medium");
        SearchResponse response = constructor.search(request, userInfo);
        assertNull("search results do not exist", response.getResponse().getResults());
        assertTrue("search redirect exists", response.getResponse().getRedirect() != null);
        assertTrue("search redirect data exists", response.getResponse().getRedirect().getData() != null);
        assertTrue("search redirect data URL exists", response.getResponse().getRedirect().getData().getUrl() != null);
        assertTrue("search redirect data rule ID exists", response.getResponse().getRedirect().getData().getRuleId() != null);
        assertTrue("search redirect data match ID exists", response.getResponse().getRedirect().getData().getMatchId() != null);
        assertTrue("search redirect matched terms exist", response.getResponse().getRedirect().getMatchedTerms().contains("medium"));
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithMaxGroupsDepthOf3() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item2");
        request.getFormatOptions().put("groups_max_depth", "3");
        SearchResponse response = constructor.search(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        FilterGroup secondGen = firstGen.getChildren().get(0);
        FilterGroup thirdGen = secondGen.getChildren().get(0);
        assertEquals("search result [root] exists", root.getGroupId(), "All");
        assertEquals("search result [firstGen] exists", firstGen.getGroupId(), "Brands");
        assertEquals("search result [secondGen] exists", secondGen.getGroupId(), "BrandX");
        assertEquals("search result [thirdGen] exists", thirdGen.getGroupId(), "BrandXY");
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithMaxGroupsDepthOf1() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item2");
        request.getFormatOptions().put("groups_max_depth", "1");
        SearchResponse response = constructor.search(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        assertEquals("search result [root] exists", root.getGroupId(), "All");
        assertEquals("search result [firstGen] exists", firstGen.getGroupId(), "Brands");
        assertEquals("search result [firstGen] children", firstGen.getChildren().size(), 0);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithHiddenFields() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item1");
        request.getHiddenFields().add("testField");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 9);
        assertEquals("search result [testField] exists", response.getResponse().getResults().get(0).getData().getMetadata().get("testField"), "hiddenFieldValue");
    }

    @Test
    public void SearchShouldReturnAResultWithHiddenFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("item1");
        request.getHiddenFacets().add("Brand");

        SearchResponse response = constructor.search(request, userInfo);
        FilterFacet brandFacet = response.getResponse().getFacets().stream().filter(new Predicate<FilterFacet>() {
            @Override
            public boolean test(FilterFacet f) {
                return f.getName().equals("Brand");
            }
        }).findAny().orElse(null);

        assertEquals("search results exist", response.getResponse().getResults().size(), 9);
        assertNotNull("search facet [Brand] exists", brandFacet);
    }

    @Test
    public void SearchShouldReturnAResultWithResultSources() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search result result sources exists", (int)response.getResponse().getResultSources().getTokenMatch().getCount(), 1);
        assertEquals("search result result sources exists", (int)response.getResponse().getResultSources().getEmbeddingsMatch().getCount(), 0);
    }

    @Test
    public void SearchShouldReturnAResultProvidedVariationsMapAsArray() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("Jacket");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.array);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule("size", VariationsMap.AggregationTypes.first, "data.facets.size");
        request.setVariationsMap(variationsMap);
        SearchResponse response = constructor.search(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);
        ArrayList<Object> varMapObject = (ArrayList<Object>) response.getResponse().getResults().get(0).getVariationsMap();

        assertNotNull("variations map exists", response.getRequest().get("variations_map"));
        assertEquals("variations map is correct", variationsMap.getdType(), variationsMapFromResponse.getdType());
        assertTrue("result contains variations_map", varMapObject.size() >= 1);
        assertEquals("variations map values is correct", variationsMap.getValues().get("size").aggregation, variationsMapFromResponse.getValues().get("size").aggregation);
        assertEquals("variations map group by is correct", variationsMap.getGroupBy().get(0).field, variationsMapFromResponse.getGroupBy().get(0).field);
    }

    @Test
    public void SearchShouldReturnAResultProvidedVariationsMapAsObject() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("Jacket");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.object);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule("size", VariationsMap.AggregationTypes.first, "data.facets.size");
        request.setVariationsMap(variationsMap);
        SearchResponse response = constructor.search(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);
        LinkedTreeMap<String, Object> varMapObject = (LinkedTreeMap<String, Object>) response.getResponse().getResults().get(0).getVariationsMap();

        assertNotNull("variations map exists", response.getRequest().get("variations_map"));
        assertEquals("variations map is correct", variationsMap.getdType(), variationsMapFromResponse.getdType());
        assertTrue("result contains variations_map", varMapObject.size() >= 1);
        assertEquals("variations map values is correct", variationsMap.getValues().get("size").aggregation, variationsMapFromResponse.getValues().get("size").aggregation);
        assertEquals("variations map group by is correct", variationsMap.getGroupBy().get(0).field, variationsMapFromResponse.getGroupBy().get(0).field);
    }
}