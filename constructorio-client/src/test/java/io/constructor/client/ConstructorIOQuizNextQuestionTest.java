package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.QuizQuestionResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOQuizNextQuestionTest {

    private final String quizKey = System.getenv("TEST_REQUEST_API_KEY");
    private final String quizId = "test-quiz";
    private final String versionId = "e03210db-0cc6-459c-8f17-bf014c4f554d";
    private final String sessionId = "1234";
    private static List<List<String>> validAnswers = new ArrayList<>();
    private static List<List<String>> finalAnswers = new ArrayList<>();

    @BeforeClass
    public static void init() {
        validAnswers.add(new ArrayList<String>(Arrays.asList("1")));
        validAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));

        finalAnswers.add(new ArrayList<String>(Arrays.asList("1")));
        finalAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));
        finalAnswers.add(new ArrayList<String>(Arrays.asList("seen")));
        finalAnswers.add(new ArrayList<String>(Arrays.asList("true")));
    }

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void QuizQuestionShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        QuizQuestionResponse response = constructor.quizNextQuestion(request, null);

        assertEquals("Quiz next_question id is correct", 1, response.getNextQuestion().getId());
        assertNotNull("quiz_version_id exists", response.getQuizVersionId());
        assertNotNull("is_last_question exists", response.getIsLastQuestion());
    }

    @Test
    public void QuizQuestionAsJsonShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        String response = constructor.quizNextQuestionAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertEquals(
                "Quiz next_question id is correct",
                1,
                jsonObject.getJSONObject("next_question").getInt("id"));
        assertFalse("quiz_version_id exists", jsonObject.isNull("quiz_version_id"));
        assertFalse("is_last_question exists", jsonObject.isNull("is_last_question"));
    }

    @Test
    public void QuizQuestionShouldReturnAResultWithSessionIdAndVersionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setQuizVersionId(versionId);
        request.setQuizSessionId(sessionId);
        QuizQuestionResponse response = constructor.quizNextQuestion(request, null);

        assertEquals("Quiz next_question id is correct", 1, response.getNextQuestion().getId());
        assertEquals("quiz_version_id exists", response.getQuizVersionId(), versionId);
        assertEquals("quiz_session_id exists", response.getQuizSessionId(), sessionId);
        assertNotNull("is_last_question exists", response.getIsLastQuestion());
    }

    @Test
    public void QuizQuestionAsJsonShouldReturnAResultWithSessionIdAndVersionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setQuizVersionId(versionId);
        request.setQuizSessionId(sessionId);
        String response = constructor.quizNextQuestionAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertEquals(
                "Quiz next_question id is correct",
                1,
                jsonObject.getJSONObject("next_question").getInt("id"));
        assertEquals("quiz_version_id exists", jsonObject.get("quiz_version_id"), versionId);
        assertEquals("quiz_session_id exists", jsonObject.get("quiz_session_id"), sessionId);
        assertFalse("is_last_question exists", jsonObject.isNull("is_last_question"));
    }

    @Test
    public void QuizQuestionShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a"
                        + " valid quiz id before trying again.");
        QuizQuestionResponse response = constructor.quizNextQuestion(request, null);
    }

    @Test
    public void QuizQuestionAsJsonShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a"
                        + " valid quiz id before trying again.");
        String response = constructor.quizNextQuestionAsJson(request, null);
    }

    @Test
    public void QuizQuestionShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \""
                        + quizId
                        + "\" was not found, please specify a valid quiz id before trying again.");
        QuizQuestionResponse response = constructor.quizNextQuestion(request, null);
    }

    @Test
    public void QuizQuestionAsJsonShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \""
                        + quizId
                        + "\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.quizNextQuestionAsJson(request, null);
    }

    @Test
    public void QuizQuestionShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);
        QuizQuestionResponse response = constructor.quizNextQuestion(request, null);

        assertEquals("Quiz next_question id is correct", 3, response.getNextQuestion().getId());
        assertNotNull("quiz_version_id exists", response.getQuizVersionId());
        assertNotNull("is_last_question exists", response.getIsLastQuestion());
    }

    @Test
    public void QuizQuestionAsJsonShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);
        String response = constructor.quizNextQuestionAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertEquals(
                "Quiz next_question id is correct",
                3,
                jsonObject.getJSONObject("next_question").getInt("id"));
        assertFalse("quiz_version_id exists", jsonObject.isNull("quiz_version_id"));
        assertFalse("is_last_question exists", jsonObject.isNull("is_last_question"));
    }

    @Test
    public void QuizQuestionShouldReturnResultWithAllAnswerTypes() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(finalAnswers);
        QuizQuestionResponse response = constructor.quizNextQuestion(request, null);

        assertNull("Quiz next_question is null", response.getNextQuestion());
        assertNotNull("quiz_version_id exists", response.getQuizVersionId());
        assertTrue("is_last_question exists", response.getIsLastQuestion());
    }

    @Test
    public void QuizQuestionAsJsonShouldReturnResultWithAllAnswerTypes() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(finalAnswers);
        String response = constructor.quizNextQuestionAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertTrue("Quiz next_question is null", jsonObject.isNull("next_question"));
        assertFalse("quiz_version_id exists", jsonObject.isNull("quiz_version_id"));
        assertTrue("is_last_question exists", jsonObject.getBoolean("is_last_question"));
    }
}
