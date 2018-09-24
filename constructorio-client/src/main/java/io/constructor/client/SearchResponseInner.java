package io.constructor.client;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Search Response Inner ... uses Gson/Reflection to load data in
 */
public class SearchResponseInner {

    @SerializedName("facets")
    List<SearchFacet> facets;

    @SerializedName("groups")
    private List<SearchGroup> groups;

    @SerializedName("results")
    private List<SearchResult> results;

    @SerializedName("total_num_results")
    private int totalNumberOfResults;

    /**
     * @return the facets
     */
    public List<SearchFacet> getFacets() {
      return facets;
    }

    /**
     * @return the groups
     */
    public List<SearchGroup> getGroups() {
      return groups;
    }

    /**
     * @return the results
     */
    public List<SearchResult> getResults() {
      return results;
    }

    /**
     * @return the totalNumberOfResults
     */
    public int getTotalNumberOfResults() {
      return totalNumberOfResults;
    }
}