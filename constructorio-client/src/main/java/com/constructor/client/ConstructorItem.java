import java.util.HashMap;
import com.google.gson.Gson;

public class ConstructorItem extends HashMap<String, Object> {
	
	public ConstructorItem(String itemName, String autocompleteSection, HashMap<String, Object> jsonParams) {
		super();
		this.put("item_name", itemName);
		this.put("autocomplete_section", autocompleteSection);
		this.putAll(jsonParams);
	}
	
	public ConstructorItem(String itemName, String autocompleteSection) {
		super();
		this.put("item_name", itemName);
		this.put("autocomplete_section", autocompleteSection);
	}
	
	public ConstructorItem(String itemName) {
		super();
		this.put("item_name", itemName);
	}
	
	
	
	public ConstructorItem put(String name, Object value) {
		if (value == null) { // Don't allow null values
			super.remove(name);
		} else {
			super.put(name, value);
		}
		return this; // Make it chainable
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public String toJsonParams() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.putAll(this);
		params.remove("item_name");
		params.remove("autocomplete_section");
		return params; 
	}
	
	
	
	public ConstructorItem setItemName(String v) { this.put("item_name", v); return this; }
	public          String getItemName()  { return this.get("item_name"); }
	
	public ConstructorItem setAutocompleteSection(String v) { this.put("autocomplete_section", v); return this; }
	public          String getAutocompleteSection()  { return this.get("autocomplete_section"); }
	
	public ConstructorItem setSuggestedScore(byte v) {
		if (v != null && (v < 1 || v > 100)) {
			throw new IndexOutOfBoundsException("suggested_score must be a number between 1-100.");
		}
		this.put("suggested_score", v); return this;
	}
	public            byte getSuggestedScore()  { return this.get("suggested_score"); }
	
	public ConstructorItem setKeywords(String... v) { this.put("keywords", v); return this; }
	public        String[] getKeywords()  { return this.get("keywords"); }
	
	public ConstructorItem setUrl(String v) { this.put("url", v); return this; }
	public          String getUrl()  { return this.get("url"); }
	
	public ConstructorItem setImageUrl(String v) { this.put("image_url", v); return this; }
	public          String getImageUrl()  { return this.get("image_url"); }
	
	public ConstructorItem setDescription(String v) { this.put("description", v); return this; }
	public          String getDescription()  { return this.get("description"); }
	
	public ConstructorItem setId(String v) { this.put("id", v); return this; }
	public          String getId()  { return this.get("id"); }
	
}