package io.constructor.client;

import java.io.File;
import java.util.Map;

/**
 * Constructor.io Catalog Request
 */
public class CatalogRequest {
  private Map<String, File> files;
  private String section;
  private String notificationEmail;
  private Boolean force;

  /**
   * Creates a catalog request
   *
   * @param files the files to be uploaded
   */
  public CatalogRequest(Map<String, File> files) {
    if (files == null) {
      throw new IllegalArgumentException("files is required");
    }

    this.files = files;
    this.section = "Products";
  }

  /**
   * @param files the files to be uploaded (items, variations, item_groups)
   */
  public void setFiles(Map<String, File> files) {
    this.files = files;
  }

  /**
   * @return the files to be uploaded
   */
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

  /**
   * @return the notification email
   */
  public String getNotificationEmail() {
    return notificationEmail;
  }

  /**
   * @param force true if the catalog should be processed even if it will invalidate a large number of existing items
   */
  public void setForce(Boolean force) {
    this.force = force;
  }

  /**
   * @return the force flag
   */
  public Boolean getForce() {
    return force;
  }
}
