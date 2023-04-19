package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.QuizQuestionResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuizQuestionResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createQuizQuestionResponseShouldReturnAResultForSingleSelect() throws Exception {
        String string = Utils.getTestResource("response.nextquiz.singleselect.json");
        QuizQuestionResponse response = ConstructorIO.createQuizQuestionResponse(string);

        assertNotNull("next_question exists", response.getNextQuestion());
        assertEquals("next_question id exists", 1, response.getNextQuestion().getId());
        assertEquals(
                "next_question title exists",
                "Sample single select question",
                response.getNextQuestion().getTitle());
        assertEquals(
                "next_question cta_text exists", "Next", response.getNextQuestion().getCtaText());
        assertEquals(
                "next_question description exists",
                "Sample description",
                response.getNextQuestion().getDescription());
        assertEquals("next_question type exists", "single", response.getNextQuestion().getType());
        assertNotNull("next_question options exists", response.getNextQuestion().getOptions());
        assertEquals(
                "next_question options id exists",
                1,
                response.getNextQuestion().getOptions().get(0).getId());
        assertEquals(
                "next_question options value exists",
                "Who",
                response.getNextQuestion().getOptions().get(0).getValue());
        assertNotNull(
                "next_question option attribute exists",
                response.getNextQuestion().getOptions().get(0).getAttribute());
        assertEquals(
                "next_question option attribute value exists",
                "test-value",
                response.getNextQuestion().getOptions().get(0).getAttribute().getValue());
        assertEquals(
                "next_question option attribute name exists",
                "group_id",
                response.getNextQuestion().getOptions().get(0).getAttribute().getName());
        assertFalse("is_last_question exists", response.getIsLastQuestion());
        assertEquals(
                "quiz_version_id exists",
                "11db5ac7-67e1-4000-9000-414d8425cab3",
                response.getQuizVersionId());
        assertEquals(
                "quiz_session_id exists",
                "bf2f9b65-84bf-492a-bad1-66ffd07f9448",
                response.getQuizSessionId());
    }

    @Test
    public void createQuizQuestionResponseShouldReturnAResultForMultielect() throws Exception {
        String string = Utils.getTestResource("response.nextquiz.multiselect.json");
        QuizQuestionResponse response = ConstructorIO.createQuizQuestionResponse(string);

        assertNotNull("next_question exists", response.getNextQuestion());
        assertEquals("next_question id exists", 1, response.getNextQuestion().getId());
        assertEquals(
                "next_question title exists",
                "Sample multiple select question",
                response.getNextQuestion().getTitle());
        assertEquals(
                "next_question cta_text exists", "Next", response.getNextQuestion().getCtaText());
        assertEquals(
                "next_question description exists",
                "Sample description",
                response.getNextQuestion().getDescription());
        assertEquals("next_question type exists", "multiple", response.getNextQuestion().getType());
        assertNotNull("next_question options exists", response.getNextQuestion().getOptions());
        assertEquals(
                "next_question options id exists",
                1,
                response.getNextQuestion().getOptions().get(0).getId());
        assertEquals(
                "next_question options value exists",
                "Who",
                response.getNextQuestion().getOptions().get(0).getValue());
        assertNotNull(
                "next_question option attribute exists",
                response.getNextQuestion().getOptions().get(0).getAttribute());
        assertEquals(
                "next_question option attribute value exists",
                "test-value",
                response.getNextQuestion().getOptions().get(0).getAttribute().getValue());
        assertEquals(
                "next_question option attribute name exists",
                "group_id",
                response.getNextQuestion().getOptions().get(0).getAttribute().getName());
        assertNotNull(
                "next_question option images exists",
                response.getNextQuestion().getOptions().get(0).getImages());
        assertEquals(
                "next_question option images exists",
                "Example secondary image",
                response.getNextQuestion().getOptions().get(0).getImages().getSecondaryAlt());
        assertEquals(
                "next_question option images exists",
                "https://example.com/small_image",
                response.getNextQuestion().getOptions().get(0).getImages().getSecondaryUrl());
        assertFalse("is_last_question exists", response.getIsLastQuestion());
        assertEquals(
                "quiz_version_id exists",
                "11db5ac7-67e1-4000-9000-414d8425cab3",
                response.getQuizVersionId());
        assertEquals(
                "quiz_session_id exists",
                "bf2f9b65-84bf-492a-bad1-66ffd07f9448",
                response.getQuizSessionId());
    }

    @Test
    public void createQuizQuestionResponseShouldReturnAResultForOpenText() throws Exception {
        String string = Utils.getTestResource("response.nextquiz.opentext.json");
        QuizQuestionResponse response = ConstructorIO.createQuizQuestionResponse(string);

        assertNotNull("next_question exists", response.getNextQuestion());
        assertEquals("next_question id exists", 1, response.getNextQuestion().getId());
        assertEquals(
                "next_question title exists",
                "Sample open text question",
                response.getNextQuestion().getTitle());
        assertEquals(
                "next_question cta_text exists", "Next", response.getNextQuestion().getCtaText());
        assertEquals(
                "next_question description exists",
                "Sample description",
                response.getNextQuestion().getDescription());
        assertEquals("next_question type exists", "open", response.getNextQuestion().getType());
        assertEquals(
                "next_question input_placeholder exists",
                "Sample input placeholder",
                response.getNextQuestion().getInputPlaceholder());
        assertEquals(
                "next_question option images exists",
                "Example image",
                response.getNextQuestion().getImages().getPrimaryAlt());
        assertEquals(
                "next_question option images exists",
                "https://example.com/image",
                response.getNextQuestion().getImages().getPrimaryUrl());
        assertFalse("is_last_question exists", response.getIsLastQuestion());
        assertEquals(
                "quiz_version_id exists",
                "11db5ac7-67e1-4000-9000-414d8425cab3",
                response.getQuizVersionId());
        assertEquals(
                "quiz_session_id exists",
                "bf2f9b65-84bf-492a-bad1-66ffd07f9448",
                response.getQuizSessionId());
    }

    @Test
    public void createQuizQuestionResponseShouldReturnAResultForCoverPage() throws Exception {
        String string = Utils.getTestResource("response.nextquiz.coverpage.json");
        QuizQuestionResponse response = ConstructorIO.createQuizQuestionResponse(string);

        assertNotNull("next_question exists", response.getNextQuestion());
        assertEquals("next_question id exists", 1, response.getNextQuestion().getId());
        assertEquals(
                "next_question title exists",
                "Sample cover page",
                response.getNextQuestion().getTitle());
        assertEquals(
                "next_question cta_text exists", "Next", response.getNextQuestion().getCtaText());
        assertEquals(
                "next_question description exists",
                "Sample description",
                response.getNextQuestion().getDescription());
        assertEquals("next_question type exists", "cover", response.getNextQuestion().getType());
        assertEquals(
                "next_question option images exists",
                "Example image",
                response.getNextQuestion().getImages().getPrimaryAlt());
        assertEquals(
                "next_question option images exists",
                "https://example.com/image",
                response.getNextQuestion().getImages().getPrimaryUrl());
        assertFalse("is_last_question exists", response.getIsLastQuestion());
        assertEquals(
                "quiz_version_id exists",
                "11db5ac7-67e1-4000-9000-414d8425cab3",
                response.getQuizVersionId());
        assertEquals(
                "quiz_session_id exists",
                "bf2f9b65-84bf-492a-bad1-66ffd07f9448",
                response.getQuizSessionId());
    }
}
