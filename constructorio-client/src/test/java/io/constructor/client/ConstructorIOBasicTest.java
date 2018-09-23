package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import okhttp3.Response;

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
    public void makeUrlShouldReturnUrl() throws Exception {	
        ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
        String generatedUrl = constructor.makeUrl("v1/test");
        assertEquals("make url should make urls", generatedUrl, "https://ac.cnstrc.com/v1/test?key=doinka&c=ciojava-3.4.0");
    }

    @Test
    public void verifyShouldReturnTrueWithValidKeyTokenPair() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertEquals("verify should return true for testing key/pair", constructor.verify(), true);
    }

    @Test
    public void getVersionShouldReturnClientVersion() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertEquals("grabs version from pom.xml", constructor.getVersion(), "ciojava-3.4.0");
    }

    @Test
    public void serializeUserInfoShouldReturnQueryString() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        assertEquals("serializes user info into query string", constructor.serializeUserInfo(userInfo), "&s=3&i=c62a-2a09-faie");
    }

    @Test
    public void checkResponseShouldReturnTrue() throws Exception {
        String string = Utils.getTestResource("response.204.json");
        Response response = Utils.createResponse(204, string);
        assertTrue("response checks true ", ConstructorIO.checkResponse(response));
    }

    @Test
    public void checkResponseShouldReturnExceptionWithNoResponseBody() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("io.constructor.client.ConstructorException: [HTTP 418]");
        String body = "";
        Response response = Utils.createResponse(418, body);
        ConstructorIO.checkResponse(response);
    }

    @Test
    public void checkResponseShouldReturnExceptionOn401() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("io.constructor.client.ConstructorException: [HTTP 401] Invalid auth_token. If you've forgotten your token, you can generate a new one at constructor.io/dashboard");
        String body = Utils.getTestResource("response.401.json");
        Response response = Utils.createResponse(401, body);
        ConstructorIO.checkResponse(response);
    }

    @Test
    public void checkResponseShouldReturnExceptionOn404() throws Exception {
        thrown.expect(ConstructorException.class);
        thrown.expectMessage("io.constructor.client.ConstructorException: [HTTP 404] You're trying to access an invalid endpoint. Please check documentation for allowed endpoints.");
        String body = Utils.getTestResource("response.404.json");
        Response response = Utils.createResponse(404, body);
        ConstructorIO.checkResponse(response);
    }
}