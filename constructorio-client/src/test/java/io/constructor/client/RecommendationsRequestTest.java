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

public class RecommendationsRequestTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullPodIdShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new RecommendationsRequest(null);
    }

    @Test
    public void newShouldReturnRecommendationsRequest() throws Exception {
        String podId = "home_page_1";
        RecommendationsRequest request = new RecommendationsRequest(podId);
        assertEquals(request.getPodId(), podId);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        String podId = "home_page_1";
        RecommendationsRequest request = new RecommendationsRequest(podId);
        assertEquals(request.getPodId(), podId);
        assertEquals(request.getSection(), "Products");
        assertEquals(request.getNumResults(), 10);
    }

    @Test
    public void settersShouldSet() throws Exception {
        String podId = "home_page_1";
        RecommendationsRequest request = new RecommendationsRequest(podId);
        
        request.setPodId("zero_results_1");
        request.setSection("Search Suggestions");
        request.setNumResults(3);

        assertEquals(request.getPodId(), "zero_results_1");
        assertEquals(request.getSection(), "Search Suggestions");
        assertEquals(request.getNumResults(), 3);
    }
}
