package io.constructor.client;

/** Constructor.io Natural Language Search Request */
public class NaturalLanguageSearchRequest {

    private String query;
    private String section;
    private int page;
    private int resultsPerPage;

    /**
     * Creates a natural language search request
     *
     * @param query the term to return natural language search results for
     */
    public NaturalLanguageSearchRequest(String query) throws IllegalArgumentException {
        if (query == null) {
            throw new IllegalArgumentException("query is required");
        }

        this.query = query;
        this.section = "Products";
        this.page = 1;
        this.resultsPerPage = 30;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
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
}
