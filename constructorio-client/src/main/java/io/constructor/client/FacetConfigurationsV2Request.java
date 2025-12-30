package io.constructor.client;

import io.constructor.client.models.FacetConfigurationV2;
import java.util.List;

/**
 * Constructor.io FacetConfigurations V2 Request.
 *
 * <p>This request class is used for bulk v2 facet configuration operations (PATCH /v2/facets).
 */
public class FacetConfigurationsV2Request {
    private List<FacetConfigurationV2> facetConfigurations;
    private String section;

    /**
     * Creates a facet configurations v2 request for bulk update
     *
     * @param facetConfigurations the list of facet configurations to be updated
     * @param section the section to which the facets belong
     */
    public FacetConfigurationsV2Request(
            List<FacetConfigurationV2> facetConfigurations, String section) {
        if (facetConfigurations == null || facetConfigurations.isEmpty()) {
            throw new IllegalArgumentException("facetConfigurations is required");
        }
        if (section == null) {
            throw new IllegalArgumentException("section is required");
        }

        this.facetConfigurations = facetConfigurations;
        this.section = section;
    }

    /**
     * Creates a facet configurations v2 request with default section "Products"
     *
     * @param facetConfigurations the list of facet configurations to be updated
     */
    public FacetConfigurationsV2Request(List<FacetConfigurationV2> facetConfigurations) {
        this(facetConfigurations, ConstructorIO.DEFAULT_SECTION);
    }

    /**
     * @return the list of facet configurations
     */
    public List<FacetConfigurationV2> getFacetConfigurations() {
        return facetConfigurations;
    }

    /**
     * @param facetConfigurations the facet configurations to set
     */
    public void setFacetConfigurations(List<FacetConfigurationV2> facetConfigurations) {
        this.facetConfigurations = facetConfigurations;
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
