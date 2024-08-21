package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.QuizResultsConfigResponse;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOQuizResultsConfigTest {

    private final String quizKey = System.getenv("TEST_REQUEST_API_KEY");
    private final String quizId = "test-quiz";

    @BeforeClass
    public static void init() {
    }

    @Rule public ExpectedException thrown = ExpectedException.none();


    @Test
    public void QuizResultsConfigShouldReturnResuls() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        String quizVersionId = constructor.quizResultsConfig(request, null).getQuizVersionId();
        request.setQuizVersionId(quizVersionId);

        QuizResultsConfigResponse response = constructor.quizResultsConfig(request, null);

        assertEquals("quiz_version_id exists", response.getQuizVersionId(), quizVersionId);
        assertNotNull("results_config exists", response.getResultsConfig());
        assertNotNull("quiz_id exists", response.getQuizId());
    }

    @Test
    public void QuizResultsConfigAsJsonShouldReturnResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        String response = constructor.quizResultsConfigAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertFalse("quiz_version_id exists", jsonObject.isNull("quiz_version_id"));
        assertFalse("quiz_id exists", jsonObject.isNull("quiz_id"));
        assertFalse("results_config exists", jsonObject.isNull("results_config"));
    }

    @Test
    public void QuizResultsShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");

        
        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a"
                        + " valid quiz id before trying again.");
        QuizResultsConfigResponse response = constructor.quizResultsConfig(request, null);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a"
                        + " valid quiz id before trying again.");
        String response = constructor.quizResultsConfigAsJson(request, null);
    }

    @Test
    public void QuizResultsShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \""
                        + quizId
                        + "\" was not found, please specify a valid quiz id before trying again.");
        QuizResultsConfigResponse response = constructor.quizResultsConfig(request, null);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage(
                "[HTTP 404] The quiz you requested, \""
                        + quizId
                        + "\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.quizResultsConfigAsJson(request, null);
    }
}
