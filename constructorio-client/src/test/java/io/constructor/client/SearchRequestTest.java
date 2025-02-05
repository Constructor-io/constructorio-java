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

    @Rule public ExpectedException thrown = ExpectedException.none();

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
        assertNull(request.getPreFilterExpression());
        assertNull(request.getQsParam());
        assertNull(request.getNow());
        assertEquals(request.getOffset(), 0);
    }

    @Test
    public void settersShouldSet() throws Exception {
        String query = "peanut";
        SearchRequest request = new SearchRequest(query);
        Map<String, List<String>> facets = new HashMap<String, List<String>>();
        facets.put("Flavors", Arrays.asList("Honey Roasted", "Dry Roasted", "Unsalted"));
        Map<String, String> formatOptions = new HashMap<String, String>();
        formatOptions.put("groups_start", "top");
        List<String> hiddenFields = Arrays.asList("hiddenField1", "hiddenField2");
        List<String> hiddenFacets = Arrays.asList("hiddenFacet1", "hiddenFacet2");
        String preFilterExpression =
                "{\"or\":[{\"and\":[{\"name\":\"group_id\",\"value\":\"electronics-group-id\"},{\"name\":\"Price\",\"range\":[\"-inf\",200]}]},{\"and\":[{\"name\":\"Type\",\"value\":\"Laptop\"},{\"not\":{\"name\":\"Price\",\"range\":[800,\"inf\"]}}]}]}";
        String qsParam =
                "{\"num_results_per_page\":\"10\",\"filters\":{\"keywords\":[\"battery-powered\"]}}";
        String now = "1659049486";
        int offset = 2;
        Map<String, String> filterMatchTypes = new HashMap<String, String>();
        filterMatchTypes.put("Color", "all");

        request.setQuery("airline tickets");
        request.setSection("Search Suggestions");
        request.setPage(3);
        request.setResultsPerPage(50);
        request.setGroupId("gr-1337");
        request.setFacets(facets);
        request.setSortBy("smooth-to-chunky");
        request.setSortAscending(false);
        request.setCollectionId("last minute getaways");
        request.setFormatOptions(formatOptions);
        request.setHiddenFields(hiddenFields);
        request.setHiddenFacets(hiddenFacets);
        request.setPreFilterExpression(preFilterExpression);
        request.setQsParam(qsParam);
        request.setNow(now);
        request.setOffset(offset);
        request.setFilterMatchTypes(filterMatchTypes);

        assertEquals(request.getQuery(), "airline tickets");
        assertEquals(request.getSection(), "Search Suggestions");
        assertEquals(request.getPage(), 3);
        assertEquals(request.getResultsPerPage(), 50);
        assertEquals(request.getGroupId(), "gr-1337");
        assertEquals(request.getFacets(), facets);
        assertEquals(request.getSortBy(), "smooth-to-chunky");
        assertEquals(request.getSortAscending(), false);
        assertEquals(request.getCollectionId(), "last minute getaways");
        assertEquals(request.getFormatOptions(), formatOptions);
        assertEquals(request.getHiddenFields(), hiddenFields);
        assertEquals(request.getHiddenFacets(), hiddenFacets);
        assertEquals(request.getPreFilterExpression(), preFilterExpression);
        assertEquals(request.getQsParam(), qsParam);
        assertEquals(request.getNow(), now);
        assertEquals(request.getOffset(), offset);
        assertEquals(request.getFilterMatchTypes(), filterMatchTypes);
    }
}
