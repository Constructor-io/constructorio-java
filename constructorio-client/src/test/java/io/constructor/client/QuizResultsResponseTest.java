package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.QuizResultsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuizResultsResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createQuizResultsResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.quizresults.json");
        QuizResultsResponse response = ConstructorIO.createQuizResultsResponse(string);

        assertEquals(
                "quiz_version_id exists",
                "8298dc03-e7eb-4ce8-8c6f-6315c900b5e8",
                response.getVersionId());
        assertEquals(
                "quiz_session_id exists",
                "bf2f9b65-84bf-492a-bad1-66ffd07f9448",
                response.getSessionId());
        assertEquals("quiz_id exists", "test-quiz", response.getQuizId());
        assertNotNull("response exists", response.getResponse());
        assertEquals("response has 6 items", 6, response.getResponse().getResults().size());
        assertTrue("response facets exist", response.getResponse().getFacets().size() > 0);
        assertTrue("response groups exist", response.getResponse().getGroups().size() > 0);
        assertTrue(
                "response total num results exist",
                response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("response sort options", response.getResponse().getSortOptions().size() > 0);
    }
}
