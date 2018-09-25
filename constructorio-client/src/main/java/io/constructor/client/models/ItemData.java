package io.constructor.client.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Item Data ... uses Gson/Reflection to load data in
 */

public class ItemData {

  @SerializedName("description")
  private String description;

  @SerializedName("id")
  private String id;

  @SerializedName("url")
  private String url;

  @SerializedName("image_url")
  private String imageUrl;

  @SerializedName("groups")
  private List<ItemGroup> groups;

  @SerializedName("facets")
  private List<ItemFacet> facets;

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
  public List<ItemGroup> getGroups() {
    return groups;
  }

  /**
   * @return the facets
   */
  public List<ItemFacet> getFacets() {
    return facets;
  }
}