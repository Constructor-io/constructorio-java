package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.BrowseResponse;
import io.constructor.client.models.FilterFacet;
import io.constructor.client.models.FilterGroup;

public class ConstructorIOBrowseItemsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

    @Test
    public void BrowseItemsShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be equal to length of supplied ids", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        String response = constructor.browseItemsAsJSON(request, userInfo);
        assertTrue("browse items results exist", response.length() > 0);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002", "10003", "10004", "10005", "10006");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.setResultsPerPage(5);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist and count is five", response.getResponse().getResults().size() == 5);
        assertTrue("browse items total results count should be equal to length of supplied ids", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithDifferentPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002", "10003", "10004", "10005", "10006");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.setPage(2);
        request.setResultsPerPage(3);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() == ids.size() / 2);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002", "10003", "10004", "10004", "10005");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.setGroupId("All");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getFacets().put("Brand", Arrays.asList("XYZ"));
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertEquals("browse items result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 1);
        assertTrue("browse items result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size() > 0);
        assertEquals("browse items result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "item1 variation");
        assertEquals("browse items result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "20001");
        assertEquals("browse items result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://test.com/p/20001");
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertEquals("browse items result [sort options] exists", response.getResponse().getSortOptions().size(), 2);
        assertEquals("browse items result sort option [display name] exists", response.getResponse().getSortOptions().get(1).getDisplayName(), "DESC");
        assertEquals("browse items result sort option [sort by] exists", response.getResponse().getSortOptions().get(1).getSortBy(), "relevance");
        assertEquals("browse items result sort option [sort order] exists", response.getResponse().getSortOptions().get(1).getSortOrder(), "descending");
        assertEquals("browse items result sort option [status] exists", response.getResponse().getSortOptions().get(1).getStatus(), "selected");
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        List<String> ids = Arrays.asList("10001");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, null);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        List<String> ids = Arrays.asList("10001");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, null);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithMaxGroupsDepthOf3() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10002");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getFormatOptions().put("groups_max_depth", "3");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        FilterGroup secondGen = firstGen.getChildren().get(0);
        FilterGroup thirdGen = secondGen.getChildren().get(0);
        assertEquals("browse items result [root] exists", root.getGroupId(), "All");
        assertEquals("browse items result [firstGen] exists", firstGen.getGroupId(), "Brands");
        assertEquals("browse items result [secondGen] exists", secondGen.getGroupId(), "BrandX");
        assertEquals("browse items result [thirdGen] exists", thirdGen.getGroupId(), "BrandXY");
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithMaxGroupsDepthOf1() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10002");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getFormatOptions().put("groups_max_depth", "1");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        assertEquals("browse items result [root] exists", root.getGroupId(), "All");
        assertEquals("browse items result [firstGen] exists", firstGen.getGroupId(), "Brands");
        assertEquals("browse items result [firstGen] children", firstGen.getChildren().size(), 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithHiddenFields() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getHiddenFields().add("testField");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be equal to length of supplied ids", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
        assertEquals("browse items [testField] exists", response.getResponse().getResults().get(0).getData().getMetadata().get("testField"), "hiddenFieldValue");
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithHiddenFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("10001", "10002");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getHiddenFacets().add("Brand");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        FilterFacet brandFacet = response.getResponse().getFacets().stream().filter(new Predicate<FilterFacet>() {
            @Override
            public boolean test(FilterFacet f) {
                return f.getName().equals("Brand");
            }
        }).findAny().orElse(null);

        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be equal to length of supplied ids", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
        assertNotNull("browse facet [Brand] exists", brandFacet);
        assertTrue("browse facet [Brand] is hidden", brandFacet.getHidden());
    }
}
