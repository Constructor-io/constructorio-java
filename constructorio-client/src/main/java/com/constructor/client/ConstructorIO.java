package com.constructor.client;
import com.mashape.unirest;
import java.net.URLEncoder;

/**
 * Constructor.io Client
 * Go to Constructor.io for our awesome services to go help your site go do wibbley-wobbley awesome things!
 */

// these all should throw that exception
public class ConstructorIOClient 
{

	public String apiToken;
	public String autocompleteKey;
	public String protocol;
	public String host;

	public ConstructorIOClient (String apiToken, String autocompleteKey, boolean isHTTPS, String host) {
		this.apiToken = apiToken;
		this.autocompleteKey = autocompleteKey;
		this.protocol = protocol;
		if (isHTTPS) {
			this.host = "https";
		} else {
			this.host = "http";
		}
	}

	public ConstructorIOClient (String apiToken, String autocompleteKey, boolean isHTTPS) {
		ConstructorIOClient(apiToken, autocompleteKey, isHTTPS, "ac.cnstrc.com");
	}
	
	public ConstructorIOClient (String apiToken, String autocompleteKey, String host) {
		ConstructorIOClient(apiToken, autocompleteKey, true, host);
	}
	
	public ConstructorIOClient (String apiToken, String autocompleteKey) {
		ConstructorIOClient(apiToken, autocompleteKey, true, "ac.cnstrc.com");
	}

	public static String serializeParams(HashMap<String, String> params) {
		String urlString = "";
		for (HashMap.Entry<String, String> entry : params.entrySet()) {
			urlString += "&";
			urlString += URLEncoder.encode(entry.getKey(), "UTF-8"); // some shit here
			urlString += "=";
			urlString += URLEncoder.encode(entry.getValue(), "UTF-8"); // some shit here
		}
		return urlString.substring(1); // get rid of initial &
	}

	public static String makeUrl(String endpoint) {
		makeUrl(endpoint, new HashMap<String, String>());
	}

	public static String makeUrl(String endpoint, HashMap<String, String> params) {
		params.set("autocomplete_key", this.autocompleteKey);
		return String.format("%s://%s/%s?%s", this.protocol, this.host, endpoint, this.serializeParams(params));
	}

	public void query(String queryStr) throws ConstructorException {
		String url = this.makeUrl("autocomplete/" + queryStr);
		//HTTPResponse<JsonNode> jsonRes = Unirest.get(url).asJson();

        //resp = requests.get(url)
        //if resp.status_code != 200:
        //    raise ConstructorError(resp.text)
        //else:
        //    return resp.json()
		////////////////
	}

	public boolean verify() throws ConstructorException {
		return false;
        //url = self._make_url("v1/verify")
        //resp = requests.get(
        //    url,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 200:
        //    raise ConstructorError(resp.text)
        //else:
        //    return resp.json()
	}

	public boolean add() throws ConstructorException {
		return false;
		////////////////
        //params = {"item_name": item_name, "autocomplete_section": autocomplete_section}
        //if "suggested_score" in kwargs:
        //    params["suggested_score"] = kwargs["suggested_score"]
        //if "keywords" in kwargs:
        //    params["keywords"] = kwargs["keywords"]
        //if "description" in kwargs:
        //    params["description"] = kwargs["description"]
        //if "url" in kwargs:
        //    params["url"] = kwargs["url"]
        //if "image_url" in kwargs:
        //    params["image_url"] = kwargs["image_url"]
        //url = self._make_url("v1/item")
        //if not self._api_token:
        //    raise IOError("You must have an API token to use the Add method!")
        //resp = requests.post(
        //    url,
        //    json=params,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 204:
        //    raise ConstructorError(resp.text)
        //else:
        //    return True
	}

	public boolean remove() throws ConstructorException {
		return false;
        //params = {"item_name": item_name, "autocomplete_section": autocomplete_section}
        //if "suggested_score" in kwargs:
        //    params["suggested_score"] = kwargs["suggested_score"]
        //if "keywords" in kwargs:
        //    params["keywords"] = kwargs["keywords"]
        //if "url" in kwargs:
        //    params["url"] = kwargs["url"]
        //url = self._make_url("v1/item")
        //if not self._api_token:
        //    raise IOError("You must have an API token to use the Remove method!")
        //resp = requests.delete(
        //    url,
        //    json=params,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 204:
        //    raise ConstructorError(resp.text)
        //else:
        //    return True
	}
	
	public boolean modify() throws ConstructorException {
		return false;
        //params = {"item_name": item_name, "autocomplete_section": autocomplete_section}
        //if "suggested_score" in kwargs:
        //    params["suggested_score"] = kwargs["suggested_score"]
        //if "keywords" in kwargs:
        //    params["keywords"] = kwargs["keywords"]
        //if "url" in kwargs:
        //    params["url"] = kwargs["url"]
        //if "new_item_name" in kwargs:
        //    params["new_item_name"] = kwargs["new_item_name"]
        //url = self._make_url("v1/item")
        //if not self._api_token:
        //    raise IOError("You must have an API token to use the Modify method!")
        //resp = requests.put(
        //    url,
        //    json=params,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 204:
        //    raise ConstructorError(resp.text)
        //else:
        //    return True
	}
	
	public boolean trackConversion() throws ConstructorException {
		return false;
        //params = {
        //    "term": term,
        //    "autocomplete_section": autocomplete_section,
        //}
        //if "item" in kwargs:
        //    params["item"] = kwargs["item"]
        //url = self._make_url("v1/conversion")
        //if not self._api_token:
        //    raise IOError("You must have an API token to track conversions!")
        //resp = requests.post(
        //    url,
        //    json=params,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 204:
        //    raise ConstructorError(resp.text)
        //else:
        //    return True
	}
	
	public boolean trackClickThrough() throws ConstructorException {
		return false;
        //params = {
        //    "term": term,
        //    "autocomplete_section": autocomplete_section,
        //}
        //if "item" in kwargs:
        //    params["item"] = kwargs["item"]
        //if "revenue" in kwargs:
        //    params["revenue"] = kwargs["revenue"]
        //url = self._make_url("v1/click_through")
        //if not self._api_token:
        //    raise IOError("You must have an API token to track click throughs!")
        //resp = requests.post(
        //    url,
        //    json=params,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 204:
        //    raise ConstructorError(resp.text)
        //else:
        //    return True
	}
	
	public boolean trackSearch() throws ConstructorException {
		return false;
        //params = {
        //    "term": term
        //}
        //if "num_results" in kwargs:
        //    params["num_results"] = kwargs["num_results"]
        //url = self._make_url("v1/search")
        //if not self._api_token:
        //    raise IOError("You must have an API token to track searches!")
        //resp = requests.post(
        //    url,
        //    json=params,
        //    auth=(self._api_token, "")
        //)
        //if resp.status_code != 204:
        //    raise ConstructorError(resp.text)
        //else:
        //    return True
	}

}
