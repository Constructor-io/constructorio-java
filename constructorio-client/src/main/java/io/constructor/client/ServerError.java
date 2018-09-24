package io.constructor.client;

import com.google.gson.annotations.SerializedName;

/**
 * Constructor.io Server Error ... uses Gson/Reflection to load data in
 */
public class ServerError {

  @SerializedName("message")
  private String message;

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }
}