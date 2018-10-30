package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserInfoTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void newWithNullClientIdShouldFail() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new UserInfo(3, null, "user-id-123");
  }

  @Test
  public void newWithInvalidSessionIdShouldFail() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new UserInfo(-1, "c62a-2a09-faie", "user-id-123");
  }

  @Test
  public void newWittNullUserIdShouldFail() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new UserInfo(3, "c62a-2a09-faie", null);
  }

  @Test
  public void newShouldReturnUserInfo() throws Exception {
    String clientId = "c62a-2a09-faie";
    int sessionId = 3;
    String userId = "user-id-123";
    UserInfo userInfo = new UserInfo(sessionId, clientId, userId);
    assertEquals(userInfo.getSessionId(), sessionId);
    assertEquals(userInfo.getClientId(), clientId);
    assertEquals(userInfo.getUserId(), userId);
  }
}