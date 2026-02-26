package io.constructor.client;

/**
 * Constructor.io Facet Configurations V2 GET Request.
 *
 * <p>This request class is used for listing v2 facet configurations (GET /v2/facets).
 */
public class FacetConfigurationsV2GetRequest {
    private String section;

    // Pagination parameters
    private Integer page;
    private Integer numResultsPerPage;
    private Integer offset;

    /**
     * Creates a facet configurations v2 GET request
     *
     * @param section the section to which the facet configurations belong
     */
    public FacetConfigurationsV2GetRequest(String section) {
        if (section == null || section.trim().isEmpty()) {
            throw new IllegalArgumentException("section is required");
        }
        this.section = section;
    }

    /** Creates a facet configurations v2 GET request with default section "Products" */
    public FacetConfigurationsV2GetRequest() {
        this.section = ConstructorIO.DEFAULT_SECTION;
    }

    /** @return the section */
    public String getSection() {
        return section;
    }

    /** @param section the section to set */
    public void setSection(String section) {
        this.section = section;
    }

    /** @return the page number */
    public Integer getPage() {
        return page;
    }

    /** @param page the page number to set */
    public void setPage(Integer page) {
        this.page = page;
    }

    /** @return the number of results per page */
    public Integer getNumResultsPerPage() {
        return numResultsPerPage;
    }

    /** @param numResultsPerPage the number of results per page to set */
    public void setNumResultsPerPage(Integer numResultsPerPage) {
        this.numResultsPerPage = numResultsPerPage;
    }

    /** @return the offset */
    public Integer getOffset() {
        return offset;
    }

    /** @param offset the offset to set */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
