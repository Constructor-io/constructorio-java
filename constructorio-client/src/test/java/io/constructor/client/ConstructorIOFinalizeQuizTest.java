package io.constructor.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.constructor.client.models.FinalizeQuizResponse;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ConstructorIOFinalizeQuizTest {

    private final String quizKey = System.getenv("TEST_API_KEY");
    private final String quizId = "test-quiz";
    private static List<List<String>> validAnswers = new ArrayList<> ();

    @BeforeClass
    public static void init () {
        validAnswers.add(new ArrayList<String>(Arrays.asList("1")));
        validAnswers.add(new ArrayList<String>(Arrays.asList("1", "2")));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void FinalizeQuizShouldErrorWithoutAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("a (answers) is a required parameter for a finalize request");
        FinalizeQuizResponse response = constructor.finalizeQuiz(request, null);
    }

    @Test
    public void FinalizeQuizAsJSONShouldErrorWithoutAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("a (answers) is a required parameter for a finalize request");
        String response = constructor.finalizeQuizAsJson(request, null);
    }

    @Test
    public void FinalizeQuizShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setA(validAnswers);
        FinalizeQuizResponse response = constructor.finalizeQuiz(request, null);

        assertNotNull("version_id exists", response.getVersionId());
        assertNotNull("result exists", response.getResult());
        assertNotNull("result browse_url exists", response.getResult().getResultUrl());
    }

    @Test
    public void FinalizeQuizAsJsonShouldReturnResultWithAnswersParameter() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setA(validAnswers);
        String response = constructor.finalizeQuizAsJson(request, null);
        JSONObject jsonObject = new JSONObject(response);

        assertFalse("version_id exists", jsonObject.isNull("version_id"));
        assertFalse("result exists", jsonObject.isNull("result"));
        assertFalse("result browse_url exists", jsonObject.getJSONObject("result").isNull("browse_url"));
    }

    @Test
    public void FinalizeQuizShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");
        request.setA(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a valid quiz id before trying again.");
        FinalizeQuizResponse response = constructor.finalizeQuiz(request, null);
    }

    @Test
    public void FinalizeQuizAsJsonShouldReturnErrorWithInvalidQuizId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", quizKey, true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest("invalidQuiz");
        request.setA(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"invalidQuiz\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.finalizeQuizAsJson(request, null);
    }

    @Test
    public void FinalizeQuizShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setA(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"" + quizId + "\" was not found, please specify a valid quiz id before trying again.");
        FinalizeQuizResponse response = constructor.finalizeQuiz(request, null);
    }

    @Test
    public void FinalizeQuizAsJsonShouldReturnErrorWithInvalidIndexKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "invalidKey", true, "quizzes.cnstrc.com");
        QuizRequest request = new QuizRequest(quizId);
        request.setA(validAnswers);

        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] The quiz you requested, \"" + quizId + "\" was not found, please specify a valid quiz id before trying again.");
        String response = constructor.finalizeQuizAsJson(request, null);
    }
}