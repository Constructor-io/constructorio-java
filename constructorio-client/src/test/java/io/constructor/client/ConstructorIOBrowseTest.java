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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void BrowseShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse collection data does not exist", response.getResponse().getCollection() == null);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        String response = constructor.browseAsJSON(request, userInfo);
        assertTrue("browse results exist", response.length() > 0);
    }

    @Test
    public void BrowseShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setResultsPerPage(5);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithDifferentPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setPage(5);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setGroupId("sale");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.getFacets().put("Brand", Arrays.asList("Frankie", "Cycle"));
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertEquals("browse result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 25);
        assertEquals("browse result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 8);
        assertEquals("browse result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "Sneakers ”H222” Hogan light blue");
        assertEquals("browse result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "M0E20000000DVZF");
        assertEquals("browse result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://demo.commercetools.com/en/hogan-sneakers-38714173-lightblue.html");
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse result [sort options] exists", response.getResponse().getSortOptions().size(), 1);
        assertEquals("browse result sort option [display name] exists", response.getResponse().getSortOptions().get(0).getDisplayName(), "Relevance");
        assertEquals("browse result sort option [sort by] exists", response.getResponse().getSortOptions().get(0).getSortBy(), "relevance");
        assertEquals("browse result sort option [sort order] exists", response.getResponse().getSortOptions().get(0).getSortOrder(), "descending");
        assertEquals("browse result sort option [status] exists", response.getResponse().getSortOptions().get(0).getStatus(), "selected");
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithCollectionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_aXLmVpkVp4BX21Sw", true, "betaac.cnstrc.com");
        BrowseRequest request = new BrowseRequest("collection_id", "fresh-deals");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse collection data exists", response.getResponse().getCollection() != null);
        assertEquals("browse collection id exists", response.getResponse().getCollection().getId(), "fresh-deals");
        assertEquals("browse collection name exists", response.getResponse().getCollection().getDisplayName(), "Fresh deals");
        assertTrue("browse collection inner data does not exist", response.getResponse().getCollection().getData() == null);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, "betaac.cnstrc.com");
        constructor.setApiKey("key_aXLmVpkVp4BX21Sw");
        BrowseRequest request = new BrowseRequest("collection_id", "fresh-deals");
        BrowseResponse response = constructor.browse(request, null);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithMaxGroupsDepthOf3() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.getFormatOptions().put("groups_max_depth", "3");
        BrowseResponse response = constructor.browse(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        FilterGroup secondGen = firstGen.getChildren().get(0);
        FilterGroup thirdGen = secondGen.getChildren().get(0);
        assertEquals("browse result [root] exists", root.getGroupId(), "all");
        assertEquals("browse result [firstGen] exists", firstGen.getGroupId(), "sale");
        assertEquals("browse result [secondGen] exists", secondGen.getGroupId(), "sale|men");
        assertEquals("browse result [thirdGen] exists", thirdGen.getGroupId(), "sale|men|clothing");
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithMaxGroupsDepthOf1() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.getFormatOptions().put("groups_max_depth", "1");
        BrowseResponse response = constructor.browse(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        assertEquals("browse result [root] exists", root.getGroupId(), "all");
        assertEquals("browse result [firstGen] exists", firstGen.getGroupId(), "sale");
        assertEquals("browse result [firstGen] children", firstGen.getChildren().size(), 0);
        assertTrue("browse result id exists", response.getResultId() != null);
    }
}