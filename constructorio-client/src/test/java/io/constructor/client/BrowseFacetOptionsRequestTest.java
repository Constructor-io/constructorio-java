package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BrowseFacetOptionsRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newShouldReturnBrowseFacetOptionsRequestWithDefaults() throws Exception {
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest("Color");
        assertEquals(0, request.getFormatOptions().size());
        assertEquals("Color", request.getFacetName());
    }

    @Test
    public void settersShouldSet() throws Exception {
        BrowseFacetOptionsRequest request = new BrowseFacetOptionsRequest("Color");
        assertEquals(0, request.getFormatOptions().size());
        assertEquals("Color", request.getFacetName());

        request.setFacetName("Brand");
        request.getFormatOptions().put("show_hidden_facets", "True");

        assertEquals(1, request.getFormatOptions().size());
        assertEquals("Brand", request.getFacetName());
    }
}
