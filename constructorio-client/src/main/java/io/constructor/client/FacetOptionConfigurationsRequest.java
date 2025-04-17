package io.constructor.client;

import io.constructor.client.models.FacetOptionConfiguration;
import java.util.List;

/** Constructor.io Facet Option Configurations Request */
public class FacetOptionConfigurationsRequest {
    private List<FacetOptionConfiguration> facetOptionConfigurations;
    private String facetName;
    private String section;

    /**
     * Creates a facet option configurations request (create or update)
     *
     * @param facetOptionConfigurations the list of facet option configurations to be created or
     *     updated
     * @param facetName the name of the facet
     * @param section the section to which the facet belongs
     */
    public FacetOptionConfigurationsRequest(
            List<FacetOptionConfiguration> facetOptionConfigurations,
            String facetName,
            String section) {
        if (facetOptionConfigurations.isEmpty()) {
            throw new IllegalArgumentException("facetOptionConfigurations is required");
        }
        if (facetName == null || facetName.trim().isEmpty()) {
            throw new IllegalArgumentException("facetName is required");
        }

        this.facetOptionConfigurations = facetOptionConfigurations;
        this.facetName = facetName;
        this.section = section;
    }

    /**
     * Creates a facet option configurations request (Create or Update) with default section
     * "Products"
     *
     * @param facetOptionConfigurations the list of facet option configuration to be created or
     *     updated
     * @param facetName the name of the facet
     */
    public FacetOptionConfigurationsRequest(
            List<FacetOptionConfiguration> facetOptionConfigurations, String facetName) {
        this(facetOptionConfigurations, facetName, "Products");
    }

    /**
     * @return the list of facet option configurations
     */
    public List<FacetOptionConfiguration> getFacetOptionConfigurations() {
        return facetOptionConfigurations;
    }

    /**
     * @param facetOptionConfigurations the list of facet option configurations to set
     */
    public void setFacetOptionConfigurations(
            List<FacetOptionConfiguration> facetOptionConfigurations) {
        this.facetOptionConfigurations = facetOptionConfigurations;
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
