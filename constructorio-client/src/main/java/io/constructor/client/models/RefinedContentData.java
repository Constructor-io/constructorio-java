package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class RefinedContentData {
  
  @SerializedName("body")
  private String body;

  @SerializedName("header")
  private String header;

  @SerializedName("altText")
  private String altText;
  
  @SerializedName("ctaLink")
  private String ctaLink;

  @SerializedName("ctaText")
  private String ctaText;

  @SerializedName("assetUrl")
  private String assetUrl;

  @SerializedName("mobileAssetUrl")
  private String mobileAssetUrl;

  @SerializedName("mobileAssetAltText")
  private String mobileAssetAltText;

  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }

  /**
   * @return the header
   */
  public String getHeader() {
    return header;
  }

  /**
   * @return the alt text
   */
  public String getAltText() {
    return altText;
  }

  /**
   * @return the CTA link
   */
  public String getCtaLink() {
    return ctaLink;
  }

  /**
   * @return the CTA text
   */
  public String getCtaText() {
    return ctaText;
  }

  /**
   * @return the asset url
   */
  public String getAssetUrl() {
    return assetUrl;
  }

  /**
   * @return the mobile asset url
   */
  public String getMobileAssetUrl() {
    return mobileAssetUrl;
  }

  /**
   * @return the mobile asset alt text
   */
  public String getMobileAssetAltText() {
    return mobileAssetAltText;
  }
}
