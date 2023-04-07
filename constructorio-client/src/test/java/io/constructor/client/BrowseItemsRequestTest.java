package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BrowseItemsRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullQueryShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new BrowseItemsRequest(null);
    }

    @Test
    public void newShouldReturnBrowseRequest() throws Exception {
        List<String> ids =
                Arrays.asList("be73c016a2959243", "a6c7904fab526c23", "6a1d33f1dd13cc6b");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        assertEquals(request.getIds(), ids);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        List<String> ids =
                Arrays.asList("be73c016a2959243", "a6c7904fab526c23", "6a1d33f1dd13cc6b");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        assertEquals(request.getIds(), ids);
        assertEquals(request.getSection(), "Products");
    }

    @Test
    public void settersShouldSet() throws Exception {
        List<String> ids =
                Arrays.asList("be73c016a2959243", "a6c7904fab526c23", "6a1d33f1dd13cc6b");
        List<String> newIds = Arrays.asList("19a74297eec15244");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        Map<String, List<String>> facets = new HashMap<String, List<String>>();
        facets.put("Flavors", Arrays.asList("Honey Roasted", "Dry Roasted", "Unsalted"));
        Map<String, String> formatOptions = new HashMap<String, String>();
        formatOptions.put("groups_start", "top");
        List<String> hiddenFields = Arrays.asList("hiddenField1", "hiddenField2");
        List<String> hiddenFacets = Arrays.asList("hiddenFacet1", "hiddenFacet2");

        request.setIds(newIds);
        request.setSection("Browse Suggestions");
        request.setPage(3);
        request.setResultsPerPage(50);
        request.setGroupId("gr-1337");
        request.setFacets(facets);
        request.setSortBy("smooth-to-chunky");
        request.setSortAscending(false);
        request.setFormatOptions(formatOptions);
        request.setHiddenFields(hiddenFields);
        request.setHiddenFacets(hiddenFacets);

        assertEquals(request.getIds(), newIds);
        assertEquals(request.getSection(), "Browse Suggestions");
        assertEquals(request.getPage(), 3);
        assertEquals(request.getResultsPerPage(), 50);
        assertEquals(request.getGroupId(), "gr-1337");
        assertEquals(request.getFacets(), facets);
        assertEquals(request.getSortBy(), "smooth-to-chunky");
        assertEquals(request.getSortAscending(), false);
        assertEquals(request.getFormatOptions(), formatOptions);
        assertEquals(request.getHiddenFields(), hiddenFields);
        assertEquals(request.getHiddenFacets(), hiddenFacets);
    }
}
