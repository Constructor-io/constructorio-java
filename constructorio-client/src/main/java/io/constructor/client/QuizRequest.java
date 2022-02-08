package io.constructor.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructor.io Next Quiz and Finalize Quiz request
 */
public class QuizRequest {
    private String id;
    private String indexKey;
    private String section;
    private List<List<String>> a;
    private String versionId;

    /**
     * Creates a Next Quiz/Finalize Quiz request
     *
     * @param id the id of the quiz to request
     * @param indexKey the index key of the product catalog
     */
    public QuizRequest(String id, String indexKey) {
        if (id == null)
            throw new IllegalArgumentException("id is a required parameter of type String");
        if (indexKey == null)
            throw new IllegalArgumentException("indexKey is a required parameter of type String");

        this.section = null;
        this.a = new ArrayList<>();
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
     * @param indexKey the index_key/api_key of the product catalog
     */
    public void setIndexKey(String indexKey) {
        this.indexKey = indexKey;
    }

    /**
     * @return the index_key/api_key of the product catalog
     */
    public String getIndexKey() {
        return indexKey;
    }

    /**
     * @param section the section of the product catalog
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the section of the product  catalog
     */
    public String getSection() {
        return section;
    }

    /**
     * @param a the list of answers in the format [ [1,2], [3,4] ] where [1,2] are the selected answers
     *          for the first question and [3,4] are the selected answers for the second question
     */
    public void setA(List<List<String>> a) {
        this.a = a;
    }

    /**
     * @return the list of answers for the quiz
     */
    public List<List<String>> getA() { return a; }

    /**
     * @param versionId the specific version id for the quiz
     */
    public void setVersionId(String versionId) { this.versionId = versionId;}

    /**
     * @return the specific version id for the quiz
     */
    public String getVersionId() { return versionId; }
}
