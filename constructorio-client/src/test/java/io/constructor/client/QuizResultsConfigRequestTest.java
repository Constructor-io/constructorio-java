package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuizResultsConfigRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NewShouldErrorWithNoQuizId() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new QuizResultsConfigRequest(null);
    }

    @Test
    public void settersShouldSet() throws Exception {
        QuizResultsConfigRequest request = new QuizResultsConfigRequest("id");
        assertEquals("id", request.getQuizId());
        assertNull(request.getSection());
        assertNull(request.getQuizVersionId());

        request.setSection("newsection");
        request.setQuizVersionId("newversion");

        assertEquals("newsection", request.getSection());
        assertEquals("newversion", request.getQuizVersionId());
    }
}
