package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.constructor.client.models.AutocompleteResponse;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOAutocompleteTest {

    private String token = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void autocompleteShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("jacket");
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                1);
        assertEquals(
                "autocomplete search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                4);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        AutocompleteRequest request = new AutocompleteRequest("jacket");
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                1);
        assertEquals(
                "autocomplete search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                4);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithProductsOnly() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        AutocompleteRequest request = new AutocompleteRequest("jacket");
        request.getResultsPerSection().put("Products", 10);
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                1);
        assertEquals(
                "autocomplete search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                0);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithSearchSuggestionsOnly() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        AutocompleteRequest request = new AutocompleteRequest("jacket");
        request.getResultsPerSection().put("Search Suggestions", 10);
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                0);
        assertEquals(
                "autocomplete search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                4);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        AutocompleteRequest request = new AutocompleteRequest("jacket");
        request.getResultsPerSection().put("Search Suggestions", 10);
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                0);
        assertEquals(
                "autocomplete search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                4);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithHiddenFields() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("item1");
        request.getHiddenFields().add("testField");
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                5);
        assertEquals(
                "autocomplete result [testField] exists",
                response.getSections()
                        .get("Products")
                        .get(0)
                        .getData()
                        .getMetadata()
                        .get("testField"),
                "hiddenFieldValue");
    }

    @Test
    public void autocompleteShouldReturnAResultWithLabels() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("item");
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                5);
        assertTrue(
                "autocomplete result labels exists",
                (Boolean)
                        response.getSections()
                                .get("Products")
                                .get(0)
                                .getLabels()
                                .get("is_sponsored"));
    }

    @Test
    public void autocompleteShouldReturnAResultWithOneFilter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("item1");
        request.getFilters().put("group_id", Arrays.asList("All"));
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                5);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
        assertEquals(
                "autocomplete request [group_id] filter should match",
                ((ArrayList) ((LinkedTreeMap) response.getRequest().get("filters")).get("group_id"))
                        .get(0),
                "All");
    }

    @Test
    public void autocompleteShouldReturnAResultWithMultipleFilters() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("item1");
        request.getFilters().put("group_id", Arrays.asList("All"));
        request.getFilters().put("Brand", Arrays.asList("XYZ"));
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);
        assertEquals(
                "autocomplete product suggestions exist",
                response.getSections().get("Products").size(),
                4);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
        assertEquals(
                "autocomplete request [Brand] filter should match",
                ((ArrayList) ((LinkedTreeMap) response.getRequest().get("filters")).get("Brand"))
                        .get(0),
                "XYZ");
        assertEquals(
                "autocomplete request [group_id] filter should match",
                ((ArrayList) ((LinkedTreeMap) response.getRequest().get("filters")).get("group_id"))
                        .get(0),
                "All");
    }

    @Test
    public void AutocompleteShouldReturnAResultProvidedVariationsMapAsArray() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("Jacket");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.array);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        variationsMap.setFilterBy(
                "{\"and\":[{\"not\":{\"field\":\"data.brand\",\"value\":\"Best Brand\"}}]}");
        request.setVariationsMap(variationsMap);
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);
        ArrayList<Object> varMapObject =
                (ArrayList<Object>)
                        response.getSections().get("Products").get(0).getVariationsMap();

        assertNotNull("variations map exists", response.getRequest().get("variations_map"));
        assertEquals(
                "variations map is correct",
                variationsMap.getdType(),
                variationsMapFromResponse.getdType());
        assertTrue("result contains variations_map", varMapObject.size() >= 1);
        assertEquals(
                "variations map values is correct",
                variationsMap.getValues().get("size").aggregation,
                variationsMapFromResponse.getValues().get("size").aggregation);
        assertEquals(
                "variations map group by is correct",
                variationsMap.getGroupBy().get(0).field,
                variationsMapFromResponse.getGroupBy().get(0).field);
        assertEquals(
                "variations map filter by is correct",
                variationsMap.getFilterBy(),
                variationsMapFromResponse.getFilterBy());
    }

    @Test
    public void AutocompleteShouldReturnAResultProvidedVariationsMapAsObject() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("Jacket");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.object);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        request.setVariationsMap(variationsMap);
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);
        LinkedTreeMap<String, Object> varMapObject =
                (LinkedTreeMap<String, Object>)
                        response.getSections().get("Products").get(0).getVariationsMap();

        assertNotNull("variations map exists", response.getRequest().get("variations_map"));
        assertEquals(
                "variations map is correct",
                variationsMap.getdType(),
                variationsMapFromResponse.getdType());
        assertTrue("result contains variations_map", varMapObject.size() >= 1);
        assertEquals(
                "variations map values is correct",
                variationsMap.getValues().get("size").aggregation,
                variationsMapFromResponse.getValues().get("size").aggregation);
        assertEquals(
                "variations map group by is correct",
                variationsMap.getGroupBy().get(0).field,
                variationsMapFromResponse.getGroupBy().get(0).field);
    }
}
