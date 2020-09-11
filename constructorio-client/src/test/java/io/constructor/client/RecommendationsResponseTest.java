package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.RecommendationsResponse;

public class RecommendationsResponseTest {

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
}
