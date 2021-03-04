package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.FilterGroup;
import io.constructor.client.models.SearchResponse;

public class ConstructorIOSearchTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void SearchAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        String response = constructor.searchAsJSON(request, userInfo);
        assertTrue("search results exist", response.length() > 1);
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
        assertTrue("search total results count should be greater than 0", (int) response.getResponse().getTotalNumberOfResults() > 0);
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
        assertTrue("search total results count should be greater than 0", (int) response.getResponse().getTotalNumberOfResults() > 0);
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

    @Test
    public void SearchShouldReturnAResultWithRedirect() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
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
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        request.getFormatOptions().put("groups_max_depth", "3");
        SearchResponse response = constructor.search(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        FilterGroup secondGen = firstGen.getChildren().get(0);
        FilterGroup thirdGen = secondGen.getChildren().get(0);
        assertEquals("search result [root] exists", root.getGroupId(), "all");
        assertEquals("search result [firstGen] exists", firstGen.getGroupId(), "women");
        assertEquals("search result [secondGen] exists", secondGen.getGroupId(), "women|clothing");
        assertEquals("search result [thirdGen] exists", thirdGen.getGroupId(), "women|clothing|jackets");
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithMaxGroupsDepthOf1() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("jacket");
        request.getFormatOptions().put("groups_max_depth", "1");
        SearchResponse response = constructor.search(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        assertEquals("search result [root] exists", root.getGroupId(), "all");
        assertEquals("search result [firstGen] exists", firstGen.getGroupId(), "women");
        assertEquals("search result [firstGen] children", firstGen.getChildren().size(), 0);
        assertTrue("search result id exists", response.getResultId() != null);
    }
}