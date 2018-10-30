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
        assertEquals("search facets exist", response.getResponse().getFacets().size(), 2);
        assertEquals("search facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("search facet [Claims] exists", response.getResponse().getFacets().get(1).getName(), "Claims");
        assertEquals("search groups exist", response.getResponse().getGroups().size(), 3);
        assertEquals("search group [Dairy] exists", response.getResponse().getGroups().get(0).getDisplayName(), "Dairy");
        assertEquals("search group [Dairy] children exist", response.getResponse().getGroups().get(0).getChildren().size(), 3);
        assertEquals("search results exist", response.getResponse().getResults().size(), 24);
        assertEquals("total number of results", response.getResponse().getTotalNumberOfResults(), 198);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie", "user-id-123");
        SearchRequest request = new SearchRequest("peanut");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie", "user-id-123");
        SearchRequest request = new SearchRequest("peanut");
        request.setResultsPerPage(5);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 5);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithLastPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie", "user-id-123");
        SearchRequest request = new SearchRequest("peanut");
        request.setPage(4);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 14);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithSearchSuggestionsSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie", "user-id-123");
        SearchRequest request = new SearchRequest("peanut");
        request.setSection("Search Suggestions");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 17);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 17);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie", "user-id-123");
        SearchRequest request = new SearchRequest("peanut");
        request.setGroupId("431");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 95);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie", "user-id-123");
        SearchRequest request = new SearchRequest("peanut");
        request.getFacets().put("Brand", Arrays.asList("Back to Nature", "Barbara's"));
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 2);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 2);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        SearchRequest request = new SearchRequest("peanut");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 30);
        assertEquals("search results count as expected", response.getResponse().getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

}