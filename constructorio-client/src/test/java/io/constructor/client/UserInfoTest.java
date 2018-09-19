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
    new UserInfo(3, null);
  }

  @Test
  public void newWithInvalidSessionIdShouldFail() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new UserInfo(-1, "c62a-2a09-faie");
  }

  @Test
  public void newShouldReturnUserInfo() throws Exception {
    String clientId = "c62a-2a09-faie";
    int sessionId = 3;
    UserInfo userInfo = new UserInfo(sessionId, clientId);
    assertEquals(userInfo.getSessionId(), sessionId);
    assertEquals(userInfo.getClientId(), clientId);
  }
}