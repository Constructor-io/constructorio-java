package io.constructor.client;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.json.JSONArray;
import org.json.JSONObject;

import io.constructor.client.models.AutocompleteResponse;
import io.constructor.client.models.SearchResponse;
import io.constructor.client.models.ItemsResponse;
import io.constructor.client.models.Item;
import io.constructor.client.models.ServerError;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    public String version;

    /**
     * Creates a constructor.io Client.
     *
     * @param apiToken API Token, gotten from your <a href="https://constructor.io/dashboard">Constructor.io Dashboard</a>, and kept secret.
     * @param apiKey API Key, used publically in your in-site javascript client.
     * @param isHTTPS true to use HTTPS, false to use HTTP. It is highly recommended that you use HTTPS.
     * @param host The host of the autocomplete service that you are using. It is recommended that you let this value be null, in which case the host defaults to the Constructor.io autocomplete servic at ac.cnstrc.com.
     */
    public ConstructorIO(String apiToken, String apiKey, boolean isHTTPS, String host) {
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
        this.credentials = "Basic " + Base64.getEncoder().encodeToString((this.apiToken + ":").getBytes());
    }

    /**
     * Verifies that an autocomplete service is working.
     *
     * @return true if working.
     * @throws ConstructorException if the service is not working
     */
    public boolean verify() throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl("v1/verify");
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url = this.makeUrl("v1/item");
            Map<String, Object> data = item.toMap();
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url = this.makeUrl("v1/item");
            url = url.newBuilder().addQueryParameter("force", "1").build();
            Map<String, Object> data = item.toMap();
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url= this.makeUrl("v1/batch_items");
            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url = this.makeUrl("v1/batch_items");
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
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url = this.makeUrl("v1/item");
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("item_name", item.getItemName());
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url = this.makeUrl("v1/batch_items");
            Map<String, Object> data = new HashMap<String, Object>();
            List<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toMap());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
            HttpUrl url = this.makeUrl("v1/item");
            Map<String, Object> data = item.toMap();
            data.put("new_item_name", item.getItemName());
            data.put("autocomplete_section", autocompleteSection);
            data.put("item_name", previousItemName);
            String params = new Gson().toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
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
     * Retrieves all items for the given autocomplete section, paginated by 
     * numResultsPerPage (default 20) for a given page (default 1).
     * 
     * @param autocompleteSection the section of the autocomplete that you're retrieving the items from
     * @return an items response
     * @throws ConstructorException if the request is invalid
     */
    public ItemsResponse getItems(String autocompleteSection) throws ConstructorException {
        return getItems(autocompleteSection, 20, 1);
    }

    /**
     * Retrieves all items for the given autocomplete section, paginated by 
     * numResultsPerPage for a given page.
     * 
     * @param autocompleteSection the section of the autocomplete that you're retrieving the items from
     * @param numResultsPerPage the number of results you want to return (defaults to 20)
     * @param page the page to return (defaults to 1)
     * @return an items response
     * @throws ConstructorException if the request is invalid
     */
    public ItemsResponse getItems(String autocompleteSection, int numResultsPerPage, int page) throws ConstructorException {
        try {
            String json = getItemsAsJSON(autocompleteSection, numResultsPerPage, page);
            return createItemsResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Retrieves all items for the given autocomplete section, paginated by
     * numResultsPerPage (default 20) for a given page (default 1).
     * 
     * @param autocompleteSection the section of the autocomplete that you're retrieving the item from
     * @return a JSON string
     * @throws ConstructorException if the request is invalid
     */
    public String getItemsAsJSON(String autocompleteSection) throws ConstructorException {
        return getItemsAsJSON(autocompleteSection, 20, 1);
    }

    /**
     * Retrieves all items for the given autocomplete section, 
     * paginated by numResultsPerPage for a given page.
     * 
     * @param autocompleteSection the section of the autocomplete that you're retrieving the item from
     * @param numResultsPerPage the number of results you want to return (defaults to 20)
     * @param page the page to return (defaults to 1)
     * @return a JSON string
     * @throws ConstructorException if the request is invalid
     */
    public String getItemsAsJSON(String autocompleteSection, int numResultsPerPage, int page) throws ConstructorException {
        try {
            HttpUrl url = this.makeUrl("v1/item");
            url = url.newBuilder()
                .addQueryParameter("section", autocompleteSection)
                .addQueryParameter("num_results_per_page", String.valueOf(numResultsPerPage))
                .addQueryParameter("page", String.valueOf(page))
                .build();

            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
                .get()
                .build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Retrieves the item with the specified item id and autocomplete section.
     * 
     * @param itemId the id of the item you'd like to retrieve
     * @param autocompleteSection the section of the autocomplete that you're retrieving the item from
     * @return a Constructor Item
     * @throws ConstructorException if the request is invalid
     */
    public Item getItem(String itemId, String autocompleteSection) throws ConstructorException {
        try {
            String json = getItemAsJSON(itemId, autocompleteSection);
            return createItem(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Retrieves the item with the specified item id and autocomplete section.
     * 
     * @param itemId the id of the item you'd like to retrieve
     * @param autocompleteSection the section of the autocomplete that you're retrieving the item from
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid
     */
    public String getItemAsJSON(String itemId, String autocompleteSection) throws ConstructorException {
        try {
            String path = "v1/item/" + itemId;
            HttpUrl url = this.makeUrl(path);
            url = url.newBuilder()
                .addQueryParameter("section", autocompleteSection)
                .build();

            Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", this.credentials)
                .get()
                .build();

            Response response = client.newCall(request).execute();
            return getResponseBody(response);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the autocomplete service.
     *
     * Note that if you're making an autocomplete service on a website, you should definitely use our javascript client instead of doing it server-side!
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
     * Note that if you're making an autocomplete service on a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the autocomplete request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String autocompleteAsJSON(AutocompleteRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            String path = "autocomplete/" + req.getQuery();
            HttpUrl url = (userInfo == null) ? this.makeUrl(path) : this.makeUrl(path, userInfo);

            for (Map.Entry<String, Integer> entry : req.getResultsPerSection().entrySet()) {
                String section = entry.getKey();
                String count = String.valueOf(entry.getValue());
                url = url.newBuilder()
                    .addQueryParameter("num_results_" + section, count)
                    .build();
            }

            Request request = new Request.Builder()
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
     * Queries the search service.
     *
     * Note that if you're making an search service on a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @return a search response
     * @throws ConstructorException if the request is invalid.
     */
    public SearchResponse search(SearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            String json = searchAsJSON(req, userInfo);
            return createSearchResponse(json);
        } catch (Exception exception) {
            throw new ConstructorException(exception);
        }
    }

    /**
     * Queries the search service.
     *
     * Note that if you're making an search service on a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the search request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String searchAsJSON(SearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            String path = "search/" + req.getQuery();
            HttpUrl url = (userInfo == null) ? this.makeUrl(path) : this.makeUrl(path, userInfo);
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

            if (req.getSortBy() != null) {
                url = url.newBuilder()
                    .addQueryParameter("sort_by", req.getSortBy())
                    .addQueryParameter("sort_order", req.getSortAscending() ? "ascending" : "descending")
                    .build();
            }

            Request request = new Request.Builder()
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
     * Queries the search service with natural language processing.
     *
     * Note that if you're making a search service on a website, you should definitely use our javascript client instead of doing it server-side!
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
     * Note that if you're making a search service on a website, you should definitely use our javascript client instead of doing it server-side!
     * That's important. That will be a solid latency difference.
     *
     * @param req the natural language search request
     * @param userInfo optional information about the user
     * @return a string of JSON
     * @throws ConstructorException if the request is invalid.
     */
    public String naturalLanguageSearchAsJSON(NaturalLanguageSearchRequest req, UserInfo userInfo) throws ConstructorException {
        try {
            String path = "search/natural_language/" + req.getQuery();
            HttpUrl url = (userInfo == null) ? this.makeUrl(path) : this.makeUrl(path, userInfo);

            url = url.newBuilder()
                .addQueryParameter("section", req.getSection())
                .addQueryParameter("page", String.valueOf(req.getPage()))
                .addQueryParameter("num_results_per_page", String.valueOf(req.getResultsPerPage()))
                .build();

            Request request = new Request.Builder()
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
     * @param path endpoint of the autocomplete service.
     * @return the created URL. Now you can use it to issue requests and things!
     */
    protected HttpUrl makeUrl(String path) throws UnsupportedEncodingException {
        HttpUrl url = new HttpUrl.Builder()
            .scheme(this.protocol)
            .addPathSegments(path)
            .addQueryParameter("key", this.apiKey)
            .addQueryParameter("c", this.version)
            .host(this.host)
            .build();
        
        return url;
    }

    /**
     * Makes a URL to issue the requests to.  Note that the URL will automagically have the apiKey embedded.
     *
     * @param path endpoint of the autocomplete service.
     * @return the created URL. Now you can use it to issue requests and things!
     */
    protected HttpUrl makeUrl(String path, UserInfo info) throws UnsupportedEncodingException {
        HttpUrl url = new HttpUrl.Builder()
            .scheme(this.protocol)
            .addPathSegments(path)
            .addQueryParameter("key", this.apiKey)
            .addQueryParameter("c", this.version)
            .addQueryParameter("s", String.valueOf(info.getSessionId()))
            .addQueryParameter("i", info.getClientId())
            .host(this.host)
            .build();

        if (info.getUserId() != null) {
            url = url.newBuilder()
                .addQueryParameter("ui", String.valueOf(info.getUserId()))
                .build();            
        }

        if (info.getUserSegments() != null) {
            for (String userSegment : info.getUserSegments()) {
                url = url.newBuilder()
                    .addQueryParameter("us",  userSegment)
                    .build();
            }
        }

        return url;
    }

    /**
     * Checks the response from an endpoint and throws an exception if an error occurred
     * 
     * @return whether the request was successful
     */
    protected static String getResponseBody(Response response) throws ConstructorException {
        String errorMessage = "Unknown error";
        try {
            String body = response.body().string();
            if (response.isSuccessful()) {
                return body;
            } else {
                ServerError error = new Gson().fromJson(body, ServerError.class);
                errorMessage = "[HTTP " + response.code() + "] " + error.getMessage();
            }
        } catch (Exception e) {
            errorMessage = "[HTTP " + response.code() + "]";
        } finally {
            response.close();
        }
        throw new ConstructorException(errorMessage);
    }

    /**
     * Grabs the version number from the pom.xml file.
     *
     * @return version number
     */
    protected String getVersion() {
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader("pom.xml"));
            return "ciojava-" + model.getVersion();
        } catch (Exception e) {
            // Do nothing
        }
        return "ciojava-";
    }

    /**
     * Transforms a JSON string to a new JSON string for easy Gson parsing into an autocomplete response.
     * Using JSON objects to acheive this is considerably less error prone than attempting to do it in
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
     * Using JSON objects to acheive this is considerably less error prone than attempting to do it in
     * a Gson Type Adapter.
     */
    protected static SearchResponse createSearchResponse(String string) {
        JSONObject json = new JSONObject(string);
        JSONObject response = json.getJSONObject("response");
        JSONArray results = response.getJSONArray("results");
        moveMetadataOutOfResultData(results);
        String transformed = json.toString();
        return new Gson().fromJson(transformed, SearchResponse.class);
    }

    /**
     * Parses the JSON string with Gson. 
     */
    protected static Item createItem(String string) {
        return new Gson().fromJson(string, Item.class);
    }

    /**
     * Parses the JSON string with Gson. 
     */
    protected static ItemsResponse createItemsResponse(String string) {
        return new Gson().fromJson(string, ItemsResponse.class);
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
            
            // Move unspecified properties in result data object to metadata object
            for (Object propertyKey : resultData.keySet()) {
                String propertyName = (String)propertyKey;
                if (!propertyName.matches("(description|id|url|image_url|groups|facets)")) {
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
}