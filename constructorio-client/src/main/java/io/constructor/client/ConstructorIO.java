package io.constructor.client;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.http.client.utils.URLEncodedUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Client
 */
public class ConstructorIO {

    public String apiToken;
    public String apiKey;
    public String protocol;
    public String host;
    protected URLEncodedUtils encoder;

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
        if (host == null) {
            this.host = "ac.cnstrc.com";
        }
        if (isHTTPS) {
            this.protocol = "https";
        } else {
            this.protocol = "http";
        }
        this.encoder = new URLEncodedUtils();
        Unirest.setDefaultHeader("Content-Type", "application/json");
    }

    /**
     * Serializes url params in a rudimentary way.
     *
     * Unlike in the other Constructor.io clients, this one only takes in hashmaps,
     * and you must write other helper methods to serialize other things.
     *
     * @param params HashMap of the parameters to encode.
     * @return The encoded parameters, as a String.
     */
    public static String serializeParams(HashMap<String, String> params) throws UnsupportedEncodingException {
        String urlString = "";
        boolean isFirst = true;
        for (String key : params.keySet()) {
            if (!isFirst) {
                urlString += "&";
            }
            urlString += URLEncoder.encode(key, "UTF-8");
            urlString += "=";
            urlString += URLEncoder.encode(params.get(key), "UTF-8");
            isFirst = false;
        }
        return urlString;
    }

    /**
     * Makes a URL to issue the requests to.
     *
     * Note that the URL will automagically have the apiKey embedded.
     *
     * @param endpoint Endpoint of the autocomplete service.
     * @return The created URL. Now you can use it to issue requests and things!
     */
    public String makeUrl(String endpoint) throws UnsupportedEncodingException {
        return String.format("%s://%s/%s?%s", this.protocol, this.host, endpoint, "key=" + this.apiKey);
    }

    /**
     * Checks the response from an endpoint.
     */
    private static boolean checkResponse(HttpResponse<JsonNode> resp, int expectedStatus) throws ConstructorException {
        if (resp.getStatus() != expectedStatus) {
            throw new ConstructorException(resp.getBody().toString());
        } else {
            return true;
        }
    }

    /**
     * Verifies that an autocomplete service is working.
     *
     * @return true if working.
     * @throws ConstructorException if the service is not working
     */
    public boolean verify() throws ConstructorException {
        try {
            String url = this.makeUrl("v1/verify");
            HttpResponse<JsonNode> jsonRes = Unirest.get(url)
                    .basicAuth(this.apiToken, "")
                    .asJson();
            if (checkResponse(jsonRes, 200)) {
                return true;
            }
            return false; // this should not get back here
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
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
            String url = this.makeUrl("v1/item");
            HashMap<String, Object> data = item.toParams();
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.post(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
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
            String url = this.makeUrl("v1/item") + "&force=1";
            HashMap<String, Object> data = item.toParams();
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.put(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }

    /**
     * Adds multiple items to your autocomplete.
     *
     * @param items the items you want to add.
     * @param autocompleteSection the section of the autocomplete that you're adding the items to.
     * @return true if working
     * @throws ConstructorException if the request is invalid.
     */
    public boolean addItemBatch(ConstructorItem[] items, String autocompleteSection) throws ConstructorException {
        try {
            String url = this.makeUrl("v1/batch_items");
            HashMap<String, Object> data = new HashMap<String, Object>();
            ArrayList<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toParams());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.post(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }

    /**
     * Adds multiple items to your autocomplete whilst updating existing ones.
     *
     * @param items the items you want to add.
     * @param autocompleteSection the section of the autocomplete that you're adding the items to.
     * @return true if working
     * @throws ConstructorException if the request is invalid.
     */
    public boolean addOrUpdateItemBatch(ConstructorItem[] items, String autocompleteSection) throws ConstructorException {
        try {
            String url = this.makeUrl("v1/batch_items") + "&force=1";
            HashMap<String, Object> data = new HashMap<String, Object>();
            ArrayList<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toParams());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.put(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
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
            String url = this.makeUrl("v1/item");
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("item_name", item.getItemName());
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.delete(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }

    /**
     * Removes multiple items from your autocomplete
     *
     * @param items the items that you are removing
     * @param autocompleteSection the section of the autocomplete that you're removing the items from.
     * @return true if successfully removed
     * @throws ConstructorException if the request is invalid
     */
    public boolean removeItemBatch(ConstructorItem[] items, String autocompleteSection) throws ConstructorException {
        try {
            String url = this.makeUrl("v1/batch_items");
            HashMap<String, Object> data = new HashMap<String, Object>();
            ArrayList<Object> itemsAsJSON = new ArrayList<Object>();
            for (ConstructorItem item : items) {
                itemsAsJSON.add(item.toParams());
            }
            data.put("items", itemsAsJSON);
            data.put("autocomplete_section", autocompleteSection);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.delete(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException | UnirestException e) {
            throw new ConstructorException(e);
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
            String url = this.makeUrl("v1/item");
            HashMap<String, Object> data = item.toParams();
            data.put("new_item_name", item.getItemName());
            data.put("autocomplete_section", autocompleteSection);
            data.put("item_name", previousItemName);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.put(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }

    /**	
     * Queries the autocomplete service.	
     *	
     * Note that if you're making an autocomplete service on a website, you should definitely use our javascript client instead of doing it server-side!	
     * That's important. That will be a solid latency difference.	
     *	
     * @param query The string that you will be autocompleting.	
     * @return An autocomplete result	
     */	
    public AutocompleteResponse autocomplete(String query) throws ConstructorException {	
        try {	
            String url = this.makeUrl("autocomplete/" + query);
            HttpResponse<JsonNode> jsonRes = Unirest.get(url)
                .asJson();	
            if (checkResponse(jsonRes, 200)) {
                JSONObject bodyJSON = jsonRes.getBody().getObject();
                JSONObject sectionsJSON = bodyJSON.getJSONObject("sections");
                HashMap<String, ArrayList<AutocompleteSuggestion>> sections = new HashMap<String, ArrayList<AutocompleteSuggestion>>();

                for(Object key : sectionsJSON.keySet()) {
                    ArrayList<AutocompleteSuggestion> items = new ArrayList<AutocompleteSuggestion>();
                    String sectionName = (String)key;
                    JSONArray resultsJSON = sectionsJSON.getJSONArray(sectionName);
                    for (int i = 0; i < resultsJSON.length(); i++) {
                        JSONObject suggestionJSON = resultsJSON.getJSONObject(i);
                        items.add(new AutocompleteSuggestion(suggestionJSON.getString("value"), new HashMap<String, Object>(), new ArrayList<String>()));
                    }
                    sections.put(sectionName, items);
                }

                String resultId = bodyJSON.getString("result_id");
                return new AutocompleteResponse(resultId, sections);
            }	
            return null; // this should not get back here	
        } catch (UnsupportedEncodingException encException) {	
            throw new ConstructorException(encException);	
        } catch (UnirestException uniException) {	
            throw new ConstructorException(uniException);	
        }	
    }

    /**
     * Tracks the fact that someone converted on your site.
     *
     * Can be for any definition of conversion, whether someone buys a product or signs up or does something important to your site.
     *
     * @param term the term that someone converted from.
     * @param autocompleteSection the item autocomplete section
     * @param itemID the item id
     * @param revenue the item revenue
     * @return true if successfully tracked.
     * @throws ConstructorException if the request is invalid.
     */
    public boolean trackConversion(String term, String autocompleteSection, String itemId, String revenue) throws ConstructorException {
        try {
            String url = this.makeUrl("v1/conversion");
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("term", term);
            data.put("autocomplete_section", autocompleteSection);
            data.put("item_id", itemId);
            data.put("revenue", revenue);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.post(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }

    /**
     * Tracks the fact that someone clicked through a search result on the site.
     *
     * @param term the term that someone clicked.
     * @param autocompleteSection the item autocomplete section
     * @param itemID the item id
     * @return true if successfully tracked.
     * @throws ConstructorException if the request is invalid.
     */
    public boolean trackClickThrough(String term, String autocompleteSection, String itemId) throws ConstructorException {
        try {
            String url = this.makeUrl("v1/click_through");
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("term", term);
            data.put("autocomplete_section", autocompleteSection);
            data.put("item_id", itemId);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.post(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }

    /**
     * Tracks the fact that someone searched on your site.
     *
     * There's no autocompleteSection parameter because if you're searching, you aren't using an autocomplete.
     *
     * @param term the term that someone searched.
     * @param numResults the number of results in the search
     * @return true if successfully tracked.
     * @throws ConstructorException if the request is invalid.
     */
    public boolean trackSearch(String term, Integer numResults) throws ConstructorException {
        try {
            String url = this.makeUrl("v1/search");
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("term", term);
            data.put("num_results", numResults);
            String params = new Gson().toJson(data);
            HttpResponse<JsonNode> jsonRes = Unirest.post(url)
                    .basicAuth(this.apiToken, "")
                    .body(params)
                    .asJson();
            return checkResponse(jsonRes, 204);
        } catch (UnsupportedEncodingException encException) {
            throw new ConstructorException(encException);
        } catch (UnirestException uniException) {
            throw new ConstructorException(uniException);
        }
    }
}