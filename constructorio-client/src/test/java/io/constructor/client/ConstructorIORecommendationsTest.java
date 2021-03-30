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
    public void getRecommendationsShouldReturnAResultWithSingleItemId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill"));
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithMultipleItemIds() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
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
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }

    @Test
    public void getRecommendationsShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey("ZqXaOfXuBWD4s3XzCI1q");
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        RecommendationsRequest request = new RecommendationsRequest("item_page_1");
        request.setItemIds(Arrays.asList("power_drill", "drill"));
        request.setNumResults(5);
        RecommendationsResponse response = constructor.recommendations(request, userInfo);
        assertTrue("recommendation results exist", response.getResponse().getResults().size() >= 0);
        assertTrue("recommendation result id exists", response.getResultId() != null);
    }
}
