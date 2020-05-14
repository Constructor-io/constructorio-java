package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.RecommendationsResponse;

public class ConstructorIORecommendationsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createRecommendationsResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.recommendations.home_page_1.json");
        RecommendationsResponse response = ConstructorIO.createRecommendationsResponse(string);

        assertEquals("pod display name exists", response.getResponse().getPod().getDisplayName(), "You may also like");
        assertEquals("pod display id exists", response.getResponse().getPod().getId(), "item_page_1");
        assertEquals("recommendation results exist", response.getResponse().getResults().size(), 10);
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 10);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithSingleItemId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill"));
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertEquals("recommendation results exist", response.getResponse().getResults().size(), 10);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithMultipleItemIds() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertEquals("recommendation results exist", response.getResponse().getResults().size(), 10);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithNumResults() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        request.setNumResults(5);
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertEquals("recommendation results exist", response.getResponse().getResults().size(), 5);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }
}
