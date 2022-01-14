package io.constructor.client;

/**
 * Constructor.io Get All Tasks Request
 */
public class AllTasksRequest {
    private int resultsPerPage;
    private int page;

    /**
     * Creates a All Tasks request
     *
     */
    public AllTasksRequest() {
        this.page = 1;
        this.resultsPerPage = 20;
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

}
