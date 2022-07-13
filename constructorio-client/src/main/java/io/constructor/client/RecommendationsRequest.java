package io.constructor.client;

import java.util.List;

/**
 * Constructor.io Recommendations Request
 */
public class RecommendationsRequest {

    private String podId;
    private int numResults;
    private List<String> itemIds;
    private String section;
    private VariationsMap variationsMap;

    /**
     * Creates a recommendations request
     *
     * @param podId the pod id to retrieve results from
     */
    public RecommendationsRequest(String podId) throws IllegalArgumentException {
      if (podId == null) {
          throw new IllegalArgumentException("podId is a required parameter of type string");
      }

      this.podId = podId;
      this.numResults = 10;
      this.itemIds = null;
      this.section = "Products";
      this.variationsMap = null;
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
     * @param itemIds the item id's to set
     */
    public void setItemIds(List<String> itemIds) {
      this.itemIds = itemIds;
  }

    /**
     * @return the item id's
     */
    public List<String> getItemIds() {
      return itemIds;
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

    /**
     * @param variationsMap the variationsMap to set
     */
    public void setVariationsMap(VariationsMap variationsMap) {
        this.variationsMap = variationsMap;
    }

    /**
     * @return the variations map
     */
    public VariationsMap getVariationsMap() {
        return variationsMap;
    }
}
