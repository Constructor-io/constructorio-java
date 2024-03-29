package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Browse Facet Options Response Inner ... uses Gson/Reflection to load data in */
public class BrowseFacetOptionsResponseInner {

    @SerializedName("facets")
    List<FilterFacet> facets;

    /**
     * @return the facets
     */
    public List<FilterFacet> getFacets() {
        return facets;
    }

    /**
     * @param facets the facets
     */
    public void setFacets(List<FilterFacet> facets) {
        this.facets = facets;
    }
}
