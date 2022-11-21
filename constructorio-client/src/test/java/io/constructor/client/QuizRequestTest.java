package io.constructor.client;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class QuizRequestTest {

    private  static List<List<String>> validAnswers = new ArrayList<>();

    @BeforeClass
    public static void init () {
        validAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));
        validAnswers.add(new ArrayList<String>(Arrays.asList("1")));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NewShouldErrorWithNoQuizId() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new QuizRequest(null);
    }

    @Test
    public void settersShouldSet() throws Exception {
        QuizRequest request = new QuizRequest("id");
        assertEquals("id", request.getId());
        assertNull(request.getSection());
        assertNull(request.getVersionId());
        assertTrue(request.getAnswers().size() == 0);

        request.setSection("newsection");
        request.setVersionId("newversion");
        request.setAnswers(validAnswers);

        assertEquals("newsection", request.getSection());
        assertEquals("newversion", request.getVersionId());
        assertEquals(request.getAnswers(), validAnswers);
    }
}
