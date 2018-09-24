package io.constructor.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Constructor.io Search Facet
 */
public class SearchFacet {

  private String name;
  private ArrayList<SearchFacetOption> options;

  /**
   * Creates a search facet
   */
  SearchFacet(JSONObject json) throws IllegalArgumentException {
    if (json == null) {
        throw new IllegalArgumentException("json is required");
    }

    this.name = json.getString("name");
    this.options = new ArrayList<SearchFacetOption>();

    JSONArray optionsJSON = json.getJSONArray("options");
    for (int i = 0; i < optionsJSON.length(); i++) {
        JSONObject optionJSON = optionsJSON.getJSONObject(i);
        SearchFacetOption option = new SearchFacetOption(optionJSON);
        this.options.add(option);
    }
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the options
   */
  public ArrayList<SearchFacetOption> getOptions() {
    return options;
  }
}