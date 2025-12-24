package io.constructor.client;

import io.constructor.client.models.SearchabilityV2;

/**
 * Constructor.io Searchability V2 Request.
 *
 * <p>This request class is used for single v2 searchability configuration operations (GET/PATCH/DELETE
 * /v2/searchabilities/{name}).
 */
public class SearchabilityV2Request {
    private SearchabilityV2 searchability;
    private String name;
    private String section;
    private Boolean skipRebuild;

    /**
     * Creates a searchability v2 request
     *
     * @param searchability the searchability configuration to be created/updated
     * @param name the name of the searchability field
     * @param section the section to which the searchability belongs
     */
    public SearchabilityV2Request(SearchabilityV2 searchability, String name, String section) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name is required");
        }
        if (section == null) {
            throw new IllegalArgumentException("section is required");
        }

        this.searchability = searchability;
        this.name = name;
        this.section = section;
    }

    /**
     * Creates a searchability v2 request with default section "Products"
     *
     * @param searchability the searchability configuration to be created/updated
     * @param name the name of the searchability field
     */
    public SearchabilityV2Request(SearchabilityV2 searchability, String name) {
        this(searchability, name, ConstructorIO.DEFAULT_SECTION);
    }

    /**
     * Creates a searchability v2 request for GET/DELETE operations (no body needed)
     *
     * @param name the name of the searchability field
     * @param section the section to which the searchability belongs
     */
    public SearchabilityV2Request(String name, String section) {
        this(null, name, section);
    }

    /**
     * Creates a searchability v2 request for GET/DELETE operations with default section
     *
     * @param name the name of the searchability field
     */
    public SearchabilityV2Request(String name) {
        this(null, name, ConstructorIO.DEFAULT_SECTION);
    }

    /** @return the searchability configuration */
    public SearchabilityV2 getSearchability() {
        return searchability;
    }

    /** @param searchability the searchability configuration to set */
    public void setSearchability(SearchabilityV2 searchability) {
        this.searchability = searchability;
    }

    /** @return the name of the searchability field */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
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
