package io.constructor.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.constructor.client.models.NextQuizResponse;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ConstructorIONextQuizTest {

    private final String quizKey = System.getenv("TEST_API_KEY");
    private final String quizId = "test-quiz";
    private static List<List<String>> validAnswers = new ArrayList<> ();

    @BeforeClass
    public static void init () {
        validAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));
        validAnswers.add(new ArrayList<String>(Arrays.asList("1")));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NextQuizShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        NextQuizResponse response = constructor.nextQuiz(request, null);

        assertEquals("Quiz next_question id is correct", 1, response.getNextQuestion().getId());
        assertNotNull("version_id exists", response.getVersionId());
        assertNotNull("is_last_question exists", response.getIsLastQuestion());
    }

    @Test
    public void NextQuizAsJsonShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        String response = constructor.nextQuizAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertEquals("Quiz next_question id is correct", 1, jsonObject.getJSONObject("next_question").getInt("id"));
        assertFalse("version_id exists", jsonObject.isNull("version_id"));
        assertFalse("is_last_question exists", jsonObject.isNull("is_last_question"));
    }

    @Test
    public void NextQuizShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a valid quiz id before trying again.");
        NextQuizResponse response = constructor.nextQuiz(request, null);
    }

    @Test
    public void NextQuizAsJsonShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.nextQuizAsJson(request, null);
    }

    @Test
    public void NextQuizShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"etchells-emporium-quiz\" was not found, please specify a valid quiz id before trying again.");
        NextQuizResponse response = constructor.nextQuiz(request, null);
    }

    @Test
    public void NextQuizAsJsonShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"etchells-emporium-quiz\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.nextQuizAsJson(request, null);
    }

    @Test
    public void NextQuizShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setA(validAnswers);
        NextQuizResponse response = constructor.nextQuiz(request, null);

        assertEquals("Quiz next_question id is correct", 3, response.getNextQuestion().getId());
        assertNotNull("version_id exists", response.getVersionId());
        assertNotNull("is_last_question exists", response.getIsLastQuestion());
    }

    @Test
    public void NextQuizAsJsonShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setA(validAnswers);
        String response = constructor.nextQuizAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertEquals("Quiz next_question id is correct", 3, jsonObject.getJSONObject("next_question").getInt("id"));
        assertFalse("version_id exists", jsonObject.isNull("version_id"));
        assertFalse("is_all_skippable exists", jsonObject.isNull("is_all_skippable"));
        assertFalse("is_skippable exists", jsonObject.isNull("is_skippable"));
        assertFalse("is_last_question exists", jsonObject.isNull("is_last_question"));
    }
}