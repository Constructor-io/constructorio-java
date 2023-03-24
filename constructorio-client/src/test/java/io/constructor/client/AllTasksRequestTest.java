package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AllTasksRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newShouldReturnTaskRequestWithDefaults() throws Exception {
        AllTasksRequest request = new AllTasksRequest();
        assertEquals(0, request.getResultsPerPage());
        assertEquals(1, request.getPage());
    }

    @Test
    public void settersShouldSet() throws Exception {
        AllTasksRequest request = new AllTasksRequest();
        assertEquals(0, request.getResultsPerPage());
        assertEquals(1, request.getPage());

        request.setPage(5);
        request.setResultsPerPage(50);

        assertEquals(50, request.getResultsPerPage());
        assertEquals(5, request.getPage());
    }
}
