package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.BrowseResponse;

public class ConstructorIOBrowseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createBrowseResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse facets exist", response.getResponse().getFacets().size(), 7);
        assertEquals("browse facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("browse facet [Color] exists", response.getResponse().getFacets().get(1).getName(), "Color");
        assertEquals("browse facet [Gender] exists", response.getResponse().getFacets().get(2).getName(), "Gender");
        assertEquals("browse facet [Made In Italy] exists", response.getResponse().getFacets().get(3).getName(), "Made In Italy");
        assertEquals("browse groups exist", response.getResponse().getGroups().size(), 1);
        assertEquals("browse group [All] exists", response.getResponse().getGroups().get(0).getDisplayName(), "All");
        assertEquals("browse group [All] children exist", response.getResponse().getGroups().get(0).getChildren().size(), 5);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 5);
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithVariations() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse facets exists", response.getResponse().getFacets().size(), 7);
        assertEquals("browse groups exists", response.getResponse().getGroups().size(), 1);
        assertEquals("browse results exists", response.getResponse().getResults().size(), 5);
        assertEquals("browse result [id] exists", response.getResponse().getResults().get(0).getData().getId(), "aspesi-coat-I502997385098-blue");
        assertEquals("browse result [variation id] exists", response.getResponse().getResults().get(0).getData().getVariationId(), "M0E20000000ECTT");
        assertEquals("browse result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 8);
        assertEquals("browse result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 8);
        assertEquals("browse result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "Coat Aspesi blue");
        assertEquals("browse result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "M0E20000000ECTT");
        assertEquals("browse result variation [image url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getImageUrl(), "https://s3-eu-west-1.amazonaws.com/commercetools-maximilian/products/081200_1_large.jpg");
        assertEquals("browse result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://demo.commercetools.com/en/aspesi-coat-I502997385098-blue.html");
        assertTrue("browse result variation [metadata] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getMetadata() != null);
        assertEquals("browse result variation metadata [product type] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getMetadata().get("productType"), "Outerwear");
        assertEquals("browse result variation metadata [price] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getMetadata().get("price"), "536.25");
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithSortOptions() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse result [sort options] exists", response.getResponse().getSortOptions().size(), 4);
        assertEquals("browse result sort option [display name] exists", response.getResponse().getSortOptions().get(0).getDisplayName(), "Relevance");
        assertEquals("browse result sort option [sort by] exists", response.getResponse().getSortOptions().get(0).getSortBy(), "relevance");
        assertEquals("browse result sort option [sort order] exists", response.getResponse().getSortOptions().get(0).getSortOrder(), "descending");
        assertEquals("browse result sort option [status] exists", response.getResponse().getSortOptions().get(0).getStatus(), "selected");
        assertEquals("total number of results", (int) response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertTrue("browse results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", response.getResultId() != null);
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
}