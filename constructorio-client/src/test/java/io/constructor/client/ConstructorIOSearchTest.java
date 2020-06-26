package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.SearchResponse;

public class ConstructorIOSearchTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createSearchResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        SearchResponse response = ConstructorIO.createSearchResponse(string);
        assertEquals("search facets exist", response.getResponse().getFacets().size(), 4);
        assertEquals("search facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("search facet [Claims] exists", response.getResponse().getFacets().get(1).getName(), "Claims");
        assertEquals("search facet [Price] exists", response.getResponse().getFacets().get(2).getName(), "Price");
        assertEquals("search facet [Rating] exists", response.getResponse().getFacets().get(3).getName(), "Rating");
        assertEquals("search groups exist", response.getResponse().getGroups().size(), 3);
        assertEquals("search group [Dairy] exists", response.getResponse().getGroups().get(0).getDisplayName(), "Dairy");
        assertEquals("search group [Dairy] children exist", response.getResponse().getGroups().get(0).getChildren().size(), 3);
        assertEquals("search results exist", response.getResponse().getResults().size(), 24);
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void createSearchResponseShouldReturnAResultWithVariations() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        SearchResponse response = ConstructorIO.createSearchResponse(string);
        assertEquals("search facets exists", response.getResponse().getFacets().size(), 7);
        assertEquals("search groups exists", response.getResponse().getGroups().size(), 1);
        assertEquals("search results exists", response.getResponse().getResults().size(), 5);
        assertEquals("search result [id] exists", response.getResponse().getResults().get(0).getData().getId(), "aspesi-coat-I502997385098-blue");
        assertEquals("search result [variation id] exists", response.getResponse().getResults().get(0).getData().getVariationId(), "M0E20000000ECTT");
        assertEquals("search result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 8);
        assertEquals("search result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 8);
        assertEquals("search result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "Coat Aspesi blue");
        assertEquals("search result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "M0E20000000ECTT");
        assertEquals("search result variation [image] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getImageUrl(), "https://s3-eu-west-1.amazonaws.com/commercetools-maximilian/products/081200_1_large.jpg");
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void createSearchResponseShouldReturnAResultWithSortOptions() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        SearchResponse response = ConstructorIO.createSearchResponse(string);
        assertEquals("search result [sort options] exists", response.getResponse().getSortOptions().size(), 4);
        assertEquals("search result sort option [display name] exists", response.getResponse().getSortOptions().get(0).getDisplayName(), "Relevance");
        assertEquals("search result sort option [sort by] exists", response.getResponse().getSortOptions().get(0).getSortBy(), "relevance");
        assertEquals("search result sort option [sort order] exists", response.getResponse().getSortOptions().get(0).getSortOrder(), "descending");
        assertEquals("search result sort option [status] exists", response.getResponse().getSortOptions().get(0).getStatus(), "selected");
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setResultsPerPage(5);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 5);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithLastPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setPage(4);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 14);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithSearchSuggestionsSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setSection("Search Suggestions");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 17);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 17);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setGroupId("431");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 95);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.getFacets().put("Brand", Arrays.asList("Back to Nature", "Barbara's"));
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 2);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 2);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 13);
        assertEquals("search result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 8);
        assertEquals("search result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "Bully – Leather Jacket");
        assertEquals("search result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "M0E20000000FBLO");
        assertEquals("search result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://demo.commercetools.com/en/bully-leather-jacket-251-grey.html");
        assertEquals("search results count as expected", (int) response.getResponse().getTotalNumberOfResults(), 261);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search result [sort options] exists", response.getResponse().getSortOptions().size(), 1);
        assertEquals("search result sort option [display name] exists", response.getResponse().getSortOptions().get(0).getDisplayName(), "Relevance");
        assertEquals("search result sort option [sort by] exists", response.getResponse().getSortOptions().get(0).getSortBy(), "relevance");
        assertEquals("search result sort option [sort order] exists", response.getResponse().getSortOptions().get(0).getSortOrder(), "descending");
        assertEquals("search result sort option [status] exists", response.getResponse().getSortOptions().get(0).getStatus(), "selected");
        assertEquals("search results count as expected", (int) response.getResponse().getTotalNumberOfResults(), 261);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        SearchRequest request = new SearchRequest("peanut");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }


    @Test
    public void SearchShouldReturnAResultWithCollectionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_aXLmVpkVp4BX21Sw", true, "betaac.cnstrc.com");
        SearchRequest request = new SearchRequest("bananas");
        request.setCollectionId("fresh-deals");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 33);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, "betaac.cnstrc.com");
        constructor.setApiKey("key_aXLmVpkVp4BX21Sw");
        SearchRequest request = new SearchRequest("bananas");
        request.setCollectionId("fresh-deals");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 33);
        assertTrue("search result id exists", response.getResultId() != null);
    }
}