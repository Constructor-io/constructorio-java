package io.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ConstructorIOSearchTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void searchShouldCreateTheCorrectUrl() throws Exception {
        MockWebServer server = new MockWebServer();
        MockResponse response = new MockResponse().setResponseCode(200).setBody("{}");
        HttpUrl serverUrl = server.url("search/Stanley");
        server.enqueue(response);
        
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorIO constructorSpy = Mockito.spy(constructor);
        Mockito.when(constructorSpy.makeUrl("search/Stanley")).thenReturn(serverUrl);
        
        constructor.search("Stanley", null);
        RecordedRequest request = server.takeRequest();
        String expectedUrl = "";
        String actualUrl = request.getRequestUrl().toString();
        assertEquals("url is correct", expectedUrl, actualUrl);

        server.shutdown();
    }
}