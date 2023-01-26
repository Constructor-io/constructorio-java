package io.constructor.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import io.constructor.client.models.QuizResultsResponse;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ConstructorIOQuizResultsTest {

    private final String quizKey = System.getenv("TEST_REQUEST_API_KEY");
    private final String quizId = "test-quiz";
    private static List<List<String>> validAnswers = new ArrayList<> ();
    private static List<List<String>> finalAnswers = new ArrayList<> ();

    @BeforeClass
    public static void init () {
        validAnswers.add(new ArrayList<String>(Arrays.asList("1")));
        validAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));

        finalAnswers.add(new ArrayList<String>(Arrays.asList("1")));
        finalAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));
        finalAnswers.add(new ArrayList<String>(Arrays.asList("seen")));
        finalAnswers.add(new ArrayList<String>(Arrays.asList("true")));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void QuizResultsShouldErrorWithoutAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("answers is a required parameter for a finalize request");
        QuizResultsResponse response = constructor.quizResults(request, null);
    }

    @Test
    public void QuizResultsAsJSONShouldErrorWithoutAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("answers is a required parameter for a finalize request");
        String response = constructor.quizResultsAsJson(request, null);
    }

    @Test
    public void QuizResultsShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);
        QuizResultsResponse response = constructor.quizResults(request, null);

        assertNotNull("version_id exists", response.getVersionId());
        assertNotNull("result exists", response.getResult());
        assertNotNull("result results_url exists", response.getResult().getResultsUrl());
    }

    @Test
    public void QuizResultsAsJsonShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);
        String response = constructor.quizResultsAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertFalse("version_id exists", jsonObject.isNull("version_id"));
        assertFalse("result exists", jsonObject.isNull("result"));
        assertFalse("result results_url exists", jsonObject.getJSONObject("result").isNull("results_url"));
    }

    @Test
    public void QuizResultsShouldReturnResultWithAllAnswerTypes() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(finalAnswers);
        QuizResultsResponse response = constructor.quizResults(request, null);
        String jsonFilterExpression = new Gson().toJson(response.getResult().getFilterExpression());

        assertNotNull("version_id exists", response.getVersionId());
        assertNotNull("result exists", response.getResult());
        assertNotNull("filter_expression exists", response.getResult().getFilterExpression());
        assertEquals("filter expression is correct", "{\"and\":[{\"name\":\"group_id\",\"value\":\"BrandX\"},{\"or\":[{\"name\":\"Color\",\"value\":\"Blue\"},{\"name\":\"Color\",\"value\":\"red\"}]}]}", jsonFilterExpression);
        assertNotNull("result results_url exists", response.getResult().getResultsUrl());
    }

    @Test
    public void QuizResultsAsJsonShouldReturnResultWithAllAnswerTypes() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(finalAnswers);
        String response = constructor.quizResultsAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertFalse("version_id exists", jsonObject.isNull("version_id"));
        assertFalse("result exists", jsonObject.isNull("result"));
        assertFalse("result results_url exists", jsonObject.getJSONObject("result").isNull("results_url"));
        assertEquals("filter expression is correct", "{\"and\":[{\"name\":\"group_id\",\"value\":\"BrandX\"},{\"or\":[{\"name\":\"Color\",\"value\":\"Blue\"},{\"name\":\"Color\",\"value\":\"red\"}]}]}", jsonObject.getJSONObject("result").getJSONObject("filter_expression").toString());
    }

    @Test
    public void QuizResultsShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a valid quiz id before trying again.");
        QuizResultsResponse response = constructor.quizResults(request, null);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.quizResultsAsJson(request, null);
    }

    @Test
    public void QuizResultsShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"" + quizId + "\" was not found, please specify a valid quiz id before trying again.");
        QuizResultsResponse response = constructor.quizResults(request, null);
    }

    @Test
    public void QuizResultsAsJsonShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setAnswers(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"" + quizId + "\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.quizResultsAsJson(request, null);
    }
}
