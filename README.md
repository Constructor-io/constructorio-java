[![Release](https://jitpack.io/v/Constructor-io/constructorio-java.svg)](https://jitpack.io/#Constructor-io/constructorio-java) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/Constructor-io/constructorio-java/blob/master/LICENSE)

# Constructor-IO Java Client
[Constructor.io](http://constructor.io/) provides search as a service that optimizes results using artificial intelligence (including natural language processing, re-ranking to optimize for conversions, and user personalization).

# Documentation
For the most up-to-date documentation for this library, please visit our [API Documentation](https://docs.constructor.io/rest-api.html?java).

# Installation
1. Follow the directions at [Jitpack.io](https://jitpack.io/#Constructor-io/constructorio-java/v4.5.0) to add the client to your project.
2. Retrieve your autocomplete token and key.  You can find this at your [Constructor.io dashboard](https://constructor.io/dashboard).
3. Create a new instance of the client.
```java
ConstructorIO constructor = new ConstructorIO("apitoken", "apikey", true, null);
```

# Creating a Constructor Item
```java
// Create a ConstructorItem
ConstructorItem item = new ConstructorItem("ROTCURTSNOC Rainy Day Coat");

// Set the different fields for the product item
item.setUrl("https://constructor.io/pdp/893092");
item.setImageUrl("https://constructor.io/images/893092.jpg");
item.setId("893092");
item.setSuggestedScore(Integer.valueOf(5000));
item.setDescription("Keep yourself dry and cozy on rainy days with this stylish rain coat that can complement any outfit.");
item.setKeywords(new ArrayList<String>(Arrays.asList("coat", "rain", "jacket")));
item.setGroupIds(new ArrayList<String>(Arrays.asList("Men\'s Apparel", "Coats & Jackets")));

Map<String, Object> facets = new HashMap<String, Object>();
facets.put("Brand", "ROTCURTSNOC");
facets.put("Colors", new ArrayList<String>(Arrays.asList("red", "black", "purple")));
item.setFacets(facets);

Map<String, String> metadata = new HashMap<String, String>();
metadata.put("price", "$50.00");
item.setMetadata(metadata);
```

# Uploading Items

## Adding or Updating an Individual Item
```java
// Add an item to the Products section
constructor.addItem(item, "Products");

// Add or Update an item in the Products section
constructor.addOrUpdateItem(item, "Products");
```

## Adding or Updating a Batch of Items
```java
// Create an ConstructorItems array
ConstructorItem[] items = new ConstructorItem[20];

// Add products to the array
items[0] = new ConstructorItem("YBGID Plaid Shirt");
items[0].setUrl("https://constructor.io/pdp/2063733");
...
items[19] = new ConstructorItem('...');

// Add a batch of items to the Products section
constructor.addItemBatch(items, "Products");

// Add or Update a batch of items in the Products section
constructor.addOrUpdateItemBatch(items, "Products");
```

# Retrieving Results
An example of a search for `rain coat`.
## Autocomplete Results
### Creating an AutocompleteRequest and requesting autocomplete results
```java
// Create an AutocompleteRequest with the query string to request results for
AutocompleteRequest request = new AutocompleteRequest("rain coat");

// Create a resultsPerSection map to set number of results to show per section
Map<String, Integer> resultsPerSection = new HashMap<String, Integer>();
resultsPerSection.put("Products", Integer.valueOf(6));
resultsPerSection.put("Search Suggestions", Integer.valueOf(8));
request.setResultsPerSection(resultsPerSection);

// Create UserInfo with the session id and client id to send along with the request
UserInfo userInfo = new UserInfo(5, "client_id");

// Request autocomplete results (userInfo can be null)
AutocompleteResponse response = constructor.autocomplete(request, userInfo);
```

### Parsing the AutocompleteResponse
```java
// Grabbing data from the autocomplete results
ArrayList<Result> productResults = response.getSections().get("Products");
ArrayList<Result> searchSuggestionResults = response.getSections().get("Search Suggestions");

// Looking into the first Product suggestion
Result result = productResults.get(0);
String itemName = result.getValue(); // "ROTRUCTSNOC Rainy Day Coat"

// Top level data
ResultData resultData = result.getData();
String description = resultData.getDescription(); // "Keep yourself dry and cozy on rainy days with this stylish rain coat that can complement any outfit."
String id = resultData.getId(); // "893092"
String url = resultData.getUrl(); // "https://constructor.io/pdp/893092"
String imageUrl = resultData.getImageUrl(); // "https://constructor.io/images/893092.jpg"

// Groups
ArrayList<ResultGroup> groups = resultData.getGroups();
ResultGroup group = groups.get(0);
String groupName = group.getDisplayName(); // "Men\'s Apparel"
String groupId = group.getGroupId(); // "1050000"

// Facets
ArrayList<ResultFacet> facets = resultData.getFacets();
ResultFacet facet = facets.get(0);
String facetName = facet.getName(); // "Brand"
ArrayList<String> facetValues = facet.getValues(); // ["ROTCURTSNOC"]

// Metadata
HashMap<String, Object> metadata = resultData.getMetadata();
String price = metadata.get("price"); // "$50.00"
```

## Search Results
An example of a search for `peanut butter`.

### Creating a SearchRequest and requesting for search results
```java
// Create a SearchRequest with the query string
SearchRequest request = new SearchRequest("peanut butter");

// Add in additional query paramters
request.setResultsPerPage(5); // Request 5 results per page
request.setPage(1); // Request the first page of results
request.setGroupId("625"); // Filter results that contain the group id "625"
request.getFacets().put("Brand", Arrays.asList("Jif")) // Filter results that contain "Jif" as the brand facet

// Create UserInfo with the session id and client id to send along with the request
UserInfo userInfo = new UserInfo(5, "client_id");

// Request Search Results (userInfo can be null)
SearchResponse response = constructor.search(request, userInfo);
SearchResponseInner responseInner = response.getResponse();
```

### Parsing the SearchResponse
```java
// Grabbing data out of the response
ArrayList<SearchGroup> groups = responseInner.getGroups();
ArrayList<SearchFacet> facets = responseInner.getFacets();
ArrayList<Result> results = responseInner.getResults();
int totalNumResults = responseInner.getTotalNumberOfResults();
```

Groups
```java
// Looking into the first group in the group list
SearchGroup group = groups.get(0);
String groupName = group.getDisplayName(); // "Pantry"
String groupId = group.getGroupId(); // "625"
int itemsInGroup = group.getCount(); // 73
ArrayList<SearchGroup> parents = group.getParents();
ArrayList<SearchGroup> children = group.getChildren();
```

Facets
```java
// Looking into the first facet in the facet list
SearchFacet facet = facets.get(0);
String facetName = facet.getName(); // "Brand"

// Looking into the first facet option of "Brand" facet section
ArrayList<SearchFacetOption> facetOptions = facet.getOptions();
SearchFacetOption facetOption = facetOptions.get(0);
int facetCount = facetOption.getCount(); // 5
String facetName = facetOption.getValue(); // "Skippy"
```

Search Result
```java
// Looking into the first search result
ResultData result = results.get(0);
String itemName = result.getValue(); // "Jif Creamy Peanut Butter To Go Cups, Case"

// Top level data
ResultData resultData = result.getData();
String description = resultData.getDescription(); // "The same great Jif taste in a convenient snack size that's perfect for dipping!"
String id = resultData.getId(); // "gro_pid_4220124"
String url = resultData.getUrl(); // "https://constructor.io/pdp/gro_pid_4220124"
String imageUrl = resultData.getImageUrl(); // "https://d17bbgoo3npfov.cloudfront.net/images/gro_pid_4220124.png"

// Groups
ArrayList<ResultGroup> groups = resultData.getGroups();
ResultGroup group = groups.get(0);
String groupName = group.getDisplayName(); // "Grocery"
String groupId = group.getGroupId(); // "431"

// Facets
ArrayList<ResultFacet> facets = resultData.getFacets();
ResultFacet facet = facets.get(0);
String facetName = facet.getName(); // "Brand"
ArrayList<String> facetValues = facet.getValues(); // ["Jif"]

// Metadata
HashMap<String, Object> metadata = resultData.getMetadata();
String price = metadata.get("price"); // "$15.99"
String quantity = metadata.get("quantity"); // "1 box (12 count)"

```
