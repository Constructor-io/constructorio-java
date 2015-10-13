package com.constructor.client;
import com.mashape.unirest;

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

	public ConstructorIOClient (String apiToken, String autocompleteKey, String protocol, String host) {
		this.apiToken = apiToken;
		this.autocompleteKey = autocompleteKey;
		this.protocol = protocol;
		this.host = host;
	}

	//apitoken, ackey, protocol
	//apitoken, ackey, host
	//apitoken, ackey

	//ackey, protocol, host
	//ackey, protocol
	//ackey, host
	//ackey

	public static String serializeParams(something params) {
		return URLEncoder.encode(params, "UTF-8")
	}

	public String makeUrl(endpoint, param) {
        //if not params:
        //    params = {}
        //params["autocomplete_key"] = self._autocomplete_key
        //return "{0}://{1}/{2}?{3}".format(self._protocol, self._host, endpoint, self._serialize_params(params))
	}

	public boolean query(queryStr) throws ConstructorException {
        //url = self._make_url("autocomplete/" + query_str)
        //resp = requests.get(url)
        //if resp.status_code != 200:
        //    raise ConstructorError(resp.text)
        //else:
        //    return resp.json()
		////////////////
	}

	public boolean verify() throws ConstructorException {
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
