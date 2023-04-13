package io.constructor.client;

import java.util.ArrayList;
import java.util.List;

/** Constructor.io Next Quiz and Finalize Quiz request */
public class QuizRequest {
    private String id;
    private String indexKey;
    private String section;
    private List<List<String>> answers;
    private String versionId;
    private String sessionId;

    /**
     * Creates a Next Quiz/Finalize Quiz request
     *
     * @param id the id of the quiz to request
     */
    public QuizRequest(String id) {
        if (id == null)
            throw new IllegalArgumentException("id is a required parameter of type String");

        this.id = id;
        this.section = null;
        this.answers = new ArrayList<>();
        this.versionId = null;
        this.sessionId = null;
    }

    /**
     * @param id the id of the quiz to request
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the id of the quiz to request
     */
    public String getId() {
        return id;
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
     * @param versionId the specific version id for the quiz
     * Specific quiz_version_id for the quiz.
     * Version ID will be returned with the first request and it should be passed with subsequent requests.
     * More information can be found: https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * @return the specific version id for the quiz
     * Specific quiz_version_id for the quiz.
     * Version ID will be returned with the first request and it should be passed with subsequent requests.
     * More information can be found: https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * @param sessionId the session id of this quiz instance
     * Specific quiz_session_id for the quiz.
     * Session ID will be returned with the first request, and it should be passed with subsequent requests.
     * More information can be found: https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the session id of this quiz instance
     * Specific quiz_session_id for the quiz.
     * Session ID will be returned with the first request, and it should be passed with subsequent requests.
     * More information can be found: https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public String getSessionId() {
        return sessionId;
    }
}
