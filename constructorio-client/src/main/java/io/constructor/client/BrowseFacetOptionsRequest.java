package io.constructor.client;

import java.util.HashMap;
import java.util.Map;

/** Constructor.io Browse Facet options Request */
public class BrowseFacetOptionsRequest {

    private String facetName;
    private Map<String, String> formatOptions;

    /** Creates a browse facet options request */
    public BrowseFacetOptionsRequest(String facetName) throws IllegalArgumentException {
        this.facetName = facetName;
        this.formatOptions = new HashMap<String, String>();
    }

    /**
     * @return the facetName
     */
    public String getFacetName() {
        return facetName;
    }

    /**
     * @param facetName the facetName to set
     */
    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    /**
     * @param formatOptions the formatOptions to set
     */
    public void setFormatOptions(Map<String, String> formatOptions) {
        this.formatOptions = formatOptions;
    }

    /**
     * @return the format options
     */
    public Map<String, String> getFormatOptions() {
        return formatOptions;
    }
}
