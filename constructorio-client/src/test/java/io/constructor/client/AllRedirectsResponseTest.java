package io.constructor.client;

import io.constructor.client.models.AllRedirectsResponse;
import io.constructor.client.models.AllTasksResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AllRedirectsResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createAllTaskResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.redirects.json");
        AllRedirectsResponse response = ConstructorIO.createAllRedirectsResponse(string);

        assertEquals("2 redirects exist", 2, response.getTotalCount());
        assertEquals("2 redirects exist", 2, response.getRedirectRules().size());
        assertNotNull("redirects exist", response.getRedirectRules());
    }

}
