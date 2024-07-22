package io.constructor.client;

import io.constructor.client.models.FacetConfiguration;

/** Constructor.io FacetConfiguration Request */
public class FacetConfigurationRequest {
    private FacetConfiguration facetConfiguration;
    private String section;

    /**
     * Creates a catalog request
     *
     * @param facetConfiguration the facet configuration to be created/updated
     * @param section the autocomplete section to upload the files to
     */
    public FacetConfigurationRequest(FacetConfiguration facetConfiguration, String section) {
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
     * @param facetConfiguration the facet configuration to be created/updated
     */
    public void setFacetConfiguration(FacetConfiguration facetConfiguration) {
        this.facetConfiguration = facetConfiguration;
    }

    /**
     * @return the facet configuration to be created/updated
     */
    public FacetConfiguration geFacetConfiguration() {
        return facetConfiguration;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the section
     * 
     */
    public String getSection() {
        return section;
    }
}
