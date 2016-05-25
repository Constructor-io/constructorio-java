package com.constructor.client;
import java.util.HashMap;
import com.google.gson.Gson;

public class ConstructorItem extends HashMap<String, Object> {
	
	/**
	 * Creates a constructor.io autocomplete item, which is basically a HashMap with additional abstraction.
	 * @param itemName the item that you're adding.
	 * @param jsonParams a map of optional parameters. Optional parameters are in the <a href="https://constructor.io/docs/#add-an-item">API documentation</a>
	 */
	public ConstructorItem(String itemName, HashMap<String, Object> jsonParams) {
		super();
		this.put("item_name", itemName);
		this.putAll(jsonParams);
	}
	
	/**
	 * Creates a constructor.io autocomplete item, which is basically a HashMap with additional abstraction.
	 * @param itemName the item that you're adding.
	 */
	public ConstructorItem(String itemName) {
		super();
		this.put("item_name", itemName);
	}
	
	/**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced. If the value is null, the field will be removed.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the ConstructorItem itself for chained syntax.
     */
	public ConstructorItem put(String name, Object value) throws IllegalArgumentException {
		// Check type for known parameters
		if ((name == "item_name") && value == null) {
			throw new IllegalArgumentException("The field " + name + " cannot be null");
		}
		if ((name == "item_name" || name == "url" || name == "image_url" || name == "description" || name == "id") && !(value instanceof String) && value != null) {
			throw new IllegalArgumentException("The field " + name + " has to be a String");
		}
		if (name == "suggested_score") {
			if (!(value instanceof Number) && value != null) {
				throw new IllegalArgumentException("The field " + name + " has to be a Number");
			}
			if (value == null) value = 0;
			// Let setSuggestedScore handle the rest...
			return setSuggestedScore(((Number) value).intValue());
		}
		if ((name == "keywords") && !(value instanceof String[]) && value != null) {
			throw new IllegalArgumentException("The field " + name + " has to be a String[]");
		}
		
		if (value == null) { // Don't allow null values
			super.remove(name);
		} else {
			super.put(name, value);
		}
		return this; // Make it chainable
	}
	
	/**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param  key key whose mapping is to be removed from the map
     * @return the ConstructorItem itself for chained syntax.
     */
	public ConstructorItem remove(String name) throws IllegalArgumentException {
		if (name == "item_name") {
			throw new IllegalArgumentException("The field " + name + " cannot be null");
		}
		super.remove(name);
		return this; // Make it chainable
	}
	
	String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	HashMap<String, Object> toJsonParams() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.putAll(this);
		params.remove("item_name");
		return params; 
	}
	
	
	
	public ConstructorItem setItemName(String v) { this.put("item_name", v); return this; }
	public          String getItemName()  { return (String) this.get("item_name"); }
	
	public ConstructorItem setSuggestedScore(int v) {
		if (v > 0) super.put("suggested_score", v);
		else super.remove("suggested_score");
		return this;
	}
	public             int getSuggestedScore()  { return this.containsKey("suggested_score") ? ((Number) this.get("suggested_score")).intValue() : 0; }
	
	public ConstructorItem setKeywords(String... v) { this.put("keywords", v); return this; }
	public        String[] getKeywords()  { return (String[]) this.get("keywords"); }
	
	public ConstructorItem setUrl(String v) { this.put("url", v); return this; }
	public          String getUrl()  { return (String) this.get("url"); }
	
	public ConstructorItem setImageUrl(String v) { this.put("image_url", v); return this; }
	public          String getImageUrl()  { return (String) this.get("image_url"); }
	
	public ConstructorItem setDescription(String v) { this.put("description", v); return this; }
	public          String getDescription()  { return (String) this.get("description"); }
	
	public ConstructorItem setId(String v) { this.put("id", v); return this; }
	public          String getId()  { return (String) this.get("id"); }
	
}