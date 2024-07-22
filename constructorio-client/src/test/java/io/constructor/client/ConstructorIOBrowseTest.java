package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.constructor.client.models.BrowseResponse;
import io.constructor.client.models.FilterFacet;
import io.constructor.client.models.FilterGroup;
import io.constructor.client.models.Result;
import io.constructor.client.models.ResultGroup;
import io.constructor.client.models.ResultGroupPathListItem;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOBrowseTest {

    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");
    private String apiToken = System.getenv("TEST_API_TOKEN");

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void BrowseShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue(
                "browse collection does not exist", response.getResponse().getCollection() == null);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWIthResultSources() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals(
                "browse result result sources exists",
                (int) response.getResponse().getResultSources().getTokenMatch().getCount(),
                1);
        assertEquals(
                "browse result result sources exists",
                (int) response.getResponse().getResultSources().getEmbeddingsMatch().getCount(),
                0);
    }

    @Test
    public void BrowseAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        String response = constructor.browseAsJSON(request, userInfo);
        assertTrue("browse results exist", response.length() > 0);
    }

    @Test
    public void BrowseShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setResultsPerPage(5);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithDifferentPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setPage(1);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setGroupId("All");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.getFacets().put("Brand", Arrays.asList("XYZ"));
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertEquals(
                "browse result [variations] exists",
                response.getResponse().getResults().get(0).getVariations().size(),
                1);
        assertTrue(
                "browse result variation [facets] exists",
                response.getResponse()
                                .getResults()
                                .get(0)
                                .getVariations()
                                .get(0)
                                .getData()
                                .getFacets()
                                .size()
                        > 0);
        assertEquals(
                "browse result variation [value] exists",
                response.getResponse().getResults().get(0).getVariations().get(0).getValue(),
                "item1 variation");
        assertEquals(
                "browse result variation [variation id] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getVariationId(),
                "20001");
        assertEquals(
                "browse result variation [url] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getUrl(),
                "https://test.com/p/20001");
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals(
                "browse result [sort options] exists",
                response.getResponse().getSortOptions().size(),
                2);
        assertEquals(
                "browse result sort option [display name] exists",
                response.getResponse().getSortOptions().get(1).getDisplayName(),
                "DESC");
        assertEquals(
                "browse result sort option [sort by] exists",
                response.getResponse().getSortOptions().get(1).getSortBy(),
                "relevance");
        assertEquals(
                "browse result sort option [sort order] exists",
                response.getResponse().getSortOptions().get(1).getSortOrder(),
                "descending");
        assertEquals(
                "browse result sort option [status] exists",
                response.getResponse().getSortOptions().get(1).getStatus(),
                "selected");
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithCollectionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        BrowseRequest request = new BrowseRequest("collection_id", "test");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse collection exists", response.getResponse().getCollection() != null);
        assertEquals(
                "browse collection id exists",
                response.getResponse().getCollection().getId(),
                "test");
        assertEquals(
                "browse collection name exists",
                response.getResponse().getCollection().getDisplayName(),
                "test");
        assertTrue(
                "browse collection data does not exist",
                response.getResponse().getCollection().getData() == null);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithMaxGroupsDepthOf3() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "yellow");
        request.getFormatOptions().put("groups_max_depth", "3");
        BrowseResponse response = constructor.browse(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        FilterGroup secondGen = firstGen.getChildren().get(0);
        FilterGroup thirdGen = secondGen.getChildren().get(0);
        assertEquals("browse result [root] exists", root.getGroupId(), "All");
        assertEquals("browse result [firstGen] exists", firstGen.getGroupId(), "Brands");
        assertEquals("browse result [secondGen] exists", secondGen.getGroupId(), "BrandX");
        assertEquals("browse result [thirdGen] exists", thirdGen.getGroupId(), "BrandXY");
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithMaxGroupsDepthOf1() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "yellow");
        request.getFormatOptions().put("groups_max_depth", "1");
        BrowseResponse response = constructor.browse(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        assertEquals("browse result [root] exists", root.getGroupId(), "All");
        assertEquals("browse result [firstGen] exists", firstGen.getGroupId(), "Brands");
        assertEquals("browse result [firstGen] children", firstGen.getChildren().size(), 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithHiddenFields() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        request.getHiddenFields().add("testField");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 4);
        assertEquals(
                "browse result [testField] exists",
                response.getResponse().getResults().get(0).getData().getMetadata().get("testField"),
                "hiddenFieldValue");
    }

    @Test
    public void BrowseShouldReturnAResultWithLabels() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        request.getHiddenFields().add("testField");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 4);
        assertTrue(
                "browse result labels exist",
                (Boolean)response.getResponse().getResults().get(0).getLabels().get("is_sponsored"));
    }

    @Test
    public void BrowseShouldReturnAResultWithHiddenFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        request.getHiddenFacets().add("Brand");
        BrowseResponse response = constructor.browse(request, userInfo);
        FilterFacet brandFacet =
                response.getResponse().getFacets().stream()
                        .filter(
                                new Predicate<FilterFacet>() {
                                    @Override
                                    public boolean test(FilterFacet f) {
                                        return f.getName().equals("Brand");
                                    }
                                })
                        .findAny()
                        .orElse(null);

        assertEquals("browse results exist", response.getResponse().getResults().size(), 4);
        assertNotNull("browse facet [Brand] exists", brandFacet);
        assertTrue("browse facet [Brand] is hidden", brandFacet.getHidden());
    }

    @Test
    public void BrowseShouldReturnAResultThatHasGroupsWithDataObject() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "yellow");
        BrowseResponse response = constructor.browse(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);

        assertEquals("browse result [root] exists", root.getGroupId(), "All");
        assertTrue("browse result [root] has data field", root.getData() instanceof Map);
        assertEquals(
                "browse result [root] has data field and it's empty", root.getData().size(), 0);
    }

    @Test
    public void BrowseShouldReturnAResultProvidedVariationsMapAsArray() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.array);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        variationsMap.setFilterBy(
                "{\"and\":[{\"not\":{\"field\":\"data.brand\",\"value\":\"Best Brand\"}}]}");
        request.setVariationsMap(variationsMap);
        BrowseResponse response = constructor.browse(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);
        ArrayList<Object> varMapObject =
                (ArrayList<Object>) response.getResponse().getResults().get(0).getVariationsMap();

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
    public void BrowseShouldReturnAResultProvidedVariationsMapAsObject() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.setdType(VariationsMap.Dtypes.object);
        variationsMap.addGroupByRule("variation", "data.variation_id");
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        request.setVariationsMap(variationsMap);
        BrowseResponse response = constructor.browse(request, userInfo);

        String json = new Gson().toJson(response.getRequest().get("variations_map"));
        VariationsMap variationsMapFromResponse = new Gson().fromJson(json, VariationsMap.class);
        LinkedTreeMap<String, Object> varMapObject =
                (LinkedTreeMap<String, Object>)
                        response.getResponse().getResults().get(0).getVariationsMap();

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

    @Test
    public void BrowseShouldReturnAResultWithPreFilterExpression() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        String preFilterExpression =
                "{\"or\":[{\"and\":[{\"name\":\"group_id\",\"value\":\"electronics-group-id\"},{\"name\":\"Price\",\"range\":[\"-inf\",200.0]}]},{\"and\":[{\"name\":\"Type\",\"value\":\"Laptop\"},{\"not\":{\"name\":\"Price\",\"range\":[800.0,\"inf\"]}}]}]}";
        request.setPreFilterExpression(preFilterExpression);

        BrowseResponse response = constructor.browse(request, userInfo);
        String preFilterExpressionFromRequestJsonString =
                new Gson().toJson(response.getRequest().get("pre_filter_expression"));

        assertTrue("browse results exist", response.getResponse().getResults().size() >= 0);
        assertNotNull(
                "pre_filter_expression exists", response.getRequest().get("pre_filter_expression"));
        assertEquals(preFilterExpression, preFilterExpressionFromRequestJsonString);
    }

    @Test
    public void BrowseShouldReturnAResultWithQsParam() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        String qsParam = "{\"filters\":{\"Color\":[\"green\"]}}";
        request.setQsParam(qsParam);

        BrowseResponse response = constructor.browse(request, userInfo);
        Map<String, List<String>> filtersFromRequest = (Map) response.getRequest().get("filters");

        assertTrue("browse results exist", response.getResponse().getResults().size() >= 0);
        assertEquals(Arrays.asList("green"), filtersFromRequest.get("Color"));
    }

    @Test
    public void BrowseShouldReturnAResultWithNow() throws Exception {
        ConstructorIO constructor = new ConstructorIO(apiToken, apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        String now = "1659053211";
        request.setNow(now);

        BrowseResponse response = constructor.browse(request, userInfo);

        assertTrue("browse results exist", response.getResponse().getResults().size() >= 0);
        assertNotNull("now exists", response.getRequest().get("now"));
        assertEquals(now, new DecimalFormat("#").format(response.getRequest().get("now")));
    }

    @Test
    public void BrowseShouldReturnAResultWithOffset() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        int offset = 5;
        request.setOffset(offset);

        BrowseResponse response = constructor.browse(request, userInfo);

        assertTrue("browse results exist", response.getResponse().getResults().size() >= 0);
        assertNotNull("offset exists", response.getRequest().get("offset"));
        assertEquals(
                String.valueOf(offset),
                new DecimalFormat("#").format(response.getRequest().get("offset")));
    }

    @Test
    public void BrowseShouldReturnErrorWithPageAndOffset() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 400] offset, page are mutually exclusive");
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Brand", "XYZ");
        request.setPage(2);
        request.setOffset(5);
        constructor.browse(request, userInfo);
    }

    @Test
    public void BrowseShouldReturnAResultWithRefinedContent() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("group_id", "BrandA");
        BrowseResponse response = constructor.browse(request, userInfo);

        assertEquals(
                "browse result [refined content] exists",
                response.getResponse().getRefinedContent().size(),
                3);
        assertTrue(
                "browse result refined content [data] exists",
                response.getResponse().getRefinedContent().get(0).getData() != null);
        assertEquals(
                "browse result refined content data [body] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("body"),
                "Content 1 Body");
        assertEquals(
                "browse result refined content data [header] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("header"),
                "Content 1 Header");
        assertEquals(
                "browse result refined content data [altText] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("altText"),
                "Content 1 desktop alt text");
        assertEquals(
                "browse result refined content data [ctaLink] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("ctaLink"),
                "https://constructor.io/wp-content/uploads/2022/09/groceryshop-2022-r2.png");
        assertEquals(
                "browse result refined content data [ctaText] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("ctaText"),
                "Content 1 CTA Button");
        assertEquals(
                "browse result refined content data [assetUrl] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("assetUrl"),
                "https://constructor.io/wp-content/uploads/2022/09/groceryshop-2022-r2.png");
        assertEquals(
                "browse result refined content data [mobileAssetUrl] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("mobileAssetUrl"),
                "https://constructor.io/wp-content/uploads/2022/09/groceryshop-2022-r2.png");
        assertEquals(
                "browse result refined content data [mobileAssetAltText] exists",
                response.getResponse()
                        .getRefinedContent()
                        .get(0)
                        .getData()
                        .get("mobileAssetAltText"),
                "Content 1 mobile alt text");
        assertTrue("browse result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithArbitraryRefinedContentData() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("group_id", "BrandA");
        BrowseResponse response = constructor.browse(request, userInfo);

        assertEquals(
                "browse result [refined content] exists",
                response.getResponse().getRefinedContent().size(),
                3);
        assertTrue(
                "browse result refined content [data] exists",
                response.getResponse().getRefinedContent().get(0).getData() != null);
        assertEquals(
                "browse result refined content data [tag-1] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("tag-1"),
                "tag-1-value");
        assertEquals(
                "browse result refined content data [tag-2] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("tag-2"),
                "tag-2-value");
        assertTrue(
                "browse result refined content data [arbitraryDataObject] exists",
                response.getResponse()
                                .getRefinedContent()
                                .get(0)
                                .getData()
                                .get("arbitraryDataObject")
                        != null);
        assertTrue("browse result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
    }

    @Test
    public void BrowseShouldReturnItemLevelGroupsObject() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("group_id", "BrandA");
        BrowseResponse response = constructor.browse(request, userInfo);

        List<Result> resultsList = response.getResponse().getResults();
        assertTrue("search results exist", resultsList.size() > 0);

        List<ResultGroup> groupsList = resultsList.get(0).getData().getGroups();
        assertTrue("groups at the item level exist", groupsList.size() > 0);

        ResultGroup group = groupsList.get(0);
        assertEquals("group displayName matches", group.getDisplayName(), "BrandA");
        assertEquals("group groupId matches", group.getGroupId(), "BrandA");
        assertEquals("group path matches", group.getPath(), "/All/Brands");

        List<ResultGroupPathListItem> groupPathList = group.getPathList();
        assertTrue("pathList exists", groupPathList.size() > 0);
        assertEquals(
                "first pathListItem displayName matches",
                groupPathList.get(0).getDisplayName(),
                "All");
        assertEquals("first pathListItem id matches", groupPathList.get(0).getId(), "All");
    }
}
