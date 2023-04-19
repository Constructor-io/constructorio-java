package io.constructor.client;

import java.util.ArrayList;
import java.util.List;

/** Constructor.io Next Quiz and Results Quiz request */
public class QuizRequest {
    private String quizId;
    private String indexKey;
    private String section;
    private List<List<String>> answers;
    private String quizVersionId;
    private String quizSessionId;

    /**
     * Creates a Next Quiz/Results Quiz request
     *
     * @param quizId the id of the quiz to request
     */
    public QuizRequest(String quizId) {
        if (quizId == null)
            throw new IllegalArgumentException("id is a required parameter of type String");

        this.quizId = quizId;
        this.section = null;
        this.answers = new ArrayList<>();
        this.quizVersionId = null;
        this.quizSessionId = null;
    }

    /**
     * @param quizId the id of the quiz to request
     */
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    /**
     * @return the id of the quiz to request
     */
    public String getQuizId() {
        return quizId;
    }

    /**
     * @param section the section of the product catalog
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the section of the product catalog
     */
    public String getSection() {
        return section;
    }

    /**
     * @param answers the list of answers in the format [ [1,2], [3,4] ] where [1,2] are the
     *     selected answers for the first question and [3,4] are the selected answers for the second
     *     question
     */
    public void setAnswers(List<List<String>> answers) {
        this.answers = answers;
    }

    /**
     * @return the list of answers for the quiz
     */
    public List<List<String>> getAnswers() {
        return answers;
    }

    /**
     * @param quizVersionId The quiz version id will be returned with the first request and it should be passed
     *     with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public void setQuizVersionId(String quizVersionId) {
        this.quizVersionId = quizVersionId;
    }

    /**
     * @return the quizVersionId. The quiz version id will be returned with the first request and it should
     *     be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public String getQuizVersionId() {
        return quizVersionId;
    }

    /**
     * @param quizSessionId The quiz session id will be returned with the
     *     first request, and it should be passed with subsequent requests. More information can be
     *     found: https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public void setQuizSessionId(String quizSessionId) {
        this.quizSessionId = quizSessionId;
    }

    /**
     * @return the quizSessionId. The quiz session id will be returned with the first request
     *     and it should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public String getQuizSessionId() {
        return quizSessionId;
    }
}
