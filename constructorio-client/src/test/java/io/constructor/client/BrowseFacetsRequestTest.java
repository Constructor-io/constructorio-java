package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BrowseFacetsRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newShouldReturnBrowseFacetsRequestWithDefaults() throws Exception {
        BrowseFacetsRequest request = new BrowseFacetsRequest();

        assertEquals(0, request.getFormatOptions().size());
        assertEquals(1, request.getPage());
        assertEquals(20, request.getResultsPerPage());
    }

    @Test
    public void settersShouldSet() throws Exception {
        BrowseFacetsRequest request = new BrowseFacetsRequest();

        assertEquals(0, request.getFormatOptions().size());
        assertEquals(1, request.getPage());
        assertEquals(20, request.getResultsPerPage());

        request.setPage(2);
        request.setResultsPerPage(50);
        request.getFormatOptions().put("show_hidden_facets", "True");

        assertEquals(1, request.getFormatOptions().size());
        assertEquals(2, request.getPage());
        assertEquals(50, request.getResultsPerPage());
    }
}
