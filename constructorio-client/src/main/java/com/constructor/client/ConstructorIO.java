package com.constructor.client;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.http.client.utils.URLEncodedUtils;

/**
 * Constructor.io Client
 * Go to Constructor.io for our awesome services to go help your site go do wibbley-wobbley awesome things!
 */

// these all should throw that exception
public class ConstructorIO
{

	public String apiToken;
	public String autocompleteKey;
	public String protocol;
	public String host;
	protected URLEncodedUtils encoder;

	public ConstructorIO (String apiToken, String autocompleteKey, boolean isHTTPS, String host) {
		this.apiToken = apiToken;
		this.autocompleteKey = autocompleteKey;
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
	 * serializeParams
	 *
	 * unlike in the other clients, this one only takes in hashmaps
	 * and you must write other helper methods to serialize other things
	 */
	public static String serializeParams(HashMap<String, String> params) throws UnsupportedEncodingException {
		String urlString = "";
		boolean isFirst = true;
		for (String key: params.keySet()) {
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

	public String makeUrl(String endpoint) throws UnsupportedEncodingException {
		return makeUrl(endpoint, new HashMap<String, String>());
	}

	private static String createItemParams(String itemName, String autocompleteSection) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("item_name", itemName);
		params.put("autocomplete_section", autocompleteSection);
		Gson gson = new Gson();
		return gson.toJson(params);
	}
	
	private static String createItemParams(String itemName, String autocompleteSection, Map<String, Object> otherJsonParams) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("item_name", itemName);
		params.put("autocomplete_section", autocompleteSection);
		params.putAll(otherJsonParams);
		Gson gson = new Gson();
		return gson.toJson(params);
	}

	private static String createTrackingParams(String term) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("term", term);
		Gson gson = new Gson();
		return gson.toJson(params);
	}

	private static String createTrackingParams(String term, String autocompleteSection) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("term", term);
		params.put("autocomplete_section", autocompleteSection);
		Gson gson = new Gson();
		return gson.toJson(params);
	}
	
	private static String createTrackingParams(String term, String autocompleteSection, Map<String, Object> otherJsonParams) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("term", term);
		params.put("autocomplete_section", autocompleteSection);
		params.putAll(otherJsonParams);
		Gson gson = new Gson();
		return gson.toJson(params);
	}

	private static String createTrackingParams(String term, Map<String, Object> otherJsonParams) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("term", term);
		params.putAll(otherJsonParams);
		Gson gson = new Gson();
		return gson.toJson(params);
	}

	private static boolean checkResponse(HttpResponse<JsonNode> resp, int expectedStatus) throws ConstructorException {
		if (resp.getStatus() != expectedStatus) {
			throw new ConstructorException(resp.getBody().toString());
		} else {
			return true;
		}
	}

	public String makeUrl(String endpoint, HashMap<String, String> params) throws UnsupportedEncodingException {
		params.put("autocomplete_key", this.autocompleteKey);
		return String.format("%s://%s/%s?%s", this.protocol, this.host, endpoint, this.serializeParams(params));
	}

	public JsonNode query(String queryStr) throws ConstructorException {
		try {
			String url = this.makeUrl("autocomplete/" + queryStr);
			HttpResponse<JsonNode> jsonRes = Unirest.get(url).asJson();
			if (checkResponse(jsonRes, 200)) {
				return jsonRes.getBody();
			}
			return null; // this should not get back here
		} catch (UnsupportedEncodingException encException) {
			throw new ConstructorException(encException);
		} catch (UnirestException uniException) {
			throw new ConstructorException(uniException);
		}
	}

	public JsonNode verify() throws ConstructorException {
		try {
			String url = this.makeUrl("v1/verify");
			HttpResponse<JsonNode> jsonRes = Unirest.get(url)
																							.basicAuth(this.apiToken, "")
																							.asJson();
			if (checkResponse(jsonRes, 200)) {
				return jsonRes.getBody();
			}
			return null; // this should not get back here
		} catch (UnsupportedEncodingException encException) {
			throw new ConstructorException(encException);
		} catch (UnirestException uniException) {
			throw new ConstructorException(uniException);
		}
	}

	public boolean add (String itemName, String autocompleteSection) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/item");
			String params = ConstructorIO.createItemParams(itemName, autocompleteSection);
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

	public boolean add (String itemName, String autocompleteSection, Map<String, Object> jsonParams) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/item");
			String params = ConstructorIO.createItemParams(itemName, autocompleteSection, jsonParams);
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

	public boolean remove(String itemName, String autocompleteSection) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/item");
			String params = ConstructorIO.createItemParams(itemName, autocompleteSection);
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
	
	public boolean remove(String itemName, String autocompleteSection, Map<String, Object> jsonParams) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/item");
			String params = ConstructorIO.createItemParams(itemName, autocompleteSection, jsonParams);
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
	
	public boolean modify(String itemName, String newItemName, String autocompleteSection) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/item");
			HashMap<String, Object> newItem = new HashMap<String, Object>();
			newItem.put("new_item_name", newItemName);
			String params = ConstructorIO.createItemParams(itemName, autocompleteSection, newItem);
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
	
	public boolean modify(String itemName, String newItemName, String autocompleteSection, Map<String, Object> jsonParams) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/item");
			jsonParams.put("new_item_name", newItemName);
			String params = ConstructorIO.createItemParams(itemName, autocompleteSection, jsonParams);
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
	
	public boolean trackConversion(String term, String autocompleteSection) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/conversion");
			String params = ConstructorIO.createTrackingParams(term, autocompleteSection);
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

	public boolean trackConversion(String term, String autocompleteSection, Map<String, Object> jsonParams) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/conversion");
			String params = ConstructorIO.createTrackingParams(term, autocompleteSection, jsonParams);
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
	
	public boolean trackClickThrough(String term, String autocompleteSection) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/click_through");
			String params = ConstructorIO.createTrackingParams(term, autocompleteSection);
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
	
	public boolean trackClickThrough(String term, String autocompleteSection, Map<String, Object> jsonParams) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/click_through");
			String params = ConstructorIO.createTrackingParams(term, autocompleteSection, jsonParams);
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
	
	public boolean trackSearch(String term) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/search");
			String params = ConstructorIO.createTrackingParams(term);
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
	
	public boolean trackSearch(String term, Map<String, Object> jsonParams) throws ConstructorException {
		try {
			String url = this.makeUrl("v1/search");
			String params = ConstructorIO.createTrackingParams(term, jsonParams);
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
