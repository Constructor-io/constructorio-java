package io.constructor.client;

import static org.junit.Assert.assertEquals;
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
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.setClient(new OkHttpClient.Builder()
                .addInterceptor(new ConstructorInterceptor())
                .retryOnConnectionFailure(false)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build());
    }

    @Test
    public void setClientShouldSetClientProperly() {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        assertEquals(constructor.getClient().readTimeoutMillis(), 30000);
        assertEquals(constructor.getClient().writeTimeoutMillis(), 30000);
        assertEquals(constructor.getClient().dispatcher().getMaxRequests(), 64);
        assertEquals(constructor.getClient().dispatcher().getMaxRequestsPerHost(), 5);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(2);
        dispatcher.setMaxRequests(20);
        constructor.setClient(constructor.getClient().newBuilder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .dispatcher(dispatcher).build());

        assertEquals(constructor.getClient().readTimeoutMillis(), 5000);
        assertEquals(constructor.getClient().writeTimeoutMillis(), 5000);
        assertEquals(constructor.getClient().dispatcher().getMaxRequests(), 20);
        assertEquals(constructor.getClient().dispatcher().getMaxRequestsPerHost(), 2);
        assertEquals(constructor.getClient().interceptors().size() , 1);
    }

    @Test
    public void configClientShouldConfigClientProperly() {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        assertEquals(constructor.getClient().readTimeoutMillis(), 30000);
        assertEquals(constructor.getClient().writeTimeoutMillis(), 30000);
        assertEquals(constructor.getClient().dispatcher().getMaxRequests(), 64);
        assertEquals(constructor.getClient().dispatcher().getMaxRequestsPerHost(), 5);

        HttpClientConfig config = new HttpClientConfig();
        config.setConnectTimeout(500);
        config.setDispatcherMaxRequests(30);
        config.setDispatcherMaxRequestsPerHost(20);
        config.setReadTimeout(100);
        config.setWriteTimeout(100);
        constructor.configHttpClient(config);

        assertEquals(constructor.getClient().readTimeoutMillis(), 100);
        assertEquals(constructor.getClient().writeTimeoutMillis(), 100);
        assertEquals(constructor.getClient().connectTimeoutMillis(), 500);
        assertEquals(constructor.getClient().dispatcher().getMaxRequests(), 30);
        assertEquals(constructor.getClient().dispatcher().getMaxRequestsPerHost(), 20);
    }
}
