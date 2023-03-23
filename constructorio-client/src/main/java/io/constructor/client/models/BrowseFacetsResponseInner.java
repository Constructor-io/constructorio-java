package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Browse Facet Response Inner ... uses Gson/Reflection to load data in */
public class BrowseFacetsResponseInner {

  @SerializedName("facets")
  List<FilterFacet> facets;

  @SerializedName("total_num_results")
  private Integer totalNumberOfResults;

  /**
   * @return the facets
   */
  public List<FilterFacet> getFacets() {
    return facets;
  }

  /**
   * @return the totalNumberOfResults
   */
  public Integer getTotalNumberOfResults() {
    return totalNumberOfResults;
  }

  public void setFacets(List<FilterFacet> facets) {
    this.facets = facets;
  }

  public void setTotalNumberOfResults(Integer totalNumberOfResults) {
    this.totalNumberOfResults = totalNumberOfResults;
  }
}
