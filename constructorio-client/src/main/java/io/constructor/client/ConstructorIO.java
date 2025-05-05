package io.constructor.client;

import com.google.gson.Gson;
import io.constructor.client.models.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/** Constructor.io Client */
public class ConstructorIO {

    /** the HTTP client used by all instances */
    private static OkHttpClient client =
            new OkHttpClient.Builder()
                    .addInterceptor(new ConstructorInterceptor())
                    .retryOnConnectionFailure(false)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

    /** the HTTP client used by all instances (with retry, only for idempotent requests like GET) */
    private static OkHttpClient clientWithRetry =
            client.newBuilder().retryOnConnectionFailure(true).build();

    /**
     * @param newClient the OkHttpClient to use by all instances
     */
    public static void setHttpClient(OkHttpClient newClient) {
        OkHttpClient.Builder builder = newClient.newBuilder().retryOnConnectionFailure(false);
        List<Interceptor> interceptors = newClient.interceptors();
        Boolean exists = false;

        for (Interceptor interceptor : interceptors) {
            if (interceptor instanceof ConstructorInterceptor) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            builder.addInterceptor(new ConstructorInterceptor());
        }

        client = builder.build();
        clientWithRetry = builder.retryOnConnectionFailure(true).build();
    }

    /**
     * @param config the Http client config
     */
    public static void setHttpClientConfig(HttpClientConfig config) {
        OkHttpClient.Builder builder = client.newBuilder();
        Dispatcher dispatcher = new Dispatcher();

        builder.readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(config.getWriteTimeout(), TimeUnit.MILLISECONDS);
        builder.callTimeout(config.getCallTimeout(), TimeUnit.MILLISECONDS);
        builder.connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS);

        ConnectionPool pool =
                new ConnectionPool(
                        config.getConnectionPoolMaxIdleConnections(),
                        config.getConnectionPoolKeepAliveDuration(),
                        TimeUnit.MILLISECONDS);
        builder.connectionPool(pool);

        dispatcher.setMaxRequests(config.getDispatcherMaxRequests());
        dispatcher.setMaxRequestsPerHost(config.getDispatcherMaxRequestsPerHost());
        builder.dispatcher(dispatcher);

        client = builder.build();
        clientWithRetry = builder.retryOnConnectionFailure(true).build();
    }

    /**
     * @return the HTTP client used by all instances
     */
    public static OkHttpClient getHttpClient() {
        return client;
    }

    private String credentials;
    public String apiToken;
    public String apiKey;
    public String protocol;
    public String host;
    public String basePath;
    public Integer port;
    public String version;
    public String constructorToken;

    /**
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a
     *     href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publicly in your in-site javascript client.
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use
     *     HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that
     *     you let this value be null, in which case the host defaults to the Constructor.io
     *     autocomplete service at ac.cnstrc.com.
     * @param constructorToken The token provided by Constructor to identify your company's traffic
     *     if proxying requests for results
     */
    public ConstructorIO(
            String apiToken, String apiKey, boolean isHTTPS, String host, String constructorToken) {
        this.apiToken = apiToken;
        this.apiKey = apiKey;
        this.host = host;
        this.version = this.getVersion();
        if (host == null) {
            this.host = "ac.cnstrc.com";
        }
        if (isHTTPS) {
            this.protocol = "https";
        } else {
            this.protocol = "http";
        }
        this.constructorToken = constructorToken;
        this.credentials =
                "Basic " + Base64.getEncoder().encodeToString((this.apiToken + ":").getBytes());
    }

    /**
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a
     *     href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publicly in your in-site javascript client.
     * @param constructorToken The token provided by Constructor to identify your company's traffic
     *     if proxying requests for results
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use
     *     HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that
     *     you let this value be null, in which case the host defaults to the Constructor.io
     *     autocomplete service at ac.cnstrc.com.
     * @param basePath The basePath to use with the host. It is recommended that you let this value
     *     be null, in which there will not be any base path.
     */
    public ConstructorIO(
            String apiToken,
            String apiKey,
            String constructorToken,
            boolean isHTTPS,
            String host,
            String basePath) {
        this(apiToken, apiKey, isHTTPS, host, constructorToken);
        this.basePath = basePath;
    }

    /**
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a
     *     href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publicly in your in-site javascript client.
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use
     *     HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that
     *     you let this value be null, in which case the host defaults to the Constructor.io
     *     autocomplete service at ac.cnstrc.com.
     */
    public ConstructorIO(String apiToken, String apiKey, boolean isHTTPS, String host) {
        this(apiToken, apiKey, isHTTPS, host, null);
    }

    /*
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publicly in your in-site javascript client.
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that you let this value be null, in which case the host defaults to the Constructor.io autocomplete service at ac.cnstrc.com.
     * @param port The port to connect to
     */
    public ConstructorIO(String apiToken, String apiKey, boolean isHTTPS, String host, int port) {
        this(apiToken, apiKey, isHTTPS, host, null);
        this.port = port;
    }

    /** Sets apiKey */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Verifies that an autocomplete service is working.
     *
     * @return true if working.
     * @throws ConstructorException if the service is not working
     */
    public boolean verify() throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "verify"));
            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            Response response = clientWithRetry.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Adds multiple items to your index whilst replacing existing ones (limit of 1,000 items)
     *
     * @param items the items you want to add or replace.
     * @param section the section of the index that you're adding the items to.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing items.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String createOrReplaceItems(
            ConstructorItem[] items, String section, Boolean force, String notificationEmail)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v2", "items"));
            url =
                    url.newBuilder()
                            .addQueryParameter("force", force.toString())
                            .addQueryParameter("section", section)
                            .build();

            if (notificationEmail != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("notification_email", notificationEmail)
                                .build();
            }

            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            String params = new Gson().toJson(data);
            RequestBody body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder().url(url).put(body).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    public String createOrReplaceItems(ConstructorItem[] items) throws ConstructorException {
        return createOrReplaceItems(items, "Products", false, null);
    }

    public String createOrReplaceItems(ConstructorItem[] items, String section)
            throws ConstructorException {
        return createOrReplaceItems(items, section, false, null);
    }

    public String createOrReplaceItems(ConstructorItem[] items, String section, Boolean force)
            throws ConstructorException {
        return createOrReplaceItems(items, section, force, null);
    }

    /**
     * Deleted multiple items from your index (limit of 1,000 items)
     *
     * @param items the items that you are deleting
     * @param section the section of the index that you're removing the items from.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing items.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid
     */
    public String deleteItems(
            ConstructorItem[] items, String section, Boolean force, String notificationEmail)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v2", "items"));
            url =
                    url.newBuilder()
                            .addQueryParameter("force", force.toString())
                            .addQueryParameter("section", section)
                            .build();

            if (notificationEmail != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("notification_email", notificationEmail)
                                .build();
            }

            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemIds = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                Map<String, Object> params = new HashMap<String, Object>();

                params.put("id", item.getId());
                itemIds.add(params);
            }
            data.put("items", itemIds);

            String params = new Gson().toJson(data);
            RequestBody body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder().url(url).delete(body).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    public String deleteItems(ConstructorItem[] items) throws ConstructorException {
        return deleteItems(items, "Products", false, null);
    }

    public String deleteItems(ConstructorItem[] items, String section) throws ConstructorException {
        return deleteItems(items, section, false, null);
    }

    public String deleteItems(ConstructorItem[] items, String section, Boolean force)
            throws ConstructorException {
        return deleteItems(items, section, force, null);
    }

    /**
     * Creates an items OkHttp request
     *
     * @param req the items request
     * @return a browse OkHttp request
     * @throws ConstructorException
     */
    protected Request createItemsRequest(ItemsRequest req) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("v2", "items");
            HttpUrl url = this.makeUrl(paths);
            url =
                    url.newBuilder()
                            .addQueryParameter("section", req.getSection())
                            .addQueryParameter("page", String.valueOf(req.getPage()))
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            for (String id : req.getIds()) {
                url = url.newBuilder().addQueryParameter("id", id).build();
            }

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Retrieves the items service for items
     *
     * @param req the items request
     * @return an ItemsResponse response
     * @throws ConstructorException if the request is invalid.
     */
    public ItemsResponse retrieveItems(ItemsRequest req) throws ConstructorException {
        try {
            Request request = createItemsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createItemsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the items service for items
     *
     * @param req the items request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String retrieveItemsAsJson(ItemsRequest req) throws ConstructorException {
        try {
            Request request = createItemsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Deletes multiple variations from your index (limit of 1,000 variations)
     *
     * @param variations the variations that you are deleting
     * @param section the section of the autocomplete that you're removing the items from.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing variations.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid
     */
    public String deleteVariations(
            ConstructorVariation[] variations,
            String section,
            Boolean force,
            String notificationEmail)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v2", "variations"));
            url =
                    url.newBuilder()
                            .addQueryParameter("force", force.toString())
                            .addQueryParameter("section", section)
                            .build();

            if (notificationEmail != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("notification_email", notificationEmail)
                                .build();
            }

            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> variationIds = new ArrayList<Object>();
            for (ConstructorVariation variation : variations) {
                Map<String, Object> params = new HashMap<String, Object>();

                params.put("id", variation.getId());
                variationIds.add(params);
            }
            data.put("variations", variationIds);
            String params = new Gson().toJson(data);
            RequestBody body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder().url(url).delete(body).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    public String deleteVariations(ConstructorVariation[] variations) throws ConstructorException {
        return deleteVariations(variations, "Products", false, null);
    }

    public String deleteVariations(ConstructorVariation[] variations, String section)
            throws ConstructorException {
        return deleteVariations(variations, section, false, null);
    }

    public String deleteVariations(ConstructorVariation[] variations, String section, Boolean force)
            throws ConstructorException {
        return deleteVariations(variations, section, force, null);
    }

    /**
     * Updates items from your index.
     *
     * @param items the items that you're updating
     * @param section the section of the autocomplete that you're modifying the item for.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing items.
     * @param onMissing Either "FAIL", "IGNORE", "CREATE", indicating how the system will handle
     *     updating items that don't exist. "FAIL" fails the ingestion if there are items that don't
     *     exist. "IGNORE" ignores items that don't exist. "CREATE" creates items that don't exist.
     *     Defaults to "FAIL".
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String updateItems(
            ConstructorItem[] items,
            String section,
            Boolean force,
            String notificationEmail,
            CatalogRequest.OnMissing onMissing)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v2", "items"));
            url =
                    url.newBuilder()
                            .addQueryParameter("force", force.toString())
                            .addQueryParameter("section", section)
                            .build();

            if (notificationEmail != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("notification_email", notificationEmail)
                                .build();
            }

            if (onMissing != null && onMissing != CatalogRequest.OnMissing.FAIL) {
                url = url.newBuilder().addQueryParameter("on_missing", onMissing.name()).build();
            }

            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            String params = new Gson().toJson(data);
            RequestBody body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder().url(url).patch(body).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Updates items from your index.
     *
     * @param items the items that you're updating
     * @param section the section of the autocomplete that you're modifying the item for.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing items.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String updateItems(
            ConstructorItem[] items, String section, Boolean force, String notificationEmail)
            throws ConstructorException {
        return updateItems(items, section, force, notificationEmail, null);
    }

    public String updateItems(ConstructorItem[] items) throws ConstructorException {
        return updateItems(items, "Products", false, null, null);
    }

    public String updateItems(ConstructorItem[] items, String section) throws ConstructorException {
        return updateItems(items, section, false, null, null);
    }

    public String updateItems(ConstructorItem[] items, String section, Boolean force)
            throws ConstructorException {
        return updateItems(items, section, force, null, null);
    }

    /**
     * Update variations from your index.
     *
     * @param variations the variations that you're updating.
     * @param section the section of the autocomplete that you're modifying the item for.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing variations.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @param onMissing Either "FAIL", "IGNORE", "CREATE", indicating how the system will handle
     *     updating variations that don't exist. "FAIL" fails the ingestion if there are items that
     *     don't exist. "IGNORE" ignores variations that don't exist. "CREATE" creates items that
     *     don't exist. Defaults to "FAIL".
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String updateVariations(
            ConstructorVariation[] variations,
            String section,
            Boolean force,
            String notificationEmail,
            CatalogRequest.OnMissing onMissing)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v2", "variations"));
            url =
                    url.newBuilder()
                            .addQueryParameter("force", force.toString())
                            .addQueryParameter("section", section)
                            .build();

            if (notificationEmail != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("notification_email", notificationEmail)
                                .build();
            }
            if (onMissing != null && onMissing != CatalogRequest.OnMissing.FAIL) {
                url = url.newBuilder().addQueryParameter("on_missing", onMissing.name()).build();
            }

            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> variationsAsJSON = new ArrayList<Object>();
            for (ConstructorVariation variation : variations) {
                variationsAsJSON.add(variation.toMap());
            }
            data.put("variations", variationsAsJSON);
            String params = new Gson().toJson(data);
            RequestBody body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder().url(url).patch(body).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Update variations from your index.
     *
     * @param variations the variations that you're updating.
     * @param section the section of the autocomplete that you're modifying the item for.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing variations.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String updateVariations(
            ConstructorVariation[] variations,
            String section,
            Boolean force,
            String notificationEmail)
            throws ConstructorException {
        try {
            return updateVariations(variations, section, force, notificationEmail, null);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    public String updateVariations(ConstructorVariation[] variations) throws ConstructorException {
        return updateVariations(variations, "Products", false, null, null);
    }

    public String updateVariations(ConstructorVariation[] variations, String section)
            throws ConstructorException {
        return updateVariations(variations, section, false, null, null);
    }

    public String updateVariations(ConstructorVariation[] variations, String section, Boolean force)
            throws ConstructorException {
        return updateVariations(variations, section, force, null, null);
    }

    /**
     * Adds multiple variations to your index whilst replacing existing ones (limit of 1,000 items)
     *
     * @param variations the items you want to add or replace.
     * @param section the section of the autocomplete that you're adding the items to.
     * @param force whether or not the system should process the request even if it will invalidate
     *     a large number of existing variations.
     * @param notificationEmail An email address where you'd like to receive an email notification
     *     in case the task fails.
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String createOrReplaceVariations(
            ConstructorVariation[] variations,
            String section,
            Boolean force,
            String notificationEmail)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v2", "variations"));
            url =
                    url.newBuilder()
                            .addQueryParameter("force", force.toString())
                            .addQueryParameter("section", section)
                            .build();

            if (notificationEmail != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("notification_email", notificationEmail)
                                .build();
            }

            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> variationsAsJSON = new ArrayList<Object>();
            for (ConstructorVariation variation : variations) {
                variationsAsJSON.add(variation.toMap());
            }
            data.put("variations", variationsAsJSON);
            String params = new Gson().toJson(data);
            RequestBody body =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder().url(url).put(body).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    public String createOrReplaceVariations(ConstructorVariation[] variations)
            throws ConstructorException {
        return createOrReplaceVariations(variations, "Products", false, null);
    }

    public String createOrReplaceVariations(ConstructorVariation[] variations, String section)
            throws ConstructorException {
        return createOrReplaceVariations(variations, section, false, null);
    }

    public String createOrReplaceVariations(
            ConstructorVariation[] variations, String section, Boolean force)
            throws ConstructorException {
        return createOrReplaceVariations(variations, section, force, null);
    }

    /**
     * Creates a variations OkHttp request
     *
     * @param req the variations request
     * @return a browse OkHttp request
     * @throws ConstructorException
     */
    protected Request createVariationsRequest(VariationsRequest req) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("v2", "variations");
            HttpUrl url = this.makeUrl(paths);
            url =
                    url.newBuilder()
                            .addQueryParameter("section", req.getSection())
                            .addQueryParameter("page", String.valueOf(req.getPage()))
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            String itemId = req.getItemId();

            if (itemId != null) {
                url = url.newBuilder().addQueryParameter("item_id", itemId).build();
            }

            for (String id : req.getIds()) {
                url = url.newBuilder().addQueryParameter("id", id).build();
            }

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the variations service for variations
     *
     * @param req the variations request
     * @return an VariationsResponse response
     * @throws ConstructorException if the request is invalid.
     */
    public VariationsResponse retrieveVariations(VariationsRequest req)
            throws ConstructorException {
        try {
            Request request = createVariationsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createVariationsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the variations service for variations
     *
     * @param req the variations request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String retrieveVariationsAsJson(VariationsRequest req) throws ConstructorException {
        try {
            Request request = createVariationsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the autocomplete service.
     *
     * <p>Note that if you're making an autocomplete request for a website, you should definitely
     * use our javascript client instead of doing it server-side! That's important. That will be a
     * solid latency difference.
     *
     * @param req the autocomplete request
     * @param userInfo optional information about the user
     * @return an autocomplete response
     * @throws ConstructorException if the request is invalid.
     */
    public AutocompleteResponse autocomplete(AutocompleteRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            String json = autocompleteAsJSON(req, userInfo);
            return createAutocompleteResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the autocomplete service.
     *
     * <p>Note that if you're making an autocomplete request for a website, you should definitely
     * use our javascript client instead of doing it server-side! That's important. That will be a
     * solid latency difference.
     *
     * @param req the autocomplete request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String autocompleteAsJSON(AutocompleteRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("autocomplete", req.getQuery());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);

            for (Map.Entry<String, Integer> entry : req.getResultsPerSection().entrySet()) {
                String section = entry.getKey();
                String count = String.valueOf(entry.getValue());
                url = url.newBuilder().addQueryParameter("num_results_" + section, count).build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_fields]", hiddenField)
                                .build();
            }

            for (String filterName : req.getFilters().keySet()) {
                for (String facetValue : req.getFilters().get(filterName)) {
                    url =
                            url.newBuilder()
                                    .addQueryParameter("filters[" + filterName + "]", facetValue)
                                    .build();
                }
            }

            for (String sectionName : req.getFiltersPerSection().keySet()) {
                for (String filterName : req.getFiltersPerSection().get(sectionName).keySet()) {
                    for (String facetValue :
                            req.getFiltersPerSection().get(sectionName).get(filterName)) {
                        url =
                                url.newBuilder()
                                        .addQueryParameter(
                                                "filters"
                                                        + "["
                                                        + sectionName
                                                        + "]"
                                                        + "["
                                                        + filterName
                                                        + "]",
                                                facetValue)
                                        .build();
                    }
                }
            }

            if (req.getVariationsMap() != null) {
                String variationsMapJson = new Gson().toJson(req.getVariationsMap());
                url =
                        url.newBuilder()
                                .addQueryParameter("variations_map", variationsMapJson)
                                .build();
            }

            Request request = this.makeUserRequestBuilder(userInfo).url(url).get().build();

            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a search OkHttp request
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @return a search OkHttp request
     * @throws ConstructorException
     */
    protected Request createSearchRequest(SearchRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Boolean authorizedRequest = false;
            List<String> paths = Arrays.asList("search", req.getQuery());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);
            url =
                    url.newBuilder()
                            .addQueryParameter("section", req.getSection())
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            if (req.getGroupId() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("filters[group_id]", req.getGroupId())
                                .build();
            }

            for (String facetName : req.getFacets().keySet()) {
                for (String facetValue : req.getFacets().get(facetName)) {
                    url =
                            url.newBuilder()
                                    .addQueryParameter("filters[" + facetName + "]", facetValue)
                                    .build();
                }
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "fmt_options[" + formatOptionKey + "]", formatOptionValue)
                                .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_fields]", hiddenField)
                                .build();
            }

            for (String hiddenFacet : req.getHiddenFacets()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_facets]", hiddenFacet)
                                .build();
            }

            if (StringUtils.isNotBlank(req.getSortBy())) {
                url =
                        url.newBuilder()
                                .addQueryParameter("sort_by", req.getSortBy())
                                .addQueryParameter(
                                        "sort_order",
                                        req.getSortAscending() ? "ascending" : "descending")
                                .build();
            }

            if (req.getCollectionId() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("collection_id", req.getCollectionId())
                                .build();
            }

            if (req.getVariationsMap() != null) {
                String variationsMapJson = new Gson().toJson(req.getVariationsMap());
                url =
                        url.newBuilder()
                                .addQueryParameter("variations_map", variationsMapJson)
                                .build();
            }

            if (req.getPreFilterExpression() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "pre_filter_expression", req.getPreFilterExpression())
                                .build();
            }

            if (req.getQsParam() != null) {
                url = url.newBuilder().addQueryParameter("qs", req.getQsParam()).build();
            }

            if (req.getNow() != null) {
                // Make an authorized request if the `now` parameter is provided
                authorizedRequest = true;

                url = url.newBuilder().addQueryParameter("now", req.getNow()).build();
            }

            if (req.getPage() != 1) {
                url =
                        url.newBuilder()
                                .addQueryParameter("page", String.valueOf(req.getPage()))
                                .build();
            }

            if (req.getOffset() != 0) {
                url =
                        url.newBuilder()
                                .addQueryParameter("offset", String.valueOf(req.getOffset()))
                                .build();
            }

            for (String filterMatchTypesKey : req.getFilterMatchTypes().keySet()) {
                String filterMatchTypesValue = req.getFilterMatchTypes().get(filterMatchTypesKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "filter_match_types[" + filterMatchTypesKey + "]",
                                        filterMatchTypesValue)
                                .build();
            }

            Request request =
                    this.makeUserRequestBuilder(userInfo, authorizedRequest).url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service.
     *
     * <p>Note that if you're making a search request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @return a search response
     * @throws ConstructorException if the request is invalid.
     */
    public SearchResponse search(SearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createSearchRequest(req, userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createSearchResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service.
     *
     * <p>Note that if you're making a search request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @param c a callback with success and failure conditions
     * @throws ConstructorException if the request is invalid.
     */
    public void search(SearchRequest req, UserInfo userInfo, final SearchCallback c)
            throws ConstructorException {
        try {
            Request request = createSearchRequest(req, userInfo);
            clientWithRetry
                    .newCall(request)
                    .enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    c.onFailure(new ConstructorException(e));
                                }

                                @Override
                                public void onResponse(Call call, final Response response)
                                        throws IOException {
                                    try {
                                        String json = getResponseBody(response);
                                        SearchResponse res = createSearchResponse(json);
                                        c.onResponse(res);
                                    } catch (Exception e) {
                                        c.onFailure(new ConstructorException(e));
                                    }
                                }
                            });
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service.
     *
     * <p>Note that if you're making a search request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String searchAsJSON(SearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createSearchRequest(req, userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a browse OkHttp request
     *
     * @param req the browse request
     * @param userInfo optional information about the user
     * @return a browse OkHttp request
     * @throws ConstructorException
     */
    protected Request createBrowseRequest(BrowseRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Boolean authorizedRequest = false;
            List<String> paths = Arrays.asList("browse", req.getFilterName(), req.getFilterValue());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);
            url =
                    url.newBuilder()
                            .addQueryParameter("section", req.getSection())
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            if (req.getGroupId() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("filters[group_id]", req.getGroupId())
                                .build();
            }

            for (String facetName : req.getFacets().keySet()) {
                for (String facetValue : req.getFacets().get(facetName)) {
                    url =
                            url.newBuilder()
                                    .addQueryParameter("filters[" + facetName + "]", facetValue)
                                    .build();
                }
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "fmt_options[" + formatOptionKey + "]", formatOptionValue)
                                .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_fields]", hiddenField)
                                .build();
            }

            for (String hiddenFacet : req.getHiddenFacets()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_facets]", hiddenFacet)
                                .build();
            }

            if (StringUtils.isNotBlank(req.getSortBy())) {
                url =
                        url.newBuilder()
                                .addQueryParameter("sort_by", req.getSortBy())
                                .addQueryParameter(
                                        "sort_order",
                                        req.getSortAscending() ? "ascending" : "descending")
                                .build();
            }

            if (req.getVariationsMap() != null) {
                String variationsMapJson = new Gson().toJson(req.getVariationsMap());
                url =
                        url.newBuilder()
                                .addQueryParameter("variations_map", variationsMapJson)
                                .build();
            }

            if (req.getPreFilterExpression() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "pre_filter_expression", req.getPreFilterExpression())
                                .build();
            }

            if (req.getQsParam() != null) {
                url = url.newBuilder().addQueryParameter("qs", req.getQsParam()).build();
            }

            if (req.getNow() != null) {
                // Make an authorized request if the `now` parameter is provided
                authorizedRequest = true;

                url = url.newBuilder().addQueryParameter("now", req.getNow()).build();
            }

            if (req.getPage() != 1) {
                url =
                        url.newBuilder()
                                .addQueryParameter("page", String.valueOf(req.getPage()))
                                .build();
            }

            if (req.getOffset() != 0) {
                url =
                        url.newBuilder()
                                .addQueryParameter("offset", String.valueOf(req.getOffset()))
                                .build();
            }

            for (String filterMatchTypesKey : req.getFilterMatchTypes().keySet()) {
                String filterMatchTypesValue = req.getFilterMatchTypes().get(filterMatchTypesKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "filter_match_types[" + filterMatchTypesKey + "]",
                                        filterMatchTypesValue)
                                .build();
            }

            Request request =
                    this.makeUserRequestBuilder(userInfo, authorizedRequest).url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service.
     *
     * <p>Note that if you're making a browse request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the browse request
     * @param userInfo optional information about the user
     * @return a browse response
     * @throws ConstructorException if the request is invalid.
     */
    public BrowseResponse browse(BrowseRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createBrowseRequest(req, userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createBrowseResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service.
     *
     * <p>Note that if you're making a browse request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the browse request
     * @param userInfo optional information about the user
     * @param c a callback with success and failure conditions
     * @throws ConstructorException if the request is invalid.
     */
    public void browse(BrowseRequest req, UserInfo userInfo, final BrowseCallback c)
            throws ConstructorException {
        try {
            Request request = createBrowseRequest(req, userInfo);
            clientWithRetry
                    .newCall(request)
                    .enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    c.onFailure(new ConstructorException(e));
                                }

                                @Override
                                public void onResponse(Call call, final Response response)
                                        throws IOException {
                                    try {
                                        String json = getResponseBody(response);
                                        BrowseResponse res = createBrowseResponse(json);
                                        c.onResponse(res);
                                    } catch (Exception e) {
                                        c.onFailure(new ConstructorException(e));
                                    }
                                }
                            });
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service.
     *
     * <p>Note that if you're making a browse request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the browse request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String browseAsJSON(BrowseRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createBrowseRequest(req, userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a browse items OkHttp request
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @return a browse OkHttp request
     * @throws ConstructorException
     */
    protected Request createBrowseItemsRequest(BrowseItemsRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("browse", "items");
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);
            url =
                    url.newBuilder()
                            .addQueryParameter("section", req.getSection())
                            .addQueryParameter("page", String.valueOf(req.getPage()))
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            for (String id : req.getIds()) {
                url = url.newBuilder().addQueryParameter("ids", id).build();
            }

            if (req.getGroupId() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("filters[group_id]", req.getGroupId())
                                .build();
            }

            for (String facetName : req.getFacets().keySet()) {
                for (String facetValue : req.getFacets().get(facetName)) {
                    url =
                            url.newBuilder()
                                    .addQueryParameter("filters[" + facetName + "]", facetValue)
                                    .build();
                }
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "fmt_options[" + formatOptionKey + "]", formatOptionValue)
                                .build();
            }

            if (StringUtils.isNotBlank(req.getSortBy())) {
                url =
                        url.newBuilder()
                                .addQueryParameter("sort_by", req.getSortBy())
                                .addQueryParameter(
                                        "sort_order",
                                        req.getSortAscending() ? "ascending" : "descending")
                                .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_fields]", hiddenField)
                                .build();
            }

            for (String hiddenFacet : req.getHiddenFacets()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_facets]", hiddenFacet)
                                .build();
            }

            for (String filterMatchTypesKey : req.getFilterMatchTypes().keySet()) {
                String filterMatchTypesValue = req.getFilterMatchTypes().get(filterMatchTypesKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "filter_match_types[" + filterMatchTypesKey + "]",
                                        filterMatchTypesValue)
                                .build();
            }

            Request request = this.makeUserRequestBuilder(userInfo).url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service with item id's.
     *
     * <p>Note that if you're making a browse request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @return a browse response
     * @throws ConstructorException if the request is invalid.
     */
    public BrowseResponse browseItems(BrowseItemsRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createBrowseItemsRequest(req, userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createBrowseResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service with item id's.
     *
     * <p>Note that if you're making a browse request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @param c a callback with success and failure conditions
     * @throws ConstructorException if the request is invalid.
     */
    public void browseItems(BrowseItemsRequest req, UserInfo userInfo, final BrowseCallback c)
            throws ConstructorException {
        try {
            Request request = createBrowseItemsRequest(req, userInfo);
            clientWithRetry
                    .newCall(request)
                    .enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    c.onFailure(new ConstructorException(e));
                                }

                                @Override
                                public void onResponse(Call call, final Response response)
                                        throws IOException {
                                    try {
                                        String json = getResponseBody(response);
                                        BrowseResponse res = createBrowseResponse(json);
                                        c.onResponse(res);
                                    } catch (Exception e) {
                                        c.onFailure(new ConstructorException(e));
                                    }
                                }
                            });
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service with item id's.
     *
     * <p>Note that if you're making a browse request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String browseItemsAsJSON(BrowseItemsRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createBrowseItemsRequest(req, userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a browse facets OkHttp request
     *
     * @param req the browse facets request
     * @return a browse OkHttp request
     * @throws ConstructorException
     */
    protected Request createBrowseFacetsRequest(BrowseFacetsRequest req)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("browse", "facets");
            HttpUrl url = this.makeUrl(paths);
            url =
                    url.newBuilder()
                            .addQueryParameter("page", String.valueOf(req.getPage()))
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "fmt_options[" + formatOptionKey + "]", formatOptionValue)
                                .build();
            }

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse facets service
     *
     * @param req the browse facets request
     * @return a browse facets response
     * @throws ConstructorException if the request is invalid.
     */
    public BrowseFacetsResponse browseFacets(BrowseFacetsRequest req) throws ConstructorException {
        try {
            Request request = createBrowseFacetsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createBrowseFacetsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse facets service
     *
     * @param req the browse facets request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String browseFacetsAsJSON(BrowseFacetsRequest req) throws ConstructorException {
        try {
            Request request = createBrowseFacetsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a browse facets OkHttp request
     *
     * @param req the browse facets request
     * @return a browse OkHttp request
     * @throws ConstructorException
     */
    protected Request createBrowseFacetOptionsRequest(BrowseFacetOptionsRequest req)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("browse", "facet_options");
            HttpUrl url = this.makeUrl(paths);
            url =
                    url.newBuilder()
                            .addQueryParameter("facet_name", String.valueOf(req.getFacetName()))
                            .build();

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "fmt_options[" + formatOptionKey + "]", formatOptionValue)
                                .build();
            }

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse facet options service
     *
     * @param req the browse facet options request
     * @return a browse facet options response
     * @throws ConstructorException if the request is invalid.
     */
    public BrowseFacetOptionsResponse browseFacetOptions(BrowseFacetOptionsRequest req)
            throws ConstructorException {
        try {
            Request request = createBrowseFacetOptionsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createBrowseFacetOptionsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse facet options service
     *
     * @param req the browse facet options request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String browseFacetOptionsAsJSON(BrowseFacetOptionsRequest req)
            throws ConstructorException {
        try {
            Request request = createBrowseFacetOptionsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service with natural language processing.
     *
     * <p>Note that if you're making a search request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the natural language search request
     * @param userInfo optional information about the user
     * @return a search response
     * @throws ConstructorException if the request is invalid.
     */
    public SearchResponse naturalLanguageSearch(NaturalLanguageSearchRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            String json = naturalLanguageSearchAsJSON(req, userInfo);
            return createSearchResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service with natural language processing.
     *
     * <p>Note that if you're making a search request for a website, you should definitely use our
     * javascript client instead of doing it server-side! That's important. That will be a solid
     * latency difference.
     *
     * @param req the natural language search request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String naturalLanguageSearchAsJSON(NaturalLanguageSearchRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("search", "natural_language", req.getQuery());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);

            url =
                    url.newBuilder()
                            .addQueryParameter("section", req.getSection())
                            .addQueryParameter("page", String.valueOf(req.getPage()))
                            .addQueryParameter(
                                    "num_results_per_page", String.valueOf(req.getResultsPerPage()))
                            .build();

            Request request = this.makeUserRequestBuilder(userInfo).url(url).get().build();

            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the recommendations service to retrieve results.
     *
     * <p>Note that if you're making a recommendations request for a website, you should definitely
     * use our javascript client instead of doing it server-side! That's important. That will be a
     * solid latency difference.
     *
     * @param req the recommendations request
     * @param userInfo optional information about the user
     * @return a recommendations response
     * @throws ConstructorException if the request is invalid.
     */
    public RecommendationsResponse recommendations(RecommendationsRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            String json = recommendationsAsJSON(req, userInfo);
            return createRecommendationsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the recommendations service to retrieve results.
     *
     * <p>Note that if you're making an recommendations request for a website, you should definitely
     * use our javascript client instead of doing it server-side! That's important. That will be a
     * solid latency difference.
     *
     * @param req the recommendations request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String recommendationsAsJSON(RecommendationsRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("recommendations", "v1", "pods", req.getPodId());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);

            url =
                    url.newBuilder()
                            .addQueryParameter("num_results", String.valueOf(req.getNumResults()))
                            .addQueryParameter("section", req.getSection())
                            .build();

            if (req.getItemIds() != null) {
                for (String itemId : req.getItemIds()) {
                    url = url.newBuilder().addQueryParameter("item_id", itemId).build();
                }
            }

            if (StringUtils.isNotBlank(req.getTerm())) {
                url = url.newBuilder().addQueryParameter("term", req.getTerm()).build();
            }

            if (req.getFacets() != null) {
                for (String facetName : req.getFacets().keySet()) {
                    for (String facetValue : req.getFacets().get(facetName)) {
                        url =
                                url.newBuilder()
                                        .addQueryParameter("filters[" + facetName + "]", facetValue)
                                        .build();
                    }
                }
            }

            if (req.getVariationsMap() != null) {
                String variationsMapJson = new Gson().toJson(req.getVariationsMap());
                url =
                        url.newBuilder()
                                .addQueryParameter("variations_map", variationsMapJson)
                                .build();
            }

            if (req.getPreFilterExpression() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "pre_filter_expression", req.getPreFilterExpression())
                                .build();
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url =
                        url.newBuilder()
                                .addQueryParameter(
                                        "fmt_options[" + formatOptionKey + "]", formatOptionValue)
                                .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url =
                        url.newBuilder()
                                .addQueryParameter("fmt_options[hidden_fields]", hiddenField)
                                .build();
            }

            Request request = this.makeUserRequestBuilder(userInfo).url(url).get().build();

            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Makes a URL to issue the requests to. Note that the URL will automagically have the apiKey
     * embedded.
     *
     * @param paths endpoint of the autocomplete service.
     * @return the created URL. Now you can use it to issue requests and things!
     */
    protected HttpUrl makeUrl(List<String> paths) throws UnsupportedEncodingException {
        okhttp3.HttpUrl.Builder builder =
                new HttpUrl.Builder()
                        .scheme(this.protocol)
                        .addQueryParameter("key", this.apiKey)
                        .host(this.host);

        if (this.basePath != null && !this.basePath.isEmpty()) {
            builder.addPathSegments(this.basePath);
        }

        if (!paths.contains("catalog")
                && !paths.contains("tasks")
                && !paths.contains("task")
                && !paths.contains("redirect_rules")) {
            builder.addQueryParameter("c", this.version);
        }

        for (String path : paths) {
            String canonicalPath =
                    OkHttpUrlBuilderUtils.canonicalize(
                            path,
                            0,
                            path.length(),
                            OkHttpUrlBuilderUtils.PATH_SEGMENT_ENCODE_SET,
                            false,
                            true,
                            true,
                            true,
                            null);
            builder.addEncodedPathSegment(canonicalPath);
        }

        if (this.port != null) {
            builder.port(this.port);
        }

        return builder.build();
    }

    /**
     * Makes a URL to issue the requests to. Note that the URL will automagically have the apiKey
     * embedded.
     *
     * @param paths endpoint of the autocomplete service.
     * @return the created URL. Now you can use it to issue requests and things!
     */
    protected HttpUrl makeUrl(List<String> paths, UserInfo info)
            throws UnsupportedEncodingException {
        okhttp3.HttpUrl.Builder builder =
                new HttpUrl.Builder()
                        .scheme(this.protocol)
                        .host(this.host)
                        .addQueryParameter("key", this.apiKey)
                        .addQueryParameter("c", this.version);

        if (this.basePath != null && !this.basePath.isEmpty()) {
            builder.addPathSegments(this.basePath);
        }

        if (info.getClientId() != null) {
            builder.addQueryParameter("i", info.getClientId());
        }

        if (info.getSessionId() != 0) {
            builder.addQueryParameter("s", String.valueOf(info.getSessionId()));
        }

        for (String path : paths) {
            String canonicalPath =
                    OkHttpUrlBuilderUtils.canonicalize(
                            path,
                            0,
                            path.length(),
                            OkHttpUrlBuilderUtils.PATH_SEGMENT_ENCODE_SET,
                            false,
                            true,
                            true,
                            true,
                            null);
            builder.addEncodedPathSegment(canonicalPath);
        }

        if (info.getUserId() != null) {
            builder.addQueryParameter("ui", String.valueOf(info.getUserId()));
        }

        if (info.getUserSegments() != null) {
            for (String userSegment : info.getUserSegments()) {
                builder.addQueryParameter("us", userSegment);
            }
        }

        if (this.port != null) {
            builder.port(this.port);
        }

        return builder.build();
    }

    /**
     * Creates a builder for an authorized request
     *
     * @return Request Builder
     */
    protected Builder makeAuthorizedRequestBuilder() {
        Builder builder = new Request.Builder();
        builder.addHeader("Authorization", this.credentials);
        return builder;
    }

    /**
     * Creates a builder for an end user request
     *
     * @param info user information if available
     * @return Request Builder
     */
    protected Builder makeUserRequestBuilder(UserInfo info) {
        Builder builder = new Request.Builder();
        if (this.constructorToken != null) {
            builder.addHeader("x-cnstrc-token", this.constructorToken);
        }
        if (info != null && info.getForwardedFor() != null) {
            builder.addHeader("x-forwarded-for", info.getForwardedFor());
        }
        if (info != null && info.getUserAgent() != null) {
            builder.addHeader("User-Agent", info.getUserAgent());
        }
        return builder;
    }

    /**
     * Creates a builder for an end user request
     *
     * @param info user information if available
     * @param authorizedRequest if true, send the request with authorized credentials
     * @return Request Builder
     */
    protected Builder makeUserRequestBuilder(UserInfo info, Boolean authorizedRequest) {
        Builder builder = makeUserRequestBuilder(info);

        if (authorizedRequest) {
            builder.addHeader("Authorization", this.credentials);
        }

        return builder;
    }

    /**
     * Checks the response from an endpoint and throws an exception if an error occurred
     *
     * @return whether the request was successful
     */
    protected static String getResponseBody(Response response) throws ConstructorException {
        String errorMessage = "Unknown error";
        Integer errorCode = null;
        try {
            String body = response.body().string();
            if (response.isSuccessful()) {
                return body;
            } else {
                ServerError error = new Gson().fromJson(body, ServerError.class);
                errorMessage = "[HTTP " + response.code() + "] " + error.getMessage();
                errorCode = response.code();
            }
        } catch (Exception e) {
            errorMessage = "[HTTP " + response.code() + "]";
        } finally {
            response.close();
        }
        throw new ConstructorException(errorMessage, errorCode);
    }

    /**
     * Grabs the version number (hard coded ATM)
     *
     * @return version number
     */
    protected String getVersion() {
        return "ciojava-6.4.1";
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an autocomplete
     * response. Using JSON objects to achieve this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static AutocompleteResponse createAutocompleteResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONObject sections = json.getJSONObject("sections");
        for (Object sectionKey : sections.keySet()) {
            String sectionName = (String) sectionKey;
            JSONArray results = sections.getJSONArray(sectionName);
            moveMetadataOutOfResultData(results);
        }
        String transformed = json.toString();
        return new Gson().fromJson(transformed, AutocompleteResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an search response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it
     * in a Gson Type Adapter.
     */
    protected static SearchResponse createSearchResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONObject response = json.getJSONObject("response");
        JSONArray results;

        if (!response.isNull("results")) {
            results = response.getJSONArray("results");
            moveMetadataOutOfResultData(results);
        }

        String transformed = json.toString();
        return new Gson().fromJson(transformed, SearchResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an browse response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it
     * in a Gson Type Adapter.
     */
    protected static BrowseResponse createBrowseResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONObject response = json.getJSONObject("response");
        JSONArray results = response.getJSONArray("results");
        moveMetadataOutOfResultData(results);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, BrowseResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an browse facets
     * response. Using JSON objects to acheive this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static BrowseFacetsResponse createBrowseFacetsResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, BrowseFacetsResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an browse facets
     * response. Using JSON objects to acheive this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static BrowseFacetOptionsResponse createBrowseFacetOptionsResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, BrowseFacetOptionsResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an recommendations
     * response. Using JSON objects to achieve this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static RecommendationsResponse createRecommendationsResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONObject response = json.getJSONObject("response");
        JSONArray results = response.getJSONArray("results");
        moveMetadataOutOfResultData(results);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, RecommendationsResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an All Tasks
     * response. Using JSON objects to achieve this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static AllTasksResponse createAllTasksResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, AllTasksResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an All Redirects
     * response. Using JSON objects to achieve this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static AllRedirectsResponse createAllRedirectsResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, AllRedirectsResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Task response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it
     * in a Gson Type Adapter.
     */
    protected static Task createTaskResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, Task.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Quiz Question
     * response. Using JSON objects to achieve this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static QuizQuestionResponse createQuizQuestionResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, QuizQuestionResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Quiz results
     * response. Using JSON objects to achieve this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static QuizResultsResponse createQuizResultsResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, QuizResultsResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Quiz results
     * configuration response. Using JSON objects to achieve this is considerably less error prone
     * than attempting to do it in a Gson Type Adapter.
     */
    protected static QuizResultsConfigResponse createQuizResultsConfigResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, QuizResultsConfigResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an Items response.
     * Using JSON objects to acheive this is considerably less error prone than attempting to do it
     * in a Gson Type Adapter.
     */
    protected static ItemsResponse createItemsResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONArray items = json.getJSONArray("items");
        JSONArray itemsTransformed = transformItemsAPIV2Response(items);
        json.put("items", itemsTransformed);

        String transformed = json.toString();
        return new Gson().fromJson(transformed, ItemsResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an Variations
     * response. Using JSON objects to acheive this is considerably less error prone than attempting
     * to do it in a Gson Type Adapter.
     */
    protected static VariationsResponse createVariationsResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONArray variations = json.getJSONArray("variations");
        JSONArray variationsTransformed = transformItemsAPIV2Response(variations);
        json.put("variations", variationsTransformed);

        String transformed = json.toString();
        return new Gson().fromJson(transformed, VariationsResponse.class);
    }

    /**
     * Moves metadata out of the result data for an array of results
     *
     * @param results A JSON array of results
     */
    protected static void moveMetadataOutOfResultData(JSONArray results) {
        for (int i = 0; i < results.length(); i++) {

            JSONObject result = results.getJSONObject(i);
            JSONObject resultData = result.getJSONObject("data");
            JSONObject metadata = new JSONObject();

            // Recursive call to move unspecified properties in result variations to its metadata
            // object
            if (!result.isNull("variations")) {
                JSONArray variations = result.getJSONArray("variations");
                moveMetadataOutOfResultData(variations);
            }

            // Move unspecified properties in result data object to metadata object
            for (Object propertyKey : resultData.keySet()) {
                String propertyName = (String) propertyKey;
                if (!propertyName.matches(
                        "(description|id|url|image_url|groups|facets|variation_id)")) {
                    metadata.put(propertyName, resultData.get(propertyName));
                }
            }

            // Remove unspecified properties from result data object
            for (Object propertyKey : metadata.keySet()) {
                String propertyName = (String) propertyKey;
                resultData.remove(propertyName);
            }

            // Add metadata to result data object
            resultData.put("metadata", metadata);
        }
    }

    /**
     * Transforms Items API V2 response to ConstructorItem and ConstructorVariation form
     *
     * @param results A JSON array of results
     */
    protected static JSONArray transformItemsAPIV2Response(JSONArray results) {
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            JSONObject resultData = result.getJSONObject("data");
            JSONObject metadata = new JSONObject();

            // Move unspecified properties in result data object to metadata object
            for (Object propertyKey : resultData.keySet()) {
                String propertyName = (String) propertyKey;
                Object propertyValue = resultData.get(propertyName);

                if (!propertyName.matches(
                        "(description|id|url|image_url|group_ids|facets|keywords)")) {
                    metadata.put(propertyName, propertyValue);
                } else {
                    String camelCasePropertyName =
                            propertyName.replaceFirst(
                                    "_[a-z]",
                                    String.valueOf(
                                            Character.toUpperCase(
                                                    propertyName.charAt(
                                                            propertyName.indexOf("_") + 1))));
                    result.put(camelCasePropertyName, propertyValue);
                }
            }

            // Add metadata to result data object
            result.put("metadata", metadata);

            // Suggested score is already at the top level but its name needs to be converted to
            // camelcase
            if (result.has("suggested_score")) {
                result.put("suggestedScore", result.get("suggested_score"));
            }
        }

        return results;
    }

    /**
     * Send a full catalog to replace the current one (sync)
     *
     * @param req the catalog request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String replaceCatalog(CatalogRequest req) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "catalog"));
            HttpUrl.Builder urlBuilder =
                    url.newBuilder().addQueryParameter("section", req.getSection());
            MultipartBody.Builder multipartBuilder =
                    new MultipartBody.Builder().setType(MultipartBody.FORM);
            String notificationEmail = req.getNotificationEmail();
            Boolean force = req.getForce();
            Map<String, File> files = req.getFiles();

            if (notificationEmail != null) {
                urlBuilder.addQueryParameter("notification_email", notificationEmail);
            }
            if (force != null) {
                urlBuilder.addQueryParameter("force", Boolean.toString(force));
            }

            url = urlBuilder.build();

            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    String fileName = entry.getKey();
                    File file = entry.getValue();

                    multipartBuilder.addFormDataPart(
                            fileName,
                            fileName + ".csv",
                            RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }

            RequestBody requestBody = multipartBuilder.build();
            Request request = this.makeAuthorizedRequestBuilder().url(url).put(requestBody).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            String errorMessage = exception.getMessage();

            if (errorMessage == "Multipart body must have at least one part.") {
                throw new ConstructorException(
                        "At least one file of \"items\", \"variations\", \"item_groups\" is"
                                + " required.");
            } else {
                throw new ConstructorException(exception);
            }
        }
    }

    /**
     * Send a partial catalog to update specific items (delta)
     *
     * @param req the catalog request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String updateCatalog(CatalogRequest req) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "catalog"));
            HttpUrl.Builder urlBuilder =
                    url.newBuilder().addQueryParameter("section", req.getSection());
            MultipartBody.Builder multipartBuilder =
                    new MultipartBody.Builder().setType(MultipartBody.FORM);
            String notificationEmail = req.getNotificationEmail();
            Boolean force = req.getForce();
            Map<String, File> files = req.getFiles();

            if (notificationEmail != null) {
                urlBuilder.addQueryParameter("notification_email", notificationEmail);
            }
            if (force != null) {
                urlBuilder.addQueryParameter("force", Boolean.toString(force));
            }

            url = urlBuilder.build();

            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    String fileName = entry.getKey();
                    File file = entry.getValue();

                    multipartBuilder.addFormDataPart(
                            fileName,
                            fileName + ".csv",
                            RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }

            RequestBody requestBody = multipartBuilder.build();
            Request request =
                    this.makeAuthorizedRequestBuilder().url(url).patch(requestBody).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            String errorMessage = exception.getMessage();

            if (errorMessage == "Multipart body must have at least one part.") {
                throw new ConstructorException(
                        "At least one file of \"items\", \"variations\", \"item_groups\" is"
                                + " required.");
            } else {
                throw new ConstructorException(exception);
            }
        }
    }

    /**
     * Send a patch delta catalog to update specific items (delta)
     *
     * @param req the catalog request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String patchCatalog(CatalogRequest req) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "catalog"));
            HttpUrl.Builder urlBuilder =
                    url.newBuilder().addQueryParameter("section", req.getSection());
            MultipartBody.Builder multipartBuilder =
                    new MultipartBody.Builder().setType(MultipartBody.FORM);
            String notificationEmail = req.getNotificationEmail();
            Boolean force = req.getForce();
            Map<String, File> files = req.getFiles();

            if (notificationEmail != null) {
                urlBuilder.addQueryParameter("notification_email", notificationEmail);
            }
            if (force != null) {
                urlBuilder.addQueryParameter("force", Boolean.toString(force));
            }
            if (req.getOnMissing() != CatalogRequest.OnMissing.FAIL) {
                urlBuilder.addQueryParameter("on_missing", req.getOnMissing().name());
            }

            urlBuilder.addQueryParameter("patch_delta", Boolean.toString(true));

            url = urlBuilder.build();

            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    String fileName = entry.getKey();
                    File file = entry.getValue();

                    multipartBuilder.addFormDataPart(
                            fileName,
                            fileName + ".csv",
                            RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }

            RequestBody requestBody = multipartBuilder.build();
            Request request =
                    this.makeAuthorizedRequestBuilder().url(url).patch(requestBody).build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            String errorMessage = exception.getMessage();

            if (errorMessage == "Multipart body must have at least one part.") {
                throw new ConstructorException(
                        "At least one file of \"items\", \"variations\", \"item_groups\" is"
                                + " required.");
            } else {
                throw new ConstructorException(exception);
            }
        }
    }

    /**
     * Creates a All Tasks OkHttp request
     *
     * @param req the All Tasks request
     * @param apiVersion version of api for url path
     * @return a All Tasks OkHttp request
     * @throws ConstructorException
     */
    protected Request createAllTasksRequest(AllTasksRequest req, String apiVersion)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList(apiVersion, "tasks");
            HttpUrl url = this.makeUrl(paths);
            HttpUrl.Builder urlBuilder =
                    url.newBuilder().addQueryParameter("page", String.valueOf(req.getPage()));

            String resultsPerPage = String.valueOf(req.getResultsPerPage());
            String startDate = req.getStartDate();
            String endDate = req.getEndDate();
            String status = req.getStatus();
            String type = req.getType();

            if (resultsPerPage != null && !resultsPerPage.equals("0")) {
                urlBuilder.addQueryParameter("num_results_per_page", resultsPerPage);
            }

            if (startDate != null) {
                urlBuilder.addQueryParameter("start_date", startDate);
            }

            if (endDate != null) {
                urlBuilder.addQueryParameter("end_date", endDate);
            }

            if (status != null) {
                urlBuilder.addQueryParameter("status", status);
            }

            if (type != null) {
                urlBuilder.addQueryParameter("type", type);
            }

            url = urlBuilder.build();

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the tasks service for all tasks
     *
     * @param req the all tasks request
     * @return a all tasks response
     * @throws ConstructorException if the request is invalid.
     */
    public AllTasksResponse allTasks(AllTasksRequest req) throws ConstructorException {
        try {
            Request request = createAllTasksRequest(req, "v1");
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createAllTasksResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the tasks service for all tasks
     *
     * @param req the all tasks request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String allTasksAsJson(AllTasksRequest req) throws ConstructorException {
        try {
            Request request = createAllTasksRequest(req, "v1");
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a Task OkHttp request
     *
     * @param req the Task request
     * @param apiVersion version of api for url path
     * @return a Task OkHttp request
     * @throws ConstructorException
     */
    protected Request createTaskRequest(TaskRequest req, String apiVersion)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList(apiVersion, "task", req.getTaskId());
            HttpUrl url = this.makeUrl(paths).newBuilder().build();

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the task service for a task id
     *
     * @param req the task request
     * @return a Task response
     * @throws ConstructorException if the request is invalid.
     */
    public Task task(TaskRequest req) throws ConstructorException {
        try {
            Request request = createTaskRequest(req, "v1");
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createTaskResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the task service for a task id
     *
     * @param req the task request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String taskAsJson(TaskRequest req) throws ConstructorException {
        try {
            Request request = createTaskRequest(req, "v1");
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a All Redirects OkHttp request
     *
     * @param req the All Redirects request
     * @return a All Redirects OkHttp request
     * @throws ConstructorException
     */
    protected Request createAllRedirectsRequest(AllRedirectsRequest req)
            throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("v1", "redirect_rules");
            HttpUrl url = this.makeUrl(paths);
            HttpUrl.Builder urlBuilder = url.newBuilder();

            String resultsPerPage = String.valueOf(req.getResultsPerPage());
            String status = req.getStatus();
            String query = req.getQuery();
            int offset = req.getOffset();
            int page = req.getPage();

            if (page != 0 && offset != 0) {
                throw new IllegalArgumentException("page and offset cannot be used together");
            }

            if (page != 0) {
                urlBuilder.addQueryParameter("page", String.valueOf(page));
            }

            if (resultsPerPage != null && !resultsPerPage.equals("0")) {
                urlBuilder.addQueryParameter("num_results_per_page", resultsPerPage);
            }

            if (query != null) {
                urlBuilder.addQueryParameter("query", query);
            }

            if (status != null) {
                urlBuilder.addQueryParameter("status", status);
            }

            if (offset != 0) {
                urlBuilder.addQueryParameter("offset", String.valueOf(offset));
            }

            url = urlBuilder.build();

            Request request = this.makeAuthorizedRequestBuilder().url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the redirects service for all redirects
     *
     * @param req the all redirects request
     * @return a all redirects response
     * @throws ConstructorException if the request is invalid.
     */
    public AllRedirectsResponse allRedirects(AllRedirectsRequest req) throws ConstructorException {
        try {
            Request request = createAllRedirectsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createAllRedirectsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the redirects service for all redirects
     *
     * @param req the all redirects request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String allRedirectsAsJson(AllRedirectsRequest req) throws ConstructorException {
        try {
            Request request = createAllRedirectsRequest(req);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a Quiz OkHttp request
     *
     * @param req the QuizRequestBase (can be QuizRequest or QuizResultsConfigRequest)
     * @param type the type of quiz request (next/results/results_config)
     * @return a Task OkHttp request
     * @throws ConstructorException
     */
    protected Request createQuizRequest(QuizRequestBase req, String type, UserInfo userInfo)
            throws ConstructorException {
        try {
            if (!type.equals("next") && !type.equals("results") && !type.equals("results_config"))
                throw new IllegalArgumentException(
                        "type must be either 'next' or 'results' or 'results_config'");

            List<String> paths = Arrays.asList("v1", "quizzes", req.getQuizId(), type);
            HttpUrl url = this.makeUrl(paths);

            if (req.getSection() != null) {
                url = url.newBuilder().addQueryParameter("section", req.getSection()).build();
            }

            if (req.getQuizVersionId() != null) {
                url =
                        url.newBuilder()
                                .addQueryParameter("quiz_version_id", req.getQuizVersionId())
                                .build();
            }

            // Handle specific logic for QuizRequest
            if (req instanceof QuizRequest) {
                QuizRequest quizRequest = (QuizRequest) req;

                if (quizRequest.getQuizSessionId() != null) {
                    url =
                            url.newBuilder()
                                    .addQueryParameter(
                                            "quiz_session_id", quizRequest.getQuizSessionId())
                                    .build();
                }

                if (quizRequest.getAnswers().size() > 0) {
                    for (List<String> questionAnswers : quizRequest.getAnswers()) {
                        String answerParam = String.join(",", questionAnswers);
                        url = url.newBuilder().addQueryParameter("a", answerParam).build();
                    }
                } else {
                    if (type.equals("results")) {
                        throw new IllegalArgumentException(
                                "answers is a required parameter for a results request");
                    }
                }
            }

            Request request = this.makeUserRequestBuilder(userInfo).url(url).get().build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for a quiz question
     *
     * @param req the Quiz request
     * @return a Quiz Question Response
     * @throws ConstructorException if the request is invalid.
     */
    public QuizQuestionResponse quizNextQuestion(QuizRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "next", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createQuizQuestionResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the a quiz question
     *
     * @param req the Quiz request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String quizNextQuestionAsJson(QuizRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "next", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the quiz results
     *
     * @param req the Quiz request
     * @return a Quiz Results Response
     * @throws ConstructorException if the request is invalid.
     */
    public QuizResultsResponse quizResults(QuizRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "results", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createQuizResultsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the quiz results
     *
     * @param req the Quiz request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String quizResultsAsJson(QuizRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "results", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the quiz results page configurations
     *
     * @param req the Quiz Results Config request
     * @return a Quiz Results Config Response
     * @throws ConstructorException if the request is invalid.
     */
    public QuizResultsConfigResponse quizResultsConfig(
            QuizResultsConfigRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "results_config", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createQuizResultsConfigResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the quiz results configuration
     *
     * @param req the Quiz Results Config request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String quizResultsConfigAsJson(QuizResultsConfigRequest req, UserInfo userInfo)
            throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "results_config", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Create a facet configuration
     *
     * @param facetConfigurationRequest the facet configuration request
     * @return returns the created facet
     * @throws ConstructorException if the request is invalid.
     */
    public String createFacetConfiguration(FacetConfigurationRequest facetConfigurationRequest)
            throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "facets"));
            url =
                    url.newBuilder()
                            .addQueryParameter("section", facetConfigurationRequest.getSection())
                            .build();

            String params = new Gson().toJson(facetConfigurationRequest.geFacetConfiguration());
            RequestBody body =
                    RequestBody.create(params, MediaType.parse("application/json; charset=utf-8"));
            Request request = this.makeAuthorizedRequestBuilder().url(url).post(body).build();

            Response response = client.newCall(request).execute();

            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Delete a facet configuration
     *
     * @param facetName the facet name
     * @param section the section to which the facet belongs
     * @return returns the deleted facet
     * @throws ConstructorException if the request is invalid.
     */
    public String deleteFacetConfiguration(String facetName, String section)
            throws ConstructorException {
        try {
            HttpUrl url =
                    this.makeUrl(Arrays.asList("v1", "facets", facetName))
                            .newBuilder()
                            .addQueryParameter("section", section)
                            .build();

            Request request = this.makeAuthorizedRequestBuilder().url(url).delete().build();

            Response response = client.newCall(request).execute();

            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    public String deleteFacetConfiguration(String facetName) throws ConstructorException {
        return deleteFacetConfiguration(facetName, "Products");
    }

    /**
     * Delete a facet configuration
     *
     * @param facetConfigurationRequest the facetConfiguration request
     * @return returns the deleted facet
     * @throws ConstructorException if the request is invalid.
     */
    public String deleteFacetConfiguration(FacetConfigurationRequest facetConfigurationRequest)
            throws ConstructorException {
        return deleteFacetConfiguration(
                facetConfigurationRequest.geFacetConfiguration().getName(),
                facetConfigurationRequest.getSection());
    }

    /**
     * Creates or Updates facet option configurations
     *
     * @param facetOptionConfigurationsRequest The facet option configurations request containing
     *     the configurations to create or update
     * @return returns the created facet option configurations
     * @throws ConstructorException if the request is invalid
     */
    public String createOrUpdateFacetOptionConfigurations(
            FacetOptionConfigurationsRequest facetOptionConfigurationsRequest)
            throws ConstructorException {
        try {
            HttpUrl url =
                    this.makeUrl(
                            Arrays.asList(
                                    "v1",
                                    "facets",
                                    facetOptionConfigurationsRequest.getFacetName(),
                                    "options"));
            url =
                    url.newBuilder()
                            .addQueryParameter(
                                    "section", facetOptionConfigurationsRequest.getSection())
                            .build();

            String params =
                    new Gson()
                            .toJson(
                                    facetOptionConfigurationsRequest
                                            .getFacetOptionConfigurations());
            RequestBody body =
                    RequestBody.create(params, MediaType.parse("application/json; charset=utf-8"));
            Request request = this.makeAuthorizedRequestBuilder().url(url).patch(body).build();

            Response response = client.newCall(request).execute();

            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Creates a facet option configuration
     *
     * @param facetOptionConfigurationRequest The facet option configuration request containing the
     *     configuration to create
     * @return returns the created facet option configuration
     * @throws ConstructorException if the request is invalid
     */
    public String createFacetOptionConfiguration(
            FacetOptionConfigurationRequest facetOptionConfigurationRequest)
            throws ConstructorException {
        try {
            HttpUrl url =
                    this.makeUrl(
                            Arrays.asList(
                                    "v1",
                                    "facets",
                                    facetOptionConfigurationRequest.getFacetName(),
                                    "options"));
            url =
                    url.newBuilder()
                            .addQueryParameter(
                                    "section", facetOptionConfigurationRequest.getSection())
                            .build();

            String params =
                    new Gson()
                            .toJson(facetOptionConfigurationRequest.getFacetOptionConfiguration());
            RequestBody body =
                    RequestBody.create(params, MediaType.parse("application/json; charset=utf-8"));
            Request request = this.makeAuthorizedRequestBuilder().url(url).post(body).build();

            Response response = client.newCall(request).execute();

            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Deletes a facet option configuration
     *
     * @param facetName the name of the facet
     * @param facetOptionValue the value of the facet option to delete
     * @param section the section of the facet
     * @return returns the deleted facet option configuration
     * @throws ConstructorException if the request is invalid
     */
    public String deleteFacetOptionConfiguration(
            String facetName, String facetOptionValue, String section) throws ConstructorException {
        if (facetName == null || facetName.trim().isEmpty()) {
            throw new IllegalArgumentException("facetName is required");
        }
        if (facetOptionValue == null || facetOptionValue.trim().isEmpty()) {
            throw new IllegalArgumentException("facetOptionValue is required");
        }

        try {
            HttpUrl url =
                    this.makeUrl(
                            Arrays.asList("v1", "facets", facetName, "options", facetOptionValue));
            url = url.newBuilder().addQueryParameter("section", section).build();

            Request request = this.makeAuthorizedRequestBuilder().url(url).delete().build();

            Response response = client.newCall(request).execute();

            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Deletes a facet option configuration with default section "Products"
     *
     * @param facetName the name of the facet
     * @param facetOptionValue the value of the facet option to delete
     * @return returns the deleted facet option configuration
     * @throws ConstructorException if the request is invalid
     */
    public String deleteFacetOptionConfiguration(String facetName, String facetOptionValue)
            throws ConstructorException {
        return deleteFacetOptionConfiguration(facetName, facetOptionValue, "Products");
    }

    /**
     * Deletes a facet option configuration
     *
     * @param facetOptionConfigurationRequest The facet option configuration request containing the
     *     configuration to delete
     * @return returns the deleted facet option configuration
     * @throws ConstructorException if the request is invalid
     */
    public String deleteFacetOptionConfiguration(
            FacetOptionConfigurationRequest facetOptionConfigurationRequest)
            throws ConstructorException {
        return deleteFacetOptionConfiguration(
                facetOptionConfigurationRequest.getFacetName(),
                facetOptionConfigurationRequest.getFacetOptionConfiguration().getValue(),
                facetOptionConfigurationRequest.getSection());
    }
}
