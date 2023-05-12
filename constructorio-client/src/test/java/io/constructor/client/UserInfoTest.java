package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserInfoTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullClientIdShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new UserInfo(3, null);
    }

    @Test
    public void newWithEmptyClientIdShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new UserInfo(3, "");
    }

    @Test
    public void newWithNegativeSessionIdShouldFail() throws Exception {
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

    @Test
    public void settingNullUserIdShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserId(null);
    }

    @Test
    public void settingEmptyUserIdShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserId("");
    }

    @Test
    public void settingUserIdShouldSucceed() throws Exception {
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserId("Bumblebee Autobot");
        assertEquals(userInfo.getUserId(), "Bumblebee Autobot");
    }

    @Test
    public void settingUserSegmentsNullShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserSegments(null);
    }

    @Test
    public void settingUserSegmentsShouldSucceed() throws Exception {
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserSegments(Arrays.asList("Influencers", "Goths", "Hipsters", "Bobos"));
        assertEquals(userInfo.getUserSegments().size(), 4);
    }

    @Test
    public void settingForwardedForNullShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setForwardedFor(null);
    }

    @Test
    public void settingForwardedForShouldSucceed() throws Exception {
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setForwardedFor("203.0.113.195, 70.41.3.18, 150.172.238.178");
        assertEquals(userInfo.getForwardedFor(), "203.0.113.195, 70.41.3.18, 150.172.238.178");
    }

    @Test
    public void settingUserAgentNullShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserAgent(null);
    }

    @Test
    public void emptyConstructorShouldSucceed() throws Exception {
        UserInfo userInfo = new UserInfo();

        assertNull(userInfo.getClientId());
        assertEquals(userInfo.getSessionId(), 0);
    }

    @Test
    public void settingUserAgentShouldSucceed() throws Exception {
        String userAgent =
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko)"
                        + " Chrome/77.0.3865.90 Safari/537.36";
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        userInfo.setUserAgent(userAgent);
        assertEquals(userInfo.getUserAgent(), userAgent);
    }
}
