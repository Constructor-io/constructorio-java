package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.Request.Builder;

public class ConstructorIOBasicTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newShouldSetApiToken() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        assertEquals("api token should be set", constructor.apiToken, "boinkaToken");
    }

    @Test
    public void newShouldSetApiKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        assertEquals("api key should be set", constructor.apiKey, "doinkaKey");
    }

    @Test
    public void newShouldSetProtocol() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, null);
        assertEquals("protocol should be set", constructor.protocol, "http");
    }

    @Test
    public void newShouldSetHostname() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, "com.cnstrc.ac");
        assertEquals("host should be set", constructor.host, "com.cnstrc.ac");
    }

    @Test
    public void newShouldSetHostnameDefault() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, null);
        assertEquals("host should be set to default", constructor.host, "ac.cnstrc.com");
    }

    @Test
    public void shouldSetNewApiKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, null);
        assertEquals("key should be set to default", constructor.apiKey, "doinkaKey");
        constructor.setApiKey("foobar");
        assertEquals("key should be set to new value", constructor.apiKey, "foobar");
    }

    @Test
    public void shouldSetNewApiKeyMultipleInstances() throws Exception {
        ConstructorIO constructorA = new ConstructorIO("boinkaToken", "foo", false, null);
        ConstructorIO constructorB = new ConstructorIO("boinkaToken", "bar", false, null);
        assertEquals("instance 1 key should be set to default", constructorA.apiKey, "foo");
        assertEquals("instance 2 key should be set to default", constructorB.apiKey, "bar");
        constructorA.setApiKey("newfoo");
        assertEquals("instance 1 key should be set to new value", constructorA.apiKey, "newfoo");
        assertEquals("instance 2 key should be set to default", constructorB.apiKey, "bar");
        constructorB.setApiKey("newbar");
        assertEquals("instance 1 key should be set to new value", constructorA.apiKey, "newfoo");
        assertEquals("instance 2 key should be set to new value", constructorB.apiKey, "newbar");
    }

    @Test
    public void shouldSetNewUserAgent() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, null);
        constructor.setUserAgent("foobar");
        assertEquals("useragent should be set to new value", constructor.userAgent, "foobar");
    }

    @Test
    public void shouldSetNewUserAgentMultipleInstances() throws Exception {
        ConstructorIO constructorA = new ConstructorIO("boinkaToken", "foo", false, null);
        ConstructorIO constructorB = new ConstructorIO("boinkaToken", "bar", false, null);
        constructorA.setUserAgent("newfoo");
        assertEquals("instance 1 useragent should be set to new value", constructorA.userAgent, "newfoo");
        assertEquals("instance 2 useragent should be set to default", constructorB.userAgent, null);
        constructorB.setUserAgent("newbar");
        assertEquals("instance 1 useragent should be set to new value", constructorA.userAgent, "newfoo");
        assertEquals("instance 2 useragent should be set to new value", constructorB.userAgent, "newbar");
    }

    @Test
    public void makeAuthorizedRequestBuilderShouldSetAuthorizationHeader() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        Builder builder = constructor.makeAuthorizedRequestBuilder();
        Request req = builder.url("https://ac.cnstrc.com").get().build();
        assertEquals("authorization headers should be set", req.header("Authorization"), "Basic Ym9pbmthVG9rZW46");
    }

    @Test
    public void makeUserRequestBuilderShouldNotSetAuthorizationHeader() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null, "whitestripes");
        UserInfo info = new UserInfo(2, "sideshow bob");
        info.setForwardedFor("forwardedFor");
        Builder builder = constructor.makeUserRequestBuilder(info);
        Request req = builder.url("https://ac.cnstrc.com").get().build();
        assertNull("authorization headers should not be set", req.header("Authorization"));
    }
    
    @Test
    public void makeUserRequestBuilderShouldSetConstructorTokenHeader() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null, "whitestripes");
        UserInfo info = new UserInfo(2, "sideshow bob");
        Builder builder = constructor.makeUserRequestBuilder(info);
        Request req = builder.url("https://ac.cnstrc.com").get().build();
        assertEquals("constructor token should be set", req.header("x-cnstrc-token"), "whitestripes");
    }

    @Test
    public void makeUserRequestBuilderShouldSetUserAgentHeader() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        constructor.setUserAgent("foobar");
        UserInfo info = new UserInfo(2, "sideshow bob");
        Builder builder = constructor.makeUserRequestBuilder(info);
        Request req = builder.url("https://ac.cnstrc.com").get().build();
        assertEquals("useragent should be set", req.header("User-Agent"), "foobar");
    }

    @Test
    public void makeUserRequestBuilderShouldSetForwardedForHeader() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null, "whitestripes");
        UserInfo info = new UserInfo(2, "sideshow bob");
        info.setForwardedFor("192.168.0.1");
        Builder builder = constructor.makeUserRequestBuilder(info);
        Request req = builder.url("https://ac.cnstrc.com").get().build();
        assertEquals("forwarded for should be set", req.header("x-forwarded-for"), "192.168.0.1");
    }

    @Test
    public void makeUrlShouldReturnAUrl() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        HttpUrl url = constructor.makeUrl(Arrays.asList("getitUuuurl"));
        assertEquals("host is set", url.host(), "ac.cnstrc.com");
        assertEquals("protocol is set", url.scheme(), "https");
        assertEquals("version is set", url.queryParameter("c"), "ciojava-5.3.3");
        assertEquals("apiKey is set", url.queryParameter("key"), "doinkaKey");
    }

    @Test
    public void makeUrlWithUserIdShouldReturnAUrl() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        UserInfo info = new UserInfo(2, "sideshow bob");
        HttpUrl url = constructor.makeUrl(Arrays.asList("getitUuuurl"), info);
        assertEquals("host is set", url.host(), "ac.cnstrc.com");
        assertEquals("protocol is set", url.scheme(), "https");
        assertEquals("version is set", url.queryParameter("c"), "ciojava-5.3.3");
        assertEquals("apiKey is set", url.queryParameter("key"), "doinkaKey");
        assertEquals("session id is set", url.queryParameter("s"), "2");
        assertEquals("client id is set", url.queryParameter("i"), "sideshow bob");
        assertEquals("user id is not set", url.queryParameter("ui"), null);
    }

    @Test
    public void makeUrlWithUserInfoShouldReturnAUrl() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        UserInfo info = new UserInfo(2, "sideshow bob");
        info.setUserId("bob-id-123");
        HttpUrl url = constructor.makeUrl(Arrays.asList("getitUuuurl"), info);
        assertEquals("host is set", url.host(), "ac.cnstrc.com");
        assertEquals("protocol is set", url.scheme(), "https");
        assertEquals("version is set", url.queryParameter("c"), "ciojava-5.3.3");
        assertEquals("apiKey is set", url.queryParameter("key"), "doinkaKey");
        assertEquals("session id is set", url.queryParameter("s"), "2");
        assertEquals("client id is set", url.queryParameter("i"), "sideshow bob");
        assertEquals("user id is set", url.queryParameter("ui"), "bob-id-123");
    }

   
    @Test
    public void makeUrlWithUserSegmentsShouldReturnAUrl() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        UserInfo info = new UserInfo(2, "sideshow bob");
        info.setUserSegments(Arrays.asList("Jocks", "Theater kids"));
        HttpUrl url = constructor.makeUrl(Arrays.asList("getitUuuurl"), info);
        assertEquals("host is set", url.host(), "ac.cnstrc.com");
        assertEquals("protocol is set", url.scheme(), "https");
        assertEquals("version is set", url.queryParameter("c"), "ciojava-5.3.3");
        assertEquals("apiKey is set", url.queryParameter("key"), "doinkaKey");
        assertEquals("session id is set", url.queryParameter("s"), "2");
        assertEquals("client id is set", url.queryParameter("i"), "sideshow bob");
        assertEquals("user segments are set", url.queryParameterValues("us").get(0), "Jocks");
        assertEquals("user segments are set", url.queryParameterValues("us").get(1), "Theater kids");
    }

    @Test
    public void verifyShouldReturnTrueWithValidKeyTokenPair() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertEquals("verify should return true for testing key/pair", constructor.verify(), true);
    }

    @Test
    public void getVersionShouldReturnClientVersion() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertEquals("grabs version from pom.xml", constructor.getVersion(), "ciojava-5.3.3");
    }

    @Test
    public void getResponseBodyShouldReturnString() throws Exception {
        String string = Utils.getTestResource("response.204.json");
        Response response = Utils.createResponse(204, string);
        assertEquals("response is response", ConstructorIO.getResponseBody(response), "");
    }

    @Test
    public void getResponseBodyShouldReturnExceptionWithNoResponseBody() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 418]");
        String body = "";
        Response response = Utils.createResponse(418, body);
        ConstructorIO.getResponseBody(response);
    }

    @Test
    public void getResponseBodyShouldReturnExceptionOn401() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one at constructor.io/dashboard");
        String body = Utils.getTestResource("response.401.json");
        Response response = Utils.createResponse(401, body);
        ConstructorIO.getResponseBody(response);
    }

    @Test
    public void getResponseBodyShouldReturnExceptionOn404() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("[HTTP 404] You're trying to access an invalid endpoint. Please check documentation for allowed endpoints.");
        String body = Utils.getTestResource("response.404.json");
        Response response = Utils.createResponse(404, body);
        ConstructorIO.getResponseBody(response);
    }
}