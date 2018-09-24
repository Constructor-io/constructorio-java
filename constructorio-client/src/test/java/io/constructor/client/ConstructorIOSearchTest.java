package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOSearchTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void SearchShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResults().size(), 30);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setResultsPerPage(5);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResults().size(), 5);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithLastPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setPage(4);
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResults().size(), 14);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithSearchSuggestionsSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setSection("Search Suggestions");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResults().size(), 17);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 17);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.setGroupId("431");
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResults().size(), 30);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 95);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        SearchRequest request = new SearchRequest("peanut");
        request.getFacets().put("Brand", Arrays.asList("Back to Nature", "Barbara's"));
        SearchResponse response = constructor.search(request, userInfo);
        assertEquals("search results exist", response.getResults().size(), 2);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 2);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        SearchRequest request = new SearchRequest("peanut");
        SearchResponse response = constructor.search(request, null);
        assertEquals("search results exist", response.getResults().size(), 30);
        assertEquals("search results count as expected", response.getTotalNumberOfResults(), 104);
        assertTrue("search result id exists", response.getResultId() != null);
    }

}
