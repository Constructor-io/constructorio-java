package io.constructor.client;

import io.constructor.client.models.FacetOptionConfiguration;

/** Constructor.io Facet Option Configuration Request */
public class FacetOptionConfigurationRequest {
    private FacetOptionConfiguration facetOptionConfiguration;
    private String facetName;
    private String section;

    /**
     * Creates a facet option configuration request
     *
     * @param facetOptionConfiguration the facet option configuration to be created
     * @param facetName the name of the facet
     * @param section the section to which the facet belongs
     */
    public FacetOptionConfigurationRequest(
            FacetOptionConfiguration facetOptionConfiguration, String facetName, String section) {
        if (facetOptionConfiguration == null) {
            throw new IllegalArgumentException("facetOptionConfiguration is required");
        }
        if (facetName == null || facetName.trim().isEmpty()) {
            throw new IllegalArgumentException("facetName is required");
        }

        this.facetOptionConfiguration = facetOptionConfiguration;
        this.facetName = facetName;
        this.section = section;
    }

    /**
     * Creates a facet option configuration request with default section "Products"
     *
     * @param facetOptionConfiguration the facet option configuration to be created
     * @param facetName the name of the facet
     */
    public FacetOptionConfigurationRequest(
            FacetOptionConfiguration facetOptionConfiguration, String facetName) {
        this(facetOptionConfiguration, facetName, "Products");
    }

    /**
     * @return the facet option configuration
     */
    public FacetOptionConfiguration getFacetOptionConfiguration() {
        return facetOptionConfiguration;
    }

    /**
     * @param facetOptionConfiguration the facet option configuration to set
     */
    public void setFacetOptionConfiguration(FacetOptionConfiguration facetOptionConfiguration) {
        this.facetOptionConfiguration = facetOptionConfiguration;
    }

    /**
     * @return the facet name
     */
    public String getFacetName() {
        return facetName;
    }

    /**
     * @param facetName the facet name to set
     */
    public void setFacetName(String facetName) {
        this.facetName = facetName;
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
