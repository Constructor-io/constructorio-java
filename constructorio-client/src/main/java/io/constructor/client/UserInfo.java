package io.constructor.client;

import java.util.List;

/**
 * Constructor.io User Info
 */
public class UserInfo {

  private int sessionId;
  private String clientId;
  private String userId;
  private List<String> userSegments;
  private String forwardedFor;

  /**
   * Creates a User Info object
   * 
   * @param sessionId the user's Session ID
   * @param clientId the user's Client ID
   */
  public UserInfo(final int sessionId, final String clientId) {
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
   * @return User ID
   */
  public String getUserId() {
    return this.userId;
  }

  /**
   * @return User Segments
   */
  public List<String> getUserSegments() {
    return userSegments;
  }

  /**
   * @return Forwarding info for the end user's device if proxying requests
   */
  public String getForwardedFor() {
    return this.forwardedFor;
  }

  /**
   * Validates and sets the session id
   *
   * @param sessionId the user's session id
   */
  public void setSessionId(final int sessionId) throws IllegalArgumentException {
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
  public void setClientId(final String clientId) throws IllegalArgumentException {
    if (clientId == null || clientId.trim().isEmpty()) {
      throw new IllegalArgumentException("Client ID cannot be null or an empty string.");
    } else {
      this.clientId = clientId;
    }
  }

  /**
   * Validates and sets the the user id according to the API consumer (for logged
   * in users)
   *
   * @param userId the user's id
   */
  public void setUserId(final String userId) throws IllegalArgumentException {
    if (userId == null || userId.trim().isEmpty()) {
      throw new IllegalArgumentException("User ID cannot be an empty string.");
    } else {
      this.userId = userId;
    }
  }

  /**
   * Validates and sets the the user segments according to the API consumer
   * 
   * @param userSegments the userSegments to set
   */
  public void setUserSegments(final List<String> userSegments) {
    if (userSegments == null) {
      throw new IllegalArgumentException("User segments cannot be null.");
    } else {
      this.userSegments = userSegments;
    }
  }

  /**
   * Validates and sets forwarding info
   *
   * @param forwardedFor the user's forwarding info
   */
  public void setForwardedFor(final String forwardedFor) throws IllegalArgumentException {
    if (forwardedFor == null || forwardedFor.trim().isEmpty()) {
      throw new IllegalArgumentException("Forwarded for cannot be null or an empty string.");
    } else {
      this.forwardedFor = forwardedFor;
    }
  }
}