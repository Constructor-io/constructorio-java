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
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * @return the specific version id for the quiz
     */
    public String getVersionId() {
        return versionId;
    }
}
