package io.constructor.client.models;

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
    private List<Result> results;

    @SerializedName("total_num_results")
    private Integer totalNumberOfResults;

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
    public List<Result> getResults() {
      return results;
    }

    /**
     * @return the totalNumberOfResults
     */
    public Integer getTotalNumberOfResults() {
      return totalNumberOfResults;
    }
}