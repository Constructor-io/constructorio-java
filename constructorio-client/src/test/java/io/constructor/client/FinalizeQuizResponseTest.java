package io.constructor.client;

import io.constructor.client.models.FinalizeQuizResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FinalizeQuizResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createFinalizeQuizResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.finalizequiz.json");
        FinalizeQuizResponse response = ConstructorIO.createFinalizeQuizResponse(string);

        assertNotNull("result exists", response.getResult());
        assertNotNull("filter_expression exists", response.getResult().getFilterExpression());
        assertNotNull("filter_expression and exists", response.getResult().getFilterExpression().get("and"));
        assertEquals("browse_url exists", "BROWSE_URL", response.getResult().getBrowseUrl());
        assertEquals("version_id exists","string", response.getVersionId());
    }
}