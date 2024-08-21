package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.QuizResultsConfigResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuizResultsConfigResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createQuizResultsConfigResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.quizresultsconfig.json");
        QuizResultsConfigResponse response = ConstructorIO.createQuizResultsConfigResponse(string);

        assertEquals(
                "quiz_version_id exists",
                "8298dc03-e7eb-4ce8-8c6f-6315c900b5e8",
                response.getQuizVersionId());
        assertEquals("quiz_id exists", "test-quiz", response.getQuizId());
        assertNotNull("results config exists", response.getResultsConfig());
    }
}
