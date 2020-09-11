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

public class BrowseRequestTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullQueryShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new BrowseRequest(null, null);
    }

    @Test
    public void newShouldReturnBrowseRequest() throws Exception {
        String filterName = "SnackType";
        String filterValue = "Nuts";
        BrowseRequest request = new BrowseRequest(filterName, filterValue);
        assertEquals(request.getFilterName(), filterName);
        assertEquals(request.getFilterValue(), filterValue);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        String filterName = "SnackType";
        String filterValue = "Nuts";
        BrowseRequest request = new BrowseRequest(filterName, filterValue);
        assertEquals(request.getFilterName(), filterName);
        assertEquals(request.getFilterValue(), filterValue);
        assertEquals(request.getSection(), "Products");
        assertEquals(request.getPage(), 1);
        assertEquals(request.getResultsPerPage(), 30);
        assertEquals(request.getGroupId(), null);
        assertEquals(request.getFacets().size(), 0);
        assertNull(request.getSortBy());
        assertTrue(request.getSortAscending());
    }

    @Test
    public void settersShouldSet() throws Exception {
        String filterName = "SnackType";
        String filterValue = "Nuts";
        BrowseRequest request = new BrowseRequest(filterName, filterValue);
        Map<String, List<String>> facets = new HashMap<String, List<String>>();
        facets.put("Flavors", Arrays.asList("Honey Roasted", "Dry Roasted", "Unsalted"));
        
        request.setFilterName("VacationType");
        request.setFilterValue("Air Travel");
        request.setSection("Browse Suggestions");
        request.setPage(3);
        request.setResultsPerPage(50);
        request.setGroupId("gr-1337");
        request.setFacets(facets);
        request.setSortBy("smooth-to-chunky");
        request.setSortAscending(false);

        assertEquals(request.getFilterName(), "VacationType");
        assertEquals(request.getFilterValue(), "Air Travel");
        assertEquals(request.getSection(), "Browse Suggestions");
        assertEquals(request.getPage(), 3);
        assertEquals(request.getResultsPerPage(), 50);
        assertEquals(request.getGroupId(), "gr-1337");
        assertEquals(request.getFacets(), facets);
        assertEquals(request.getSortBy(), "smooth-to-chunky");
        assertEquals(request.getSortAscending(), false);
    }
}
