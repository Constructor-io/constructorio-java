package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

/** Constructor.io Server Error ... uses Gson/Reflection to load data in */
public class ServerError {

  @SerializedName("message")
  private String message;

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
