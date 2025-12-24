package io.constructor.client;

import java.util.List;

/**
 * Constructor.io Searchabilities V2 DELETE Request.
 *
 * <p>This request class is used for bulk deletion of v2 searchability configurations
 * (DELETE /v2/searchabilities).
 */
public class SearchabilitiesV2DeleteRequest {
    private List<String> searchabilityNames;
    private String section;
    private Boolean skipRebuild;

    /**
     * Creates a searchabilities v2 DELETE request
     *
     * @param searchabilityNames the list of searchability names to delete
     * @param section the section to which the searchabilities belong
     */
    public SearchabilitiesV2DeleteRequest(List<String> searchabilityNames, String section) {
        if (searchabilityNames == null || searchabilityNames.isEmpty()) {
            throw new IllegalArgumentException("searchabilityNames is required");
        }
        if (section == null) {
            throw new IllegalArgumentException("section is required");
        }

        this.searchabilityNames = searchabilityNames;
        this.section = section;
    }

    /**
     * Creates a searchabilities v2 DELETE request with default section "Products"
     *
     * @param searchabilityNames the list of searchability names to delete
     */
    public SearchabilitiesV2DeleteRequest(List<String> searchabilityNames) {
        this(searchabilityNames, ConstructorIO.DEFAULT_SECTION);
    }

    /** @return the list of searchability names to delete */
    public List<String> getSearchabilityNames() {
        return searchabilityNames;
    }

    /** @param searchabilityNames the searchability names to set */
    public void setSearchabilityNames(List<String> searchabilityNames) {
        this.searchabilityNames = searchabilityNames;
    }

    /** @return the section */
    public String getSection() {
        return section;
    }

    /** @param section the section to set */
    public void setSection(String section) {
        this.section = section;
    }

    /** @return whether to skip index rebuild */
    public Boolean getSkipRebuild() {
        return skipRebuild;
    }

    /** @param skipRebuild whether to skip index rebuild */
    public void setSkipRebuild(Boolean skipRebuild) {
        this.skipRebuild = skipRebuild;
    }
}
