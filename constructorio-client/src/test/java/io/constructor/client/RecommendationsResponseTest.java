package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.RecommendationsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RecommendationsResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createRecommendationsResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.recommendations.item_page_1.json");
        RecommendationsResponse response = ConstructorIO.createRecommendationsResponse(string);

        assertEquals(
                "pod display name exists",
                response.getResponse().getPod().getDisplayName(),
                "You may also like");
        assertEquals(
                "pod display id exists", response.getResponse().getPod().getId(), "item_page_1");
        assertEquals(
                "recommendation results exist", response.getResponse().getResults().size(), 10);
        assertEquals(
                "total number of results",
                (int) response.getResponse().getTotalNumberOfResults(),
                10);
        assertTrue("recommendation result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertTrue(
                "recommendation result labels exist",
                (Boolean)
                        response.getResponse().getResults().get(0).getLabels().get("is_sponsored"));
        assertEquals(
                "request filter value exists", response.getRequest().get("item_id"), "109350015");
        assertEquals(
                "recommendation result strategy exists",
                response.getResponse().getResults().get(0).getStrategy().get("id"),
                "alternative_items");
    }
}
