package io.constructor.client;

/**
 * Constructor.io User Info
 */
public class UserInfo {

  private int sessionId;
  private String clientId;

  /**
   * Creates a User Info
   * 
   * @param sessionId the user's Session ID
   * @param clientId the user's Client ID
   */
  public UserInfo(int sessionId, String clientId) {
    this.sessionId = sessionId;
    this.clientId = clientId;
  }

  /**
   * @return Client ID
   */
  public String getClientId() {
    return this.clientId;
  }

  /**
   * @return Session ID
   */
  public int getSessionId() {
    return this.sessionId;
  }
}