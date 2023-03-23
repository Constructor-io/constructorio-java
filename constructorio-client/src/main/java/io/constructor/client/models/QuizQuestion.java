package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuizQuestion {

  @SerializedName("id")
  private int id;

  @SerializedName("title")
  private String title;

  @SerializedName("type")
  private String type;

  @SerializedName("description")
  private String description;

  @SerializedName("cta_text")
  private String ctaText;

  @SerializedName("images")
  private QuizImages images;

  @SerializedName("input_placeholder")
  private String inputPlaceholder;

  @SerializedName("options")
  private List<QuizOption> options;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the options
   */
  public List<QuizOption> getOptions() {
    return options;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the ctaText
   */
  public String getCtaText() {
    return ctaText;
  }

  /**
   * @return the images
   */
  public QuizImages getImages() {
    return images;
  }

  /**
   * @return the inputPlaceholder
   */
  public String getInputPlaceholder() {
    return inputPlaceholder;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCtaText(String ctaText) {
    this.ctaText = ctaText;
  }

  public void setImages(QuizImages images) {
    this.images = images;
  }

  public void setInputPlaceholder(String inputPlaceholder) {
    this.inputPlaceholder = inputPlaceholder;
  }

  public void setOptions(List<QuizOption> options) {
    this.options = options;
  }
}
