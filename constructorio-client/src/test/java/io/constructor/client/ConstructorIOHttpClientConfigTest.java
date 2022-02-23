package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOHttpClientConfigTest {
    private String token = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @After
    public void resetClient() {
        ConstructorIO.setHttpClient(new OkHttpClient.Builder()
                .addInterceptor(new ConstructorInterceptor())
                .retryOnConnectionFailure(false)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build());
    }

    @Test
    public void setClientShouldSetClientProperly() {
        assertEquals(30000, ConstructorIO.getHttpClient().readTimeoutMillis());
        assertEquals(30000, ConstructorIO.getHttpClient().writeTimeoutMillis());
        assertEquals(10000, ConstructorIO.getHttpClient().connectTimeoutMillis());
        assertEquals(64, ConstructorIO.getHttpClient().dispatcher().getMaxRequests());
        assertEquals(5, ConstructorIO.getHttpClient().dispatcher().getMaxRequestsPerHost());

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(2);
        dispatcher.setMaxRequests(20);
        ConstructorIO.setHttpClient(ConstructorIO.getHttpClient().newBuilder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .dispatcher(dispatcher).build());

        assertEquals(5000, ConstructorIO.getHttpClient().readTimeoutMillis());
        assertEquals(5000, ConstructorIO.getHttpClient().writeTimeoutMillis());
        assertEquals(5000, ConstructorIO.getHttpClient().connectTimeoutMillis());
        assertEquals(20, ConstructorIO.getHttpClient().dispatcher().getMaxRequests());
        assertEquals(2, ConstructorIO.getHttpClient().dispatcher().getMaxRequestsPerHost());
        assertEquals(1, ConstructorIO.getHttpClient().interceptors().size());
    }

    @Test
    public void configClientShouldConfigClientProperly() {
        assertEquals(30000, ConstructorIO.getHttpClient().readTimeoutMillis());
        assertEquals(30000, ConstructorIO.getHttpClient().writeTimeoutMillis());
        assertEquals(10000, ConstructorIO.getHttpClient().connectTimeoutMillis());
        assertEquals(64, ConstructorIO.getHttpClient().dispatcher().getMaxRequests());
        assertEquals(5, ConstructorIO.getHttpClient().dispatcher().getMaxRequestsPerHost());

        HttpClientConfig config = new HttpClientConfig();
        config.setConnectTimeout(500);
        config.setDispatcherMaxRequests(30);
        config.setDispatcherMaxRequestsPerHost(20);
        config.setReadTimeout(100);
        config.setWriteTimeout(100);
        ConstructorIO.setHttpClientConfig(config);

        assertEquals(100, ConstructorIO.getHttpClient().readTimeoutMillis());
        assertEquals(100, ConstructorIO.getHttpClient().writeTimeoutMillis());
        assertEquals(500, ConstructorIO.getHttpClient().connectTimeoutMillis());
        assertEquals(30, ConstructorIO.getHttpClient().dispatcher().getMaxRequests());
        assertEquals(20, ConstructorIO.getHttpClient().dispatcher().getMaxRequestsPerHost());
    }

    @Test
    public void nullConfigVariablesDoNotGetConfigured() {
        assertEquals(30000, ConstructorIO.getHttpClient().readTimeoutMillis());
        assertEquals(30000, ConstructorIO.getHttpClient().writeTimeoutMillis());
        assertEquals(10000, ConstructorIO.getHttpClient().connectTimeoutMillis());
        assertEquals(64, ConstructorIO.getHttpClient().dispatcher().getMaxRequests());
        assertEquals(5, ConstructorIO.getHttpClient().dispatcher().getMaxRequestsPerHost());

        HttpClientConfig config = new HttpClientConfig();

        assertNull(config.getDispatcherMaxRequests());
        assertNull(config.getDispatcherMaxRequestsPerHost());
        assertNull(config.getReadTimeout());
        assertNull(config.getWriteTimeout());
        assertNull(config.getConnectTimeout());
        assertNull(config.getConnPoolKeepAliveDuration());
        assertNull(config.getConnPoolMaxIdleConnections());
        ConstructorIO.setHttpClientConfig(config);

        assertEquals(30000, ConstructorIO.getHttpClient().readTimeoutMillis());
        assertEquals(30000, ConstructorIO.getHttpClient().writeTimeoutMillis());
        assertEquals(10000, ConstructorIO.getHttpClient().connectTimeoutMillis());
        assertEquals(64, ConstructorIO.getHttpClient().dispatcher().getMaxRequests());
        assertEquals(5, ConstructorIO.getHttpClient().dispatcher().getMaxRequestsPerHost());
    }
}
