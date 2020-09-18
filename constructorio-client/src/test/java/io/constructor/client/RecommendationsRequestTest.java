package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

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
        assertEquals(request.getNumResults(), 10);
        assertEquals(request.getItemIds(), null);
        assertEquals(request.getSection(), "Products");
    }

    @Test
    public void settersShouldSet() throws Exception {
        String podId = "home_page_1";
        RecommendationsRequest request = new RecommendationsRequest(podId);
        
        request.setPodId("zero_results_1");
        request.setNumResults(3);
        request.setItemIds(Arrays.asList("1", "2", "3"));
        request.setSection("Search Suggestions");

        assertEquals(request.getPodId(), "zero_results_1");
        assertEquals(request.getNumResults(), 3);
        assertEquals(request.getSection(), "Search Suggestions");
    }
}
