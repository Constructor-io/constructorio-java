package io.constructor.client;

import java.util.HashMap;
import java.util.Map;

/** Constructor.io Browse Facets Request */
public class BrowseFacetsRequest {

  private int page;
  private int resultsPerPage;
  private Map<String, String> formatOptions;

  /** Creates a browse facets request */
  public BrowseFacetsRequest() throws IllegalArgumentException {
    this.page = 1;
    this.resultsPerPage = 20;
    this.formatOptions = new HashMap<String, String>();
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
   * @param formatOptions the formatOptions to set
   */
  public void setFormatOptions(Map<String, String> formatOptions) {
    this.formatOptions = formatOptions;
  }

  /**
   * @return the format options
   */
  public Map<String, String> getFormatOptions() {
    return formatOptions;
  }
}
