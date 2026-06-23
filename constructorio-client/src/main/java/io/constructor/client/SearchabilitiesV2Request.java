package io.constructor.client;

import io.constructor.client.models.SearchabilityV2;
import java.util.List;

/**
 * Constructor.io Searchabilities V2 Request.
 *
 * <p>This request class is used for bulk v2 searchability configuration operations (PATCH
 * /v2/searchabilities).
 *
 * <p>For GET operations, use {@link SearchabilitiesV2GetRequest}. For DELETE operations, use {@link
 * SearchabilitiesV2DeleteRequest}.
 */
public class SearchabilitiesV2Request {
    private List<SearchabilityV2> searchabilities;
    private String section;
    private Boolean skipRebuild;

    /**
     * Creates a searchabilities v2 request for bulk update (PATCH)
     *
     * @param searchabilities the list of searchability configurations
     * @param section the section to which the searchabilities belong
     */
    public SearchabilitiesV2Request(List<SearchabilityV2> searchabilities, String section) {
        if (searchabilities == null || searchabilities.isEmpty()) {
            throw new IllegalArgumentException("searchabilities is required");
        }
        if (section == null || section.trim().isEmpty()) {
            throw new IllegalArgumentException("section is required");
        }

        this.searchabilities = searchabilities;
        this.section = section;
    }

    /**
     * Creates a searchabilities v2 request with default section "Products"
     *
     * @param searchabilities the list of searchability configurations
     */
    public SearchabilitiesV2Request(List<SearchabilityV2> searchabilities) {
        this(searchabilities, ConstructorIO.DEFAULT_SECTION);
    }

    /**
     * @return the list of searchability configurations
     */
    public List<SearchabilityV2> getSearchabilities() {
        return searchabilities;
    }

    /**
     * @param searchabilities the searchabilities to set
     */
    public void setSearchabilities(List<SearchabilityV2> searchabilities) {
        this.searchabilities = searchabilities;
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
     * @return whether to skip index rebuild
     */
    public Boolean getSkipRebuild() {
        return skipRebuild;
    }

    /**
     * @param skipRebuild whether to skip index rebuild
     */
    public void setSkipRebuild(Boolean skipRebuild) {
        this.skipRebuild = skipRebuild;
    }
}
