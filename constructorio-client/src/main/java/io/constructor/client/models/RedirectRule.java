package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Redirect Rule ... uses Gson/Reflection to load data in */
public class RedirectRule {

  @SerializedName("id")
  private int id;

  @SerializedName("start_time")
  private String startTime;

  @SerializedName("end_time")
  private String endTime;

  @SerializedName("user_segments")
  private List<String> userSegments;

  @SerializedName("metadata")
  private Object metadata;

  @SerializedName("url")
  private String url;

  @SerializedName("matches")
  private List<RedirectRuleMatch> matches;

  /**
   * @return the id of the redirect
   */
  public int getId() {
    return id;
  }

  /**
   * @return the start_time of the redirect
   */
  public String getStartTime() {
    return startTime;
  }

  /**
   * @return the end_time of the redirect
   */
  public String getEndTime() {
    return endTime;
  }

  /**
   * @return the user_segments of the redirect
   */
  public List<String> getUserSegments() {
    return userSegments;
  }

  /**
   * @return the metadata of the redirect
   */
  public Object getMetadata() {
    return metadata;
  }

  /**
   * @return the url of the redirect
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the matches of the redirect
   */
  public List<RedirectRuleMatch> getMatches() {
    return matches;
  }

  public void setMatches(List<RedirectRuleMatch> matches) {
    this.matches = matches;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMetadata(Object metadata) {
    this.metadata = metadata;
  }

  public void setUserSegments(List<String> userSegments) {
    this.userSegments = userSegments;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
}
