package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SearchRequestTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullQueryShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new SearchRequest(null);
    }

    @Test
    public void newShouldReturnSearchRequest() throws Exception {
        String query = "peanut";
        SearchRequest request = new SearchRequest(query);
        assertEquals(request.getQuery(), query);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        String query = "peanut";
        SearchRequest request = new SearchRequest(query);
        assertEquals(request.getQuery(), query);
        assertEquals(request.getSection(), "Products");
        assertEquals(request.getPage(), 1);
        assertEquals(request.getResultsPerPage(), 30);
        assertEquals(request.getGroupId(), null);
        assertEquals(request.getFacets().size(), 0);
        assertNull(request.getSortBy());
        assertTrue(request.getSortAscending());
        assertNull(request.getCollectionId());
    }

    @Test
    public void settersShouldSet() throws Exception {
        String query = "peanut";
        SearchRequest request = new SearchRequest(query);
        Map<String, List<String>> facets = new HashMap<String, List<String>>();
        facets.put("Flavors", Arrays.asList("Honey Roasted", "Dry Roasted", "Unsalted"));
        
        request.setQuery("airline tickets");
        request.setSection("Search Suggestions");
        request.setPage(3);
        request.setResultsPerPage(50);
        request.setGroupId("gr-1337");
        request.setFacets(facets);
        request.setSortBy("smooth-to-chunky");
        request.setSortAscending(false);
        request.setCollectionId("last minute getaways");

        assertEquals(request.getQuery(), "airline tickets");
        assertEquals(request.getSection(), "Search Suggestions");
        assertEquals(request.getPage(), 3);
        assertEquals(request.getResultsPerPage(), 50);
        assertEquals(request.getGroupId(), "gr-1337");
        assertEquals(request.getFacets(), facets);
        assertEquals(request.getSortBy(), "smooth-to-chunky");
        assertEquals(request.getSortAscending(), false);
        assertEquals(request.getCollectionId(), "last minute getaways");
    }
}
