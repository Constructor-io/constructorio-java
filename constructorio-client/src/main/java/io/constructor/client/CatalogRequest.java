package io.constructor.client;

import java.io.File;
import java.util.Map;

public class CatalogRequest {
  private Map<String, File> files;
  private String section;
  private String notificationEmail;
  private Boolean force;

  public CatalogRequest(Map<String, File> files) {
    if (files == null) {
      throw new IllegalArgumentException("files is required");
    }

    this.files = files;
    this.section = "Products";
  }

  public void setFiles(Map<String, File> files) {
    this.files = files;
  }

  public Map<String, File> getFiles() {
    return files;
  }

  /**
   * @param section the section to set
   */
  public void setSection(String section) {
    this.section = section;
  }

  /**
   * @return the notification email
   */
  public String getSection() {
    return section;
  }

  /**
   * @param email the email address where you'd like to receive a notifcation in case the task fails
   */
  public void setNotificationEmail(String email) {
    this.notificationEmail = email;
  }

  public void setForce(Boolean force) {
    this.force = force;
  }

  public Boolean getForce() {
    return force;
  }

  /**
   * @return the notification email
   */
  public String getNotificationEmail() {
    return notificationEmail;
  }
}
