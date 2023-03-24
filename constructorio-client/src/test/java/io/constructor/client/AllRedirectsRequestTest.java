package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AllRedirectsRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newShouldReturnTaskRequestWithDefaults() throws Exception {
        AllRedirectsRequest request = new AllRedirectsRequest();
        assertEquals(0, request.getResultsPerPage());
        assertEquals(0, request.getPage());
        assertEquals(0, request.getOffset());
    }

    @Test
    public void settersShouldSet() throws Exception {
        AllRedirectsRequest request = new AllRedirectsRequest();
        assertEquals(0, request.getResultsPerPage());
        assertEquals(0, request.getPage());
        assertEquals(0, request.getOffset());
        assertEquals(null, request.getQuery());
        assertEquals(null, request.getStatus());

        request.setPage(5);
        request.setResultsPerPage(50);
        request.setOffset(3);
        request.setQuery("bubble gum");
        request.setStatus("expired");

        assertEquals(50, request.getResultsPerPage());
        assertEquals(5, request.getPage());
        assertEquals(3, request.getOffset());
        assertEquals("bubble gum", request.getQuery());
        assertEquals("expired", request.getStatus());
    }
}
