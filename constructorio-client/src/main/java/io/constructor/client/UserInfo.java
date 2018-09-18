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
    this.setSessionId(sessionId);
    this.setClientId(clientId);
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

  /**
   * Validates and sets the session id
   *
   * @param sessionId the user's session id
   */
  private void setSessionId(int sessionId) throws IllegalArgumentException {
    if (sessionId <= 0) {
      throw new IllegalArgumentException("Session ID cannot be less than or equal to 0.");
    } else {
      this.sessionId = sessionId;
    }
  }

  /**
   * Validates and sets the client id
   *
   * @param clientId the user's client id
   */
  private void setClientId(String clientId) throws IllegalArgumentException {
    if (clientId == null || clientId.trim().isEmpty()) {
      throw new IllegalArgumentException("Client ID cannot be null or an empty string.");
    } else {
      this.clientId = clientId;
    }
  }
}