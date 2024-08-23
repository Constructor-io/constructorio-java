package io.constructor.client;

/** Constructor.io Base Quiz request */
public class QuizRequestBase {
    private String quizId;
    private String section;
    private String quizVersionId;

    /**
     * Creates a Base Quiz request
     *
     * @param quizId the id of the quiz to request
     */
    public QuizRequestBase(String quizId) {
        if (quizId == null)
            throw new IllegalArgumentException("id is a required parameter of type String");

        this.quizId = quizId;
        this.section = null;
        this.quizVersionId = null;
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
     * @param quizVersionId The quiz version id will be returned with the first request and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public void setQuizVersionId(String quizVersionId) {
        this.quizVersionId = quizVersionId;
    }

    /**
     * @return the quizVersionId. The quiz version id will be returned with the first request and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-versioning
     */
    public String getQuizVersionId() {
        return quizVersionId;
    }
}
