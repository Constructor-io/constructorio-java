package io.constructor.client.models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor Item ... uses Gson/Reflection to load data in
 */
public class Item {

  @SerializedName("id")
  private String id;
  
  @SerializedName("name")
  private String name;
  
  @SerializedName("description")
  private String description;
  
  @SerializedName("url")
  private String url;
  
  @SerializedName("image_url")
  private String imageUrl;

  @SerializedName("suggested_score")
  private Integer suggestedScore;

  @SerializedName("keywords")
  private List<String> keywords;

  @SerializedName("group_ids")
  private List<String> groupIds;

  @SerializedName("facets")
  private Map<String, Object> facets;

  @SerializedName("metadata")
  private Map<String, Object> metadata;

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * @return the product url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the image url
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * @return the suggested score
   */
  public Integer getSuggestedScore() {
    return suggestedScore;
  }
  
  /**
   * @return the keywords
   */
  public List<String> getKeywords() {
    return keywords;
  }

  /**
   * @return the group ids
   */
  public List<String> getGroupIds() {
    return groupIds;
  }

  /**
   * @return the facets
   */
  public Map<String, Object> getFacets() {
    return facets;
  }

  /**
   * @return the metadata
   */
  public Map<String, Object> getMetadata() {
    return metadata;
  }
}