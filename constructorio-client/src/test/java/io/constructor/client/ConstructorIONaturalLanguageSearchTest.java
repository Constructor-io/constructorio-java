package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.SearchResponse;

public class ConstructorIONaturalLanguageSearchTest {

    private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NaturalLanguageSearchShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest("show me jackets");
        SearchResponse response = constructor.naturalLanguageSearch(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 1);
        assertEquals("search results count as expected", (int) response.getResponse().getTotalNumberOfResults(), 1);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void NaturalLanguageSearchShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest("show me jackets");
        request.setResultsPerPage(5);
        SearchResponse response = constructor.naturalLanguageSearch(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 1);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 1);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void NaturalLanguageSearchShouldReturnAResultWithLastPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest("show me jackets");
        request.setPage(1);
        SearchResponse response = constructor.naturalLanguageSearch(request, userInfo);
        assertEquals("search results exist", response.getResponse().getResults().size(), 1);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 1);
        assertTrue("search result id exists", response.getResultId() != null);
    }

    @Test
    public void NaturalLanguageSearchShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest("show me jackets");
        SearchResponse response = constructor.naturalLanguageSearch(request, null);
        assertEquals("search results exist", response.getResponse().getResults().size(), 1);
        assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 1);
        assertTrue("search result id exists", response.getResultId() != null);
    }
}
