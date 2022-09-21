package io.constructor.client;

import io.constructor.client.models.NextQuizResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class NextQuizResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createNextQuizResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.nextquiz.json");
        NextQuizResponse response = ConstructorIO.createNextQuizResponse(string);

        assertNotNull("next_question exists", response.getNextQuestion());
        assertEquals("next_question id exists", 1, response.getNextQuestion().getId());
        assertEquals("next_question title exists", "Question title", response.getNextQuestion().getTitle());
        assertNotNull("next_question options exists", response.getNextQuestion().getOptions());
        assertEquals("next_question options id exists", 1, response.getNextQuestion().getOptions().get(0).getId());
        assertEquals("next_question options value exists", "Option 1", response.getNextQuestion().getOptions().get(0).getValue());
        assertTrue("is_last_question exists", response.getIsLastQuestion());
        assertEquals("version_id exists","string", response.getVersionId());
    }
}