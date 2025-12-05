package io.constructor.client;

/** Constructor.io Sort Option Get Request */
public class SortOptionGetRequest {
    private String section;
    private Integer page;
    private Integer resultsPerPage;
    private Integer offset;
    private String sortBy;

    /**
     * @param sortBy the optional filter by sortBy field (can be null)
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the optional filter by sortBy field (can be null)
     */
    public String getSortBy() {
        return sortBy;
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
    public Integer getPage() {
        return page;
    }

    /**
     * @return the resultsPerPage
     */
    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * @param resultsPerPage the resultsPerPage to set
     */
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
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
    public Integer getOffset() {
        return offset;
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
}
