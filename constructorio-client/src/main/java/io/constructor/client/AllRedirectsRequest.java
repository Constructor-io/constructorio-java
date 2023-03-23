package io.constructor.client;

/** Constructor.io Get All Redirects Request */
public class AllRedirectsRequest {
  private int resultsPerPage;
  private int page;
  private int offset;
  private String status;
  private String query;

  /** Creates a All Redirects request */
  public AllRedirectsRequest() {}

  /**
   * @param page the page of results to return
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   * @return the page of results to return
   */
  public int getPage() {
    return page;
  }

  /**
   * @param resultsPerPage the number of redirects to return - maximum value 100
   */
  public void setResultsPerPage(int resultsPerPage) {
    this.resultsPerPage = resultsPerPage;
  }

  /**
   * @return the results per page
   */
  public int getResultsPerPage() {
    return resultsPerPage;
  }

  /**
   * @param offset the offset of results to return (can't be used with page)
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * @return the offset of results to return
   */
  public int getOffset() {
    return offset;
  }

  /**
   * @param status The status of redirects to return - 'current', 'pending', 'expired'
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @return the query
   */
  public String getQuery() {
    return query;
  }

  /**
   * @param query The query to use to filter against match pattern of redirects
   */
  public void setQuery(String query) {
    this.query = query;
  }
}
