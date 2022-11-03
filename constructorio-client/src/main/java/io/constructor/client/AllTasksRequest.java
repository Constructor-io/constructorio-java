package io.constructor.client;

/**
 * Constructor.io Get All Tasks Request
 */
public class AllTasksRequest {
    private int resultsPerPage;
    private int page;
    private String startDate;
    private String endDate;
    private String status;
    private String type;

    /**
     * Creates a All Tasks request
     *
     */
    public AllTasksRequest() {
        this.page = 1;
    }

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
     * @param resultsPerPage the number of tasks to return - maximum value 100
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
     * @param startDate The start date of results to return - YYYY-MM-DD
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return startDate The start date of results to return - YYYY-MM-DD
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param endDate The end date of results to return - YYYY-MM-DD
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return endDate The end date of results to return - YYYY-MM-DD
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param status The status of tasks to return - 'QUEUED', 'IN_PROGRESS', 'DONE', 'FAILED', 'CANCELED'
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return status The status of tasks to return - 'QUEUED', 'IN_PROGRESS', 'DONE', 'FAILED', 'CANCELED'
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * @return type The type of results to return - 'ingestion', 'user_data_request'
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type of results to return - 'ingestion', 'user_data_request'
     */
  	public void setType(String type) {
        this.type = type;
    }
}
