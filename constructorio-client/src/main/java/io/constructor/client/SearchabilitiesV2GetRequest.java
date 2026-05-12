package io.constructor.client;

/**
 * Constructor.io Searchabilities V2 GET Request.
 *
 * <p>This request class is used for listing v2 searchability configurations (GET
 * /v2/searchabilities).
 */
public class SearchabilitiesV2GetRequest {
    private String section;

    // Pagination parameters
    private Integer page;
    private Integer numResultsPerPage;
    private Integer offset;

    // Filter parameters
    private String name;
    private Boolean fuzzySearchable;
    private Boolean exactSearchable;
    private Boolean displayable;
    private String matchType;
    private String sortBy;
    private String sortOrder;

    /**
     * Creates a searchabilities v2 GET request
     *
     * @param section the section to which the searchabilities belong
     */
    public SearchabilitiesV2GetRequest(String section) {
        if (section == null || section.trim().isEmpty()) {
            throw new IllegalArgumentException("section is required");
        }
        this.section = section;
    }

    /** Creates a searchabilities v2 GET request with default section "Products" */
    public SearchabilitiesV2GetRequest() {
        this.section = ConstructorIO.DEFAULT_SECTION;
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
     * @return the page number
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page number to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the number of results per page
     */
    public Integer getNumResultsPerPage() {
        return numResultsPerPage;
    }

    /**
     * @param numResultsPerPage the number of results per page to set
     */
    public void setNumResultsPerPage(Integer numResultsPerPage) {
        this.numResultsPerPage = numResultsPerPage;
    }

    /**
     * @return the offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return the name filter
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name filter to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the fuzzy searchable filter
     */
    public Boolean getFuzzySearchable() {
        return fuzzySearchable;
    }

    /**
     * @param fuzzySearchable the fuzzy searchable filter to set
     */
    public void setFuzzySearchable(Boolean fuzzySearchable) {
        this.fuzzySearchable = fuzzySearchable;
    }

    /**
     * @return the exact searchable filter
     */
    public Boolean getExactSearchable() {
        return exactSearchable;
    }

    /**
     * @param exactSearchable the exact searchable filter to set
     */
    public void setExactSearchable(Boolean exactSearchable) {
        this.exactSearchable = exactSearchable;
    }

    /**
     * @return the displayable filter
     */
    public Boolean getDisplayable() {
        return displayable;
    }

    /**
     * @param displayable the displayable filter to set
     */
    public void setDisplayable(Boolean displayable) {
        this.displayable = displayable;
    }

    /**
     * @return the match type filter
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * @param matchType the match type filter to set
     */
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    /**
     * @return the sort by field
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sort by field to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the sort order
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sort order to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
