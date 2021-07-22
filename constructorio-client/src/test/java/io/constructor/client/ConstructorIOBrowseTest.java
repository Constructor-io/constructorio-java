package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.BrowseResponse;
import io.constructor.client.models.FilterGroup;

public class ConstructorIOBrowseTest {

    private String apiKey = System.getenv("TEST_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void BrowseShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse collection does not exist", response.getResponse().getCollection() == null);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        String response = constructor.browseAsJSON(request, userInfo);
        assertTrue("browse results exist", response.length() > 0);
    }

    @Test
    public void BrowseShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        request.setResultsPerPage(5);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithDifferentPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        request.setPage(1);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        request.setGroupId("All");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        request.getFacets().put("Brand", Arrays.asList("XYZ"));
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertEquals("browse result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 1);
        assertEquals("browse result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 2);
        assertEquals("browse result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "item1 variation");
        assertEquals("browse result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "20001");
        assertEquals("browse result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://test.com/p/20001");
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse result [sort options] exists", response.getResponse().getSortOptions().size(), 2);
        assertEquals("browse result sort option [display name] exists", response.getResponse().getSortOptions().get(1).getDisplayName(), "DESC");
        assertEquals("browse result sort option [sort by] exists", response.getResponse().getSortOptions().get(1).getSortBy(), "relevance");
        assertEquals("browse result sort option [sort order] exists", response.getResponse().getSortOptions().get(1).getSortOrder(), "descending");
        assertEquals("browse result sort option [status] exists", response.getResponse().getSortOptions().get(1).getStatus(), "selected");
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        BrowseRequest request = new BrowseRequest("Color", "blue");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithCollectionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        BrowseRequest request = new BrowseRequest("collection_id", "test");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse collection exists", response.getResponse().getCollection() != null);
        assertEquals("browse collection id exists", response.getResponse().getCollection().getId(), "test");
        assertEquals("browse collection name exists", response.getResponse().getCollection().getDisplayName(), "test");
        assertTrue("browse collection data does not exist", response.getResponse().getCollection().getData() == null);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        BrowseRequest request = new BrowseRequest("Color", "blue");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
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
}