package io.constructor.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructor.io Variations Request
 */
public class VariationsRequest {
    private String section;
    private int page;
    private int resultsPerPage;
    private List<String> ids;
    private String itemId;
    
    /**
     * Creates an variations request
     *
     */
    public VariationsRequest() {
      this.section = "Products";
      this.page = 1;
      this.resultsPerPage = 30;
      this.ids = new ArrayList<String>();
    }

    /**
     * @return the section
     */
    public String getSection() {
      return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
      this.section = section;
    }

    /**
     * @return the page
     */
    public int getPage() {
      return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
      this.page = page;
    }

    /**
     * @return the resultsPerPage
     */
    public int getResultsPerPage() {
      return resultsPerPage;
    }

    /**
     * @param resultsPerPage the resultsPerPage to set
     */
    public void setResultsPerPage(int resultsPerPage) {
      this.resultsPerPage = resultsPerPage;
    }

    /**
     * @return the ids
     */
    public List<String> getIds() {
      return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<String> ids) {
      this.ids = ids;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
      return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
      this.itemId = itemId;
    }
}
