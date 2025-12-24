package io.constructor.client;

import io.constructor.client.models.FacetConfigurationV2;

/**
 * Constructor.io FacetConfiguration V2 Request.
 *
 * <p>This request class is used for v2 facet configuration operations which require
 * path_in_metadata to be specified.
 */
public class FacetConfigurationV2Request {
    private FacetConfigurationV2 facetConfiguration;
    private String section;

    /**
     * Creates a facet configuration v2 request
     *
     * @param facetConfiguration the facet configuration to be created/updated
     * @param section the section to which the facet belongs
     */
    public FacetConfigurationV2Request(FacetConfigurationV2 facetConfiguration, String section) {
        if (facetConfiguration == null) {
            throw new IllegalArgumentException("facetConfiguration is required");
        }
        if (section == null) {
            throw new IllegalArgumentException("section is required");
        }

        this.facetConfiguration = facetConfiguration;
        this.section = section;
    }

    /**
     * Creates a facet configuration v2 request with default section "Products"
     *
     * @param facetConfiguration the facet configuration to be created/updated
     */
    public FacetConfigurationV2Request(FacetConfigurationV2 facetConfiguration) {
        this(facetConfiguration, ConstructorIO.DEFAULT_SECTION);
    }

    /** @param facetConfiguration the facet configuration to be created/updated */
    public void setFacetConfiguration(FacetConfigurationV2 facetConfiguration) {
        this.facetConfiguration = facetConfiguration;
    }

    /** @return the facet configuration to be created/updated */
    public FacetConfigurationV2 getFacetConfiguration() {
        return facetConfiguration;
    }

    /** @param section the section to set */
    public void setSection(String section) {
        this.section = section;
    }

    /** @return the section */
    public String getSection() {
        return section;
    }
}
