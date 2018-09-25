package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Result Data ... uses Gson/Reflection to load data in
 */

public class ResultData {

  @SerializedName("description")
  private String description;

  @SerializedName("id")
  private String id;

  @SerializedName("url")
  private String url;

  @SerializedName("image_url")
  private String imageUrl;

  @SerializedName("groups")
  private List<ResultGroup> groups;

  @SerializedName("facets")
  private List<ResultFacet> facets;

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the imageUrl
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * @return the groups
   */
  public List<ResultGroup> getGroups() {
    return groups;
  }

  /**
   * @return the facets
   */
  public List<ResultFacet> getFacets() {
    return facets;
  }
}