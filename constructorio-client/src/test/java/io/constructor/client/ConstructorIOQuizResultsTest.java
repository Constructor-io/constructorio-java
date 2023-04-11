package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.QuizResultsResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOQuizResultsTest {

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
    public void QuizResultsShouldErrorWithoutAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("answers is a required parameter for a results request");
        QuizResultsResponse response = constructor.quizResults(request, null);
    }

    @Test
    public void QuizResultsAsJSONShouldErrorWithoutAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("answers is a required parameter for a results request");
        String response = constructor.quizResultsAsJson(request, null);
    }

    @Test
    public void QuizResultsShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);
        QuizResultsResponse response = constructor.quizResults(request, null);

        assertNotNull("quiz_version_id exists", response.getVersionId());
        assertNotNull("quiz_session_id exists", response.getSessionId());
        assertNotNull("response exists", response.getResponse());
        assertNotNull("quiz_id exists", response.getQuizId());
        assertTrue("results exist", response.getResponse().getResults().size() > 0);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);
        String response = constructor.quizResultsAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertFalse("quiz_version_id exists", jsonObject.isNull("quiz_version_id"));
        assertFalse("quiz_session_id exists", jsonObject.isNull("quiz_session_id"));
        assertFalse("quiz_id exists", jsonObject.isNull("quiz_id"));
        assertFalse("response exists", jsonObject.isNull("response"));
        assertFalse("results exist", jsonObject.getJSONObject("response").isNull("results"));
    }

    @Test
    public void QuizResultsShouldReturnResultWithVersionIdAndSessionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setVersionId(versionId);
        request.setSessionId(sessionId);
        request.setAnswers(validAnswers);
        QuizResultsResponse response = constructor.quizResults(request, null);

        assertEquals("quiz_version_id exists", response.getVersionId(), versionId);
        assertEquals("quiz_session_id exists", response.getSessionId(), sessionId);
        assertNotNull("response exists", response.getResponse());
        assertNotNull("quiz_id exists", response.getQuizId());
        assertTrue("results exist", response.getResponse().getResults().size() > 0);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnResultWithVersionIdAndSessionid() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setVersionId(versionId);
        request.setSessionId(sessionId);
        request.setAnswers(validAnswers);
        String response = constructor.quizResultsAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertEquals("quiz_version_id exists", jsonObject.get("quiz_version_id"), versionId);
        assertEquals("quiz_session_id exists", jsonObject.get("quiz_session_id"), sessionId);
        assertFalse("quiz_id exists", jsonObject.isNull("quiz_id"));
        assertFalse("response exists", jsonObject.isNull("response"));
        assertFalse("results exist", jsonObject.getJSONObject("response").isNull("results"));
    }

    @Test
    public void QuizResultsShouldReturnResultWithAllAnswerTypes() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(finalAnswers);
        QuizResultsResponse response = constructor.quizResults(request, null);

        assertNotNull("quiz_version_id exists", response.getVersionId());
        assertNotNull("quiz_session_id exists", response.getSessionId());
        assertNotNull("response exists", response.getResponse());
        assertNotNull("quiz_id exists", response.getQuizId());
        assertTrue("results exist", response.getResponse().getResults().size() > 0);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnResultWithAllAnswerTypes() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(finalAnswers);
        String response = constructor.quizResultsAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertFalse("quiz_version_id exists", jsonObject.isNull("quiz_version_id"));
        assertFalse("quiz_session_id exists", jsonObject.isNull("quiz_session_id"));
        assertFalse("quiz_id exists", jsonObject.isNull("quiz_id"));
        assertFalse("response exists", jsonObject.isNull("response"));
        assertFalse("results exist", jsonObject.getJSONObject("response").isNull("results"));
    }

    @Test
    public void QuizResultsShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a"
                        + " valid quiz id before trying again.");
        QuizResultsResponse response = constructor.quizResults(request, null);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a"
                        + " valid quiz id before trying again.");
        String response = constructor.quizResultsAsJson(request, null);
    }

    @Test
    public void QuizResultsShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \""
                        + quizId
                        + "\" was not found, please specify a valid quiz id before trying again.");
        QuizResultsResponse response = constructor.quizResults(request, null);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \""
                        + quizId
                        + "\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.quizResultsAsJson(request, null);
    }
}
