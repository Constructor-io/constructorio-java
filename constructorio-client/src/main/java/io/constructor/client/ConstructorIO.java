package io.constructor.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import io.constructor.client.models.AutocompleteResponse;
import io.constructor.client.models.BrowseResponse;
import io.constructor.client.models.SearchResponse;
import io.constructor.client.models.RecommendationsResponse;
import io.constructor.client.models.ServerError;
import io.constructor.client.models.AllTasksResponse;
import io.constructor.client.models.Task;
import io.constructor.client.models.NextQuizResponse;
import io.constructor.client.models.FinalizeQuizResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Constructor.io Client
 */
public class ConstructorIO {

    /**
     * the HTTP client used by all instances
     */
    private static OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new ConstructorInterceptor())
        .retryOnConnectionFailure(false)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build();

    /**
     * the HTTP client used by all instances (with retry, only for idempotent requests like GET)
     */
    private static OkHttpClient clientWithRetry = new OkHttpClient.Builder()
        .addInterceptor(new ConstructorInterceptor())
        .retryOnConnectionFailure(true)
        .build();

    /**
     * @param newClient the HTTP client to use by all instances
     */
    protected static void setClient(OkHttpClient newClient) {
        client = newClient;
    }

    /**
     * @return the HTTP client used by all instances
     */
    protected static OkHttpClient getClient() {
        return client;
    }

    private String credentials;
    public String apiToken;
    public String apiKey;
    public String protocol;
    public String host;
    public Integer port;
    public String version;
    public String constructorToken;

    /**
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publicly in your in-site javascript client.
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that you let this value be null, in which case the host defaults to the Constructor.io autocomplete service at ac.cnstrc.com.
     * @param constructorToken The token provided by Constructor to identify your company's traffic if proxying requests for results
     */
    public ConstructorIO(String apiToken, String apiKey, boolean isHTTPS, String host, String constructorToken) {
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
        this.credentials = "Basic " + Base64.getEncoder().encodeToString((this.apiToken + ":").getBytes());
    }

    /**
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publicly in your in-site javascript client.
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that you let this value be null, in which case the host defaults to the Constructor.io autocomplete service at ac.cnstrc.com.
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

    /**
     * Sets apiKey
     */
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
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .get()
                .build();

            Response response = clientWithRetry.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Adds an item to your autocomplete.
     *
     * @param item the item that you're adding.
     * @param autocompleteSection the section of the autocomplete that you're adding the item to.
     * @return true if working
     * @throws ConstructorException if the request is invalid.
     */
    public boolean addItem(ConstructorItem item, String autocompleteSection) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "item"));
            Map<String, Object> data = item.toMap();
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .post(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Adds an item to your autocomplete or updates it if it already exists.
     *
     * @param item the item that you're adding.
     * @param autocompleteSection the section of the autocomplete that you're adding the item to.
     * @return true if working
     * @throws ConstructorException if the request is invalid.
     */
    public boolean addOrUpdateItem(ConstructorItem item, String autocompleteSection) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "item"));
            url = url.newBuilder().addQueryParameter("force", "1").build();
            Map<String, Object> data = item.toMap();
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .put(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Adds multiple items to your autocomplete (limit of 1000 items)
     *
     * @param items the items you want to add.
     * @param autocompleteSection the section of the autocomplete that you're adding the items to.
     * @return true if working
     * @throws ConstructorException if the request is invalid.
     */
    public boolean addItemBatch(ConstructorItem[] items, String autocompleteSection) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "batch_items"));
            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .post(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Adds multiple items to your autocomplete whilst updating existing ones (limit of 1000 items)
     *
     * @param items the items you want to add.
     * @param autocompleteSection the section of the autocomplete that you're adding the items to.
     * @return true if working
     * @throws ConstructorException if the request is invalid.
     */
    public boolean addOrUpdateItemBatch(ConstructorItem[] items, String autocompleteSection) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "batch_items"));
            url = url.newBuilder().addQueryParameter("force", "1").build();
            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .put(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Removes an item from your autocomplete.
     *
     * @param item the item that you're removing.
     * @param autocompleteSection the section of the autocomplete that you're removing the item from.
     * @return true if successfully removed
     * @throws ConstructorException if the request is invalid.
     */
    public boolean removeItem(ConstructorItem item, String autocompleteSection) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "item"));
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("item_name", item.getItemName());
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .delete(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Removes multiple items from your autocomplete (limit of 1000 items)
     *
     * @param items the items that you are removing
     * @param autocompleteSection the section of the autocomplete that you're removing the items from.
     * @return true if successfully removed
     * @throws ConstructorException if the request is invalid
     */
    public boolean removeItemBatch(ConstructorItem[] items, String autocompleteSection) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "batch_items"));
            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .delete(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Modifies an item from your autocomplete.
     *
     * @param item the item that you're modifying.
     * @param autocompleteSection the section of the autocomplete that you're modifying the item for.
     * @param previousItemName the previous name of the item.
     * @return true if successfully modified
     * @throws ConstructorException if the request is invalid.
     */
    public boolean modifyItem(ConstructorItem item, String autocompleteSection, String previousItemName) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl(Arrays.asList("v1", "item"));
            Map<String, Object> data = item.toMap();
            data.put("new_item_name", item.getItemName());
            data.put("autocomplete_section", autocompleteSection);
            data.put("item_name", previousItemName);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .put(body)
                .build();

            Response response = client.newCall(request).execute();
            getResponseBody(response);
            return true;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the autocomplete service.
     *
     * Note that if you're making an autocomplete request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the autocomplete request
     * @param userInfo optional information about the user
     * @return an autocomplete response
     * @throws ConstructorException if the request is invalid.
     */
    public AutocompleteResponse autocomplete(AutocompleteRequest req, UserInfo userInfo) throws ConstructorException {
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
     * Note that if you're making an autocomplete request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the autocomplete request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String autocompleteAsJSON(AutocompleteRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("autocomplete", req.getQuery());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);

            for (Map.Entry<String, Integer> entry : req.getResultsPerSection().entrySet()) {
                String section = entry.getKey();
                String count = String.valueOf(entry.getValue());
                url = url.newBuilder()
                    .addQueryParameter("num_results_" + section, count)
                    .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url = url.newBuilder()
                    .addQueryParameter("hidden_fields", hiddenField)
                    .build();
            }
            
            for (String filterName : req.getFilters().keySet()) {
                for (String facetValue : req.getFilters().get(filterName)) {
                    url = url.newBuilder()
                        .addQueryParameter("filters[" + filterName + "]", facetValue)
                        .build();
                }
            }

            Request request = this.makeUserRequestBuilder(userInfo)
                .url(url)
                .get()
                .build();

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
    protected Request createSearchRequest(SearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("search", req.getQuery());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);
            url = url.newBuilder()
                .addQueryParameter("section", req.getSection())
                .addQueryParameter("page", String.valueOf(req.getPage()))
                .addQueryParameter("num_results_per_page", String.valueOf(req.getResultsPerPage()))
                .build();

            if (req.getGroupId() != null) {
                url = url.newBuilder()
                    .addQueryParameter("filters[group_id]", req.getGroupId())
                    .build();
            }

            for (String facetName : req.getFacets().keySet()) {
                for (String facetValue : req.getFacets().get(facetName)) {
                    url = url.newBuilder()
                        .addQueryParameter("filters[" + facetName + "]", facetValue)
                        .build();
                }
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
                String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                  url = url.newBuilder()
                      .addQueryParameter("fmt_options[" + formatOptionKey + "]", formatOptionValue)
                      .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url = url.newBuilder()
                    .addQueryParameter("hidden_fields", hiddenField)
                    .build();
            }

            if (StringUtils.isNotBlank(req.getSortBy())) {
                url = url.newBuilder()
                    .addQueryParameter("sort_by", req.getSortBy())
                    .addQueryParameter("sort_order", req.getSortAscending() ? "ascending" : "descending")
                    .build();
            }

            if (req.getCollectionId() != null) {
                url = url.newBuilder()
                .addQueryParameter("collection_id", req.getCollectionId())
                .build();
            }

            Request request = this.makeUserRequestBuilder(userInfo)
                .url(url)
                .get()
                .build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service.
     *
     * Note that if you're making a search request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
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
     * Note that if you're making a search request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @param c a callback with success and failure conditions
     * @throws ConstructorException if the request is invalid.
     */
    public void search(SearchRequest req, UserInfo userInfo, final SearchCallback c) throws ConstructorException {
        try {
            Request request = createSearchRequest(req, userInfo);
            clientWithRetry.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    c.onFailure(new ConstructorException(e));
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
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
     * Note that if you're making a search request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
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
    protected Request createBrowseRequest(BrowseRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("browse", req.getFilterName(), req.getFilterValue());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);
            url = url.newBuilder()
                .addQueryParameter("section", req.getSection())
                .addQueryParameter("page", String.valueOf(req.getPage()))
                .addQueryParameter("num_results_per_page", String.valueOf(req.getResultsPerPage()))
                .build();

            if (req.getGroupId() != null) {
                url = url.newBuilder()
                    .addQueryParameter("filters[group_id]", req.getGroupId())
                    .build();
            }

            for (String facetName : req.getFacets().keySet()) {
                for (String facetValue : req.getFacets().get(facetName)) {
                    url = url.newBuilder()
                        .addQueryParameter("filters[" + facetName + "]", facetValue)
                        .build();
                }
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
              String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url = url.newBuilder()
                    .addQueryParameter("fmt_options[" + formatOptionKey + "]", formatOptionValue)
                    .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url = url.newBuilder()
                    .addQueryParameter("hidden_fields", hiddenField)
                    .build();
            }

            if (StringUtils.isNotBlank(req.getSortBy())) {
                url = url.newBuilder()
                    .addQueryParameter("sort_by", req.getSortBy())
                    .addQueryParameter("sort_order", req.getSortAscending() ? "ascending" : "descending")
                    .build();
            }

            Request request = this.makeUserRequestBuilder(userInfo)
                .url(url)
                .get()
                .build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service.
     *
     * Note that if you're making a browse request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
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
     * Note that if you're making a browse request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the browse request
     * @param userInfo optional information about the user
     * @param c a callback with success and failure conditions
     * @throws ConstructorException if the request is invalid.
     */
    public void browse(BrowseRequest req, UserInfo userInfo, final BrowseCallback c) throws ConstructorException {
        try {
            Request request = createBrowseRequest(req, userInfo);
            clientWithRetry.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    c.onFailure(new ConstructorException(e));
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
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
     * Note that if you're making a browse request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
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
    protected Request createBrowseItemsRequest(BrowseItemsRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("browse", "items");
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);
            url = url.newBuilder()
                .addQueryParameter("section", req.getSection())
                .addQueryParameter("page", String.valueOf(req.getPage()))
                .addQueryParameter("num_results_per_page", String.valueOf(req.getResultsPerPage()))
                .build();

            for (String id : req.getIds()) {
                url = url.newBuilder()
                    .addQueryParameter("ids", id)
                    .build();
            }

            if (req.getGroupId() != null) {
                url = url.newBuilder()
                    .addQueryParameter("filters[group_id]", req.getGroupId())
                    .build();
            }

            for (String facetName : req.getFacets().keySet()) {
                for (String facetValue : req.getFacets().get(facetName)) {
                    url = url.newBuilder()
                        .addQueryParameter("filters[" + facetName + "]", facetValue)
                        .build();
                }
            }

            for (String formatOptionKey : req.getFormatOptions().keySet()) {
              String formatOptionValue = req.getFormatOptions().get(formatOptionKey);
                url = url.newBuilder()
                    .addQueryParameter("fmt_options[" + formatOptionKey + "]", formatOptionValue)
                    .build();
            }

            if (StringUtils.isNotBlank(req.getSortBy())) {
                url = url.newBuilder()
                    .addQueryParameter("sort_by", req.getSortBy())
                    .addQueryParameter("sort_order", req.getSortAscending() ? "ascending" : "descending")
                    .build();
            }

            for (String hiddenField : req.getHiddenFields()) {
                url = url.newBuilder()
                        .addQueryParameter("hidden_fields", hiddenField)
                        .build();
            }

            Request request = this.makeUserRequestBuilder(userInfo)
                .url(url)
                .get()
                .build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the browse service with item id's.
     *
     * Note that if you're making a browse request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @return a browse response
     * @throws ConstructorException if the request is invalid.
     */
    public BrowseResponse browseItems(BrowseItemsRequest req, UserInfo userInfo) throws ConstructorException {
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
     * Note that if you're making a browse request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @param c a callback with success and failure conditions
     * @throws ConstructorException if the request is invalid.
     */
    public void browseItems(BrowseItemsRequest req, UserInfo userInfo, final BrowseCallback c) throws ConstructorException {
        try {
            Request request = createBrowseItemsRequest(req, userInfo);
            clientWithRetry.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    c.onFailure(new ConstructorException(e));
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
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
     * Note that if you're making a browse request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the browse items request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String browseItemsAsJSON(BrowseItemsRequest req, UserInfo userInfo) throws ConstructorException {
      try {
        Request request = createBrowseItemsRequest(req, userInfo);
        Response response = clientWithRetry.newCall(request).execute();
        return getResponseBody(response);
      } catch (Exception exception) {
          throw new ConstructorException(exception);
      }
    }

    /**
     * Queries the search service with natural language processing.
     *
     * Note that if you're making a search request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the natural language search request
     * @param userInfo optional information about the user
     * @return a search response
     * @throws ConstructorException if the request is invalid.
     */
    public SearchResponse naturalLanguageSearch(NaturalLanguageSearchRequest req, UserInfo userInfo) throws ConstructorException {
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
     * Note that if you're making a search request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the natural language search request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String naturalLanguageSearchAsJSON(NaturalLanguageSearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("search", "natural_language", req.getQuery());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);

            url = url.newBuilder()
                .addQueryParameter("section", req.getSection())
                .addQueryParameter("page", String.valueOf(req.getPage()))
                .addQueryParameter("num_results_per_page", String.valueOf(req.getResultsPerPage()))
                .build();

            Request request = this.makeUserRequestBuilder(userInfo)
                .url(url)
                .get()
                .build();

            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the recommendations service to retrieve results.
     *
     * Note that if you're making a recommendations request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the recommendations request
     * @param userInfo optional information about the user
     * @return a recommendations response
     * @throws ConstructorException if the request is invalid.
     */
    public RecommendationsResponse recommendations(RecommendationsRequest req, UserInfo userInfo) throws ConstructorException {
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
     * Note that if you're making an recommendations request for a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the recommendations request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String recommendationsAsJSON(RecommendationsRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList("recommendations", "v1", "pods", req.getPodId());
            HttpUrl url = (userInfo == null) ? this.makeUrl(paths) : this.makeUrl(paths, userInfo);

            url = url.newBuilder()
                .addQueryParameter("num_results", String.valueOf(req.getNumResults()))
                .addQueryParameter("section", req.getSection())
                .build();

            if (req.getItemIds() != null) {
                for (String itemId : req.getItemIds()) {
                    url = url.newBuilder()
                        .addQueryParameter("item_id", itemId)
                        .build();
                }
            }

            Request request = this.makeUserRequestBuilder(userInfo)
                .url(url)
                .get()
                .build();

            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Makes a URL to issue the requests to.  Note that the URL will automagically have the apiKey embedded.
     *
     * @param paths endpoint of the autocomplete service.
     * @return the created URL. Now you can use it to issue requests and things!
     */
    protected HttpUrl makeUrl(List<String> paths) throws UnsupportedEncodingException {
        okhttp3.HttpUrl.Builder builder = new HttpUrl.Builder()
            .scheme(this.protocol)
            .addQueryParameter("key", this.apiKey)
            .host(this.host);

        if (!paths.contains("catalog") && !paths.contains("tasks") && !paths.contains("task")) {
            builder.addQueryParameter("c", this.version);
        }

        for (String path : paths) {
          String canonicalPath = OkHttpUrlBuilderUtils.canonicalize(path, 0, path.length(), 
            OkHttpUrlBuilderUtils.PATH_SEGMENT_ENCODE_SET, false, true, true, true, null);
          builder.addEncodedPathSegment(canonicalPath);
        }

        if (this.port != null) {
          builder.port(this.port);
        }
       
        return builder.build();
    }

    /**
     * Makes a URL to issue the requests to.  Note that the URL will automagically have the apiKey embedded.
     *
     * @param paths endpoint of the autocomplete service.
     * @return the created URL. Now you can use it to issue requests and things!
     */
    protected HttpUrl makeUrl(List<String> paths, UserInfo info) throws UnsupportedEncodingException {
        okhttp3.HttpUrl.Builder builder = new HttpUrl.Builder()
            .scheme(this.protocol)
            .addQueryParameter("key", this.apiKey)
            .addQueryParameter("c", this.version)
            .addQueryParameter("s", String.valueOf(info.getSessionId()))
            .addQueryParameter("i", info.getClientId())
            .host(this.host);

        for (String path : paths) {
          String canonicalPath = OkHttpUrlBuilderUtils.canonicalize(path, 0, path.length(),
            OkHttpUrlBuilderUtils.PATH_SEGMENT_ENCODE_SET, false, true, true, true, null);
          builder.addEncodedPathSegment(canonicalPath);
        }

        if (info.getUserId() != null) {
          builder.addQueryParameter("ui", String.valueOf(info.getUserId()));
        }

        if (info.getUserSegments() != null) {
            for (String userSegment : info.getUserSegments()) {
              builder.addQueryParameter("us",  userSegment);
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
      return "ciojava-5.15.1";
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an autocomplete response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
     */
    protected static AutocompleteResponse createAutocompleteResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONObject sections = json.getJSONObject("sections");
        for (Object sectionKey : sections.keySet()) {
            String sectionName = (String)sectionKey;
            JSONArray results = sections.getJSONArray(sectionName);
            moveMetadataOutOfResultData(results);
        }
        String transformed = json.toString();
        return new Gson().fromJson(transformed, AutocompleteResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an search response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
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
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
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
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an recommendations response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
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
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an All Tasks response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
     */
    protected static AllTasksResponse createAllTasksResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, AllTasksResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Task response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
     */
    protected static Task createTaskResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, Task.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Next Quiz response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
     */
    protected static NextQuizResponse createNextQuizResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, NextQuizResponse.class);
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into a Task response.
     * Using JSON objects to achieve this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
     */
    protected static FinalizeQuizResponse createFinalizeQuizResponse(String string) {
        JSONObject json = new JSONObject(string);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, FinalizeQuizResponse.class);
    }

    /**
     * Moves metadata out of the result data for an array of results
     * @param results A JSON array of results
     */
    protected static void moveMetadataOutOfResultData(JSONArray results) {
        for (int i = 0; i < results.length(); i++) {

            JSONObject result = results.getJSONObject(i);
            JSONObject resultData = result.getJSONObject("data");
            JSONObject metadata = new JSONObject();
            
            // Recursive call to move unspecified properties in result variations to its metadata object
            if (!result.isNull("variations")) {
                JSONArray variations = result.getJSONArray("variations");
                moveMetadataOutOfResultData(variations);
            }

            // Move unspecified properties in result data object to metadata object
            for (Object propertyKey : resultData.keySet()) {
                String propertyName = (String)propertyKey;
                if (!propertyName.matches("(description|id|url|image_url|groups|facets|variation_id)")) {
                    metadata.put(propertyName, resultData.get(propertyName));
                }
            }

            // Remove unspecified properties from result data object
            for (Object propertyKey : metadata.keySet()) {
                String propertyName = (String)propertyKey;
                resultData.remove(propertyName);
            }

            // Add metadata to result data object
            resultData.put("metadata", metadata);
        }
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
            HttpUrl url = this.makeUrl(Arrays.asList("v1","catalog"));
            HttpUrl.Builder urlBuilder = url.newBuilder()
                .addQueryParameter("section", req.getSection());
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
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
                for (Map.Entry<String,File> entry : files.entrySet()) {
                    String fileName = entry.getKey();
                    File file = entry.getValue();

                    multipartBuilder.addFormDataPart(fileName, fileName + ".csv", RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }

            RequestBody requestBody = multipartBuilder.build();
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .put(requestBody)
                .build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            String errorMessage = exception.getMessage();

            if (errorMessage == "Multipart body must have at least one part.") {
                throw new ConstructorException("At least one file of \"items\", \"variations\", \"item_groups\" is required.");
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
            HttpUrl url = this.makeUrl(Arrays.asList("v1","catalog"));
            HttpUrl.Builder urlBuilder = url.newBuilder()
                .addQueryParameter("section", req.getSection());
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
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
                for (Map.Entry<String,File> entry : files.entrySet()) {
                    String fileName = entry.getKey();
                    File file = entry.getValue();

                    multipartBuilder.addFormDataPart(fileName, fileName + ".csv", RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }

            RequestBody requestBody = multipartBuilder.build();
            Request request = this.makeAuthorizedRequestBuilder()
                .url(url)
                .patch(requestBody)
                .build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            String errorMessage = exception.getMessage();

            if (errorMessage == "Multipart body must have at least one part.") {
                throw new ConstructorException("At least one file of \"items\", \"variations\", \"item_groups\" is required.");
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
    protected Request createAllTasksRequest(AllTasksRequest req, String apiVersion) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList(apiVersion, "tasks");
            HttpUrl url = this.makeUrl(paths);

            url = url.newBuilder()
                    .addQueryParameter("page", String.valueOf(req.getPage()))
                    .addQueryParameter("num_results_per_page", String.valueOf(req.getResultsPerPage()))
                    .build();

            Request request = this.makeAuthorizedRequestBuilder()
                    .url(url)
                    .get()
                    .build();

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
    protected Request createTaskRequest(TaskRequest req, String apiVersion) throws ConstructorException {
        try {
            List<String> paths = Arrays.asList(apiVersion, "task", req.getTaskId());
            HttpUrl url = this.makeUrl(paths).newBuilder().build();

            Request request = this.makeAuthorizedRequestBuilder()
                    .url(url)
                    .get()
                    .build();

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
     * Creates a Quiz OkHttp request
     *
     * @param req the Next Quiz request
     * @param type the type of quiz request (next/finalize)
     * @return a Task OkHttp request
     * @throws ConstructorException
     */
    protected Request createQuizRequest(QuizRequest req, String type, UserInfo userInfo) throws ConstructorException {
        try {
            if (!type.equals("next") && !type.equals("finalize"))
                throw new IllegalArgumentException("type must be either 'next' or 'finalize'");

            List<String> paths = Arrays.asList("quizzes", req.getId(), type);
            HttpUrl url = this.makeUrl(paths);
            url = url.newBuilder().addQueryParameter("index_key", req.getIndexKey()).build();

            if (req.getSection() != null) {
                url = url.newBuilder()
                        .addQueryParameter("section", req.getSection())
                        .build();
            }

            if (req.getVersionId() != null) {
                url = url.newBuilder()
                        .addQueryParameter("version_id", req.getVersionId())
                        .build();
            }

            if (req.getA().size() > 0) {
                for (List<String> questionAnswers : req.getA())
                {
                    String answerParam = String.join(",", questionAnswers);
                    url = url.newBuilder().addQueryParameter("a", answerParam).build();
                }
            } else {
                if (type.equals("finalize")) {
                    throw new IllegalArgumentException("a (answers) is a required parameter for a finalize request");
                }
            }

            Request request = this.makeUserRequestBuilder(userInfo)
                    .url(url)
                    .get()
                    .build();

            return request;
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the next quiz/question
     *
     * @param req the Next Quiz request
     * @return a Next Quiz Response
     * @throws ConstructorException if the request is invalid.
     */
    public NextQuizResponse nextQuiz(QuizRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "next", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            String json = getResponseBody(response);
            return createNextQuizResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the quiz service for the next quiz/question
     *
     * @param req the Next Quiz request
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String nextQuizAsJson(QuizRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            Request request = createQuizRequest(req, "next", userInfo);
            Response response = clientWithRetry.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

//    /**
//     * Queries the quiz service for the next quiz/question
//     *
//     * @param req the Next Quiz request
//     * @return a Next Quiz Response
//     * @throws ConstructorException if the request is invalid.
//     */
//    public NextQuizResponse nextQuiz(QuizRequest req) throws ConstructorException {
//        try {
//            Request request = createNextQuizRequest(req);
//            Response response = clientWithRetry.newCall(request).execute();
//            String json = getResponseBody(response);
//            return createNextQuizResponse(json);
//        } catch (Exception exception) {
//            throw new ConstructorException(exception);
//        }
//    }
//
//    /**
//     * Queries the quiz service for the next quiz/question
//     *
//     * @param req the Next Quiz request
//     * @return a string of JSON
//     * @throws ConstructorException if the request is invalid.
//     */
//    public String nextQuizAsJson(QuizRequest req) throws ConstructorException {
//        try {
//            Request request = createNextQuizRequest(req);
//            Response response = clientWithRetry.newCall(request).execute();
//            return getResponseBody(response);
//        } catch (Exception exception) {
//            throw new ConstructorException(exception);
//        }
//    }
}
