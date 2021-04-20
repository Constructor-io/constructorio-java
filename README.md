[![Release](https://jitpack.io/v/Constructor-io/constructorio-java.svg)](https://jitpack.io/#Constructor-io/constructorio-java) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/Constructor-io/constructorio-java/blob/master/LICENSE)

# Constructor-IO Java Client

[Constructor.io](http://constructor.io/) provides search as a service that optimizes results using artificial intelligence (including natural language processing, re-ranking to optimize for conversions, and user personalization).

# Documentation

For the most up-to-date documentation for this library, please visit our [API Documentation](https://docs.constructor.io/rest-api.html?java).

# Installation

1. Follow the directions at [Jitpack.io](https://jitpack.io/#Constructor-io/constructorio-java/v5.3.3) to add the client to your project.
2. Retrieve your autocomplete token and key.  You can find this at your [Constructor.io dashboard](https://constructor.io/dashboard).
3. Create a new instance of the client.

```java
ConstructorIO constructor = new ConstructorIO("apitoken", "apikey", true, null);
```

# Creating and Modifying Items

A `ConstructorItem` contains all the information about a product or search suggestion. To add or update an individual item, you will need to provide a `ConstructorItem` and the relevant `Autocomplete Section` it belongs to.

```java
// Create an item
ConstructorItem item = new ConstructorItem("ROTCURTSNOC Rainy Day Coat");
item.setUrl("https://constructor.io/pdp/893092");
item.setImageUrl("https://constructor.io/images/893092.jpg");
item.setId("893092");
item.setSuggestedScore(Integer.valueOf(5000));
item.setDescription("Keep yourself dry and cozy on rainy days.");
item.setKeywords(Arrays.asList("coat", "rain", "jacket"));

// Add an item to the Products section
constructor.addItem(item, "Products");

// Add or update an item in the Products section
constructor.addOrUpdateItem(item, "Products");
```

Similarly with adding or updating a batch of items, you will need to provide an array of `ConstructorItem`'s and their relevant `Autocomplete Section`.

```java
// Create an array of items
ConstructorItem[] items = new ConstructorItem[20];
items[0] = new ConstructorItem("YBGID Plaid Shirt");
items[1] = new ConstructorItem("YBGID Striped Shirt");
...
items[19] = new ConstructorItem("YBGID Polka Dot Shirt");

// Add items to the Products section
constructor.addItemBatch(items, "Products");

// Add or update items in the Products section
constructor.addOrUpdateItemBatch(items, "Products");
```

# Replacing and Updating Catalogs
To replace or update your product catalog, you will need to create a `CatalogRequest`. In this request, you can specify the files you want to upload (items, variations, and item groups) and the section you want to upload to. You can also set a notification e-mail to be alerted when a file ingestion fails.

```java
// Create a files map and add the relevant files
Map<String, File> files = new HashMap<String, File>();
files.put("items", new File("src/test/resources/csv/items.csv"));
files.put("variations", new File("src/test/resources/csv/variations.csv"));
files.put("item_groups", new File("src/test/resources/csv/item_groups.csv"));

// Create a CatalogRequest with files to upload and the section to upload to
CatalogRequest request = new CatalogRequest(files, "Products");

// Set a notification e-mail
request.setNotificationEmail("integration@company.com");

// Set the force flag if the catalog should be processed even if it will invalidate a large number of existing items
request.setForce(true)

// Send a request to replace the catalog (sync)
constructor.replaceCatalog(request);

// Or send a request to update the catalog (delta)
constructor.updateCatalog(request);
```


# Retrieving Autocomplete Results

To retrieve autocomplete results, you will need to create an `AutocompleteRequest`. In this request you can specify the number of results you want per autocomplete section.  If the results are for a specific user, you can also create a `UserInfo` object, which will allow you to retrieve personalized results.

```java
// Create an AutocompleteRequest with the term to request results for
AutocompleteRequest request = new AutocompleteRequest("rain coat");

// Define the number of results to show per section
Map<String, Integer> resultsPerSection = new HashMap<String, Integer>();
resultsPerSection.put("Products", Integer.valueOf(6));
resultsPerSection.put("Search Suggestions", Integer.valueOf(8));
request.setResultsPerSection(resultsPerSection);

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Request results as an object
AutocompleteResponse response = constructor.autocomplete(request, userInfo);

// Request results as a JSON string
String response = constructor.autocompleteAsJSON(request, userInfo);
```

# Retrieving Search Results

To retrieve search results, you will need to create a `SearchRequest`. In this request you can specify the number of results you want per page, the page you want, sorting instructions, and also filter the results by category or facets. If the results are for a specific user, you can also create a `UserInfo` object, which will allow you to retrieve personalized results.

```java
// Create a SearchRequest with the term to request results for
SearchRequest request = new SearchRequest("peanut butter");

// Add in additional parameters
request.setResultsPerPage(5);
request.setPage(1);
request.setGroupId("625");r
request.setSortBy("Price");
request.setSortAscending(true);
request.getFacets().put("Brand", Arrays.asList("Jif"))

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Request results as an object
SearchResponse response = constructor.search(request, userInfo);

// Request results as a JSON string
String response = constructor.searchAsJSON(request, userInfo);
```

If you'd like to retrieve search results asynchronously, the above code can be modified slightly to utilize a callback methodology:

```java
constructor.search(request, userInfo, new SearchCallback() {
  @Override
  public void onFailure(final ConstructorException exception) {
    // failure condition
  }

  @Override
  public void onResponse(final SearchResponse response) {
    // success condition - data located within `response`
  };
});
```

# Retrieving Search Results for Speech

To retrieve search results for text that originated from speech transcription rather than typing, you will need to create a `NaturalLanguageSearchRequest`. In this request you can specify the number of results you want per page and the page you want.  All other information is inferred from the text itself.  If the results are for a specific user, you can also create a `UserInfo` object, which will allow you to retrieve personalized results.  The response returned contains all of the same data points as a standard search response.

```java
// Create a SearchRequest with the term to request results for
NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest("peanut butter");

// Add in additional parameters
request.setResultsPerPage(5);
request.setPage(1);

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Request results as an object
SearchResponse response = constructor.naturalLanguageSearch(request, userInfo);

// Request results as a JSON string
String response = constructor.naturalLanguageSearchAsJSON(request, userInfo);
```

# Retrieving Browse Results

To retrieve browse results, you will need to create a `BrowseRequest`. When creating the `BrowseRequest` the filter name can be one of `collection_id`, `group_id`, or a facet name (i.e. Brand). In this request, you can also specify the number of results you want per page, the page you want, sorting instructions, and also filter the results by category or facets. If the results are for a specific user, you can also create a `UserInfo` object, which will allow you to retrieve personalized results.

```java
// Create a BrowseRequest with the filter name and filter value to request results for
BrowseRequest request = new BrowseRequest("group_id", "8193");

// Add in additional parameters
request.setResultsPerPage(5);
request.setPage(1);
request.setGroupId("625");r
request.setSortBy("Price");
request.setSortAscending(true);
request.getFacets().put("Brand", Arrays.asList("Jif"))

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Request results as an object
BrowseResponse response = constructor.browse(request, userInfo);

// Request results as a JSON string
String response = constructor.browseAsJSON(request, userInfo);
```

If you'd like to retrieve browse results asynchronously, the above code can be modified slightly to utilize a callback methodology:

```java
constructor.browse(request, userInfo, new BrowseCallback() {
  @Override
  public void onFailure(final ConstructorException exception) {
    // failure condition
  }

  @Override
  public void onResponse(final BrowseResponse response) {
    // success condition - data located within `response`
  };
});
```

# Retrieving Recommendation Results

To retrieve recommendation results, you will need to create a `RecommendationsRequest`. In this request, you can also specify the number of results you want and the items (given the ids) that you want to retrieve recommendations for. If the results are for a specific user, you can also create a `UserInfo` object, which will allow you to retrieve personalized results.

```java
// Create a RecommendationsRequest with the pod id to request results for
RecommendationsRequest request = new RecommendationsRequest("pdp_complementary_items");

// Add in additional parameters
request.setNumResults(5);
request.setItemIds(Arrays.asList("9838172"))

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Request results as an object
RecommendationsResponse response = constructor.recommendations(request, userInfo);

// Request results as a JSON string
String response = constructor.recommendationsAsJSON(request, userInfo);
```

# Testing

Download the repository and run the following commands from `./constructorio-client`

```bash
mvn clean               # clean target directory
mvn install             # installs dependencies
mvn test                # run tests
mvn jacoco:report       # writes code coverage reports to ./target/site/jacoco
```
