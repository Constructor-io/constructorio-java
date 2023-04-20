package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Base Result ... uses Gson/Reflection to load data in */
public class BaseResultsResponse {

    @SerializedName("facets")
    private List<FilterFacet> facets;

    @SerializedName("groups")
    private List<FilterGroup> groups;

    @SerializedName("results")
    private List<Result> results;

    @SerializedName("total_num_results")
    private Integer totalNumberOfResults;

    @SerializedName("sort_options")
    private List<FilterSortOption> sortOptions;

    @SerializedName("result_sources")
    private ResultSources resultSources;

    /**
     * @return the facets
     */
    public List<FilterFacet> getFacets() {
        return facets;
    }

    /**
     * @return the groups
     */
    public List<FilterGroup> getGroups() {
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

    /**
     * @return the sort options
     */
    public List<FilterSortOption> getSortOptions() {
        return sortOptions;
    }

    /**
     * @return resultSources data
     */
    public ResultSources getResultSources() {
        return resultSources;
    }

    public void setFacets(List<FilterFacet> facets) {
        this.facets = facets;
    }

    public void setGroups(List<FilterGroup> groups) {
        this.groups = groups;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void setTotalNumberOfResults(Integer totalNumberOfResults) {
        this.totalNumberOfResults = totalNumberOfResults;
    }

    public void setSortOptions(List<FilterSortOption> sortOptions) {
        this.sortOptions = sortOptions;
    }

    public void setResultSources(ResultSources resultSources) {
        this.resultSources = resultSources;
    }
}
