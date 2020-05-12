package io.constructor.client;

/**
 * Constructor.io Recommendations Request
 */
public class RecommendationsRequest {

    private String podId;
    private int numResults;
    private String section;

    /**
     * Creates a recommendations request
     *
     * @param podId the pod id to retrieve results from
     * @param numResults the number of results to retrieve
     * @param section the section to retrieve results from
     */
    public RecommendationsRequest(String podId) throws IllegalArgumentException {
      if (podId == null) {
          throw new IllegalArgumentException("podId is a required parameter of type string");
      }

      this.podId = podId;
      this.numResults = 10;
      this.section = "Products";
    }

    /**
     * @param podId the pod id to set
     */
    public void setPodId(String podId) {
      this.podId = podId;
    }

    /**
     * @return the pod id
     */
    public String getPodId() {
      return podId;
    }

    /**
     * @param numResults the num results to set
     */
    public void setNumResults(int numResults) {
      this.numResults = numResults;
    }

    /**
     * @return the num results
     */
    public int getNumResults() {
      return numResults;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
      this.section = section;
    }

    /**
     * @return the section
     */
    public String getSection() {
      return section;
    }
}
