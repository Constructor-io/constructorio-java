[![Release](https://jitpack.io/v/Constructor-io/constructorio-java.svg)](https://jitpack.io/#Constructor-io/constructorio-java) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/Constructor-io/constructorio-java/blob/master/LICENSE)

# Constructor-IO Java Client

[Constructor.io](http://constructor.io/) provides search as a service that optimizes results using artificial intelligence (including natural language processing, re-ranking to optimize for conversions, and user personalization).

# Documentation

Full API documentation is available on [Github Pages](https://constructor-io.github.io/constructorio-java/)

# Requirements

Requesting results from your JVM based back-end can be useful in order to control result rendering logic on your server, or augment/hydrate results with data from another system. However, a back-end integration has additional requirements compared to a front-end integration. Please review the [Additional Information For Backend Integrations](https://github.com/Constructor-io/constructorio-java/wiki/Additional-Information-for-Backend-Integrations) article within the wiki for more detail.

# Installation

1. Follow the directions at [Jitpack.io](https://jitpack.io/#Constructor-io/constructorio-java/v5.21.2) to add the client to your project.
2. Retrieve your autocomplete token and key.  You can find this at your [Constructor.io dashboard](https://constructor.io/dashboard).
3. Create a new instance of the client.

```java
ConstructorIO constructor = new ConstructorIO("apitoken", "apikey", true, null);
```

# Creating and Modifying Items

A `ConstructorItem` contains all the information about a product or search suggestion. To add or replace a batch of items, you will need to provide an array of `ConstructorItem`'s and their relevant `Autocomplete Section`.

```java
// Create an array of items
ConstructorItem[] items = new ConstructorItem[20];
items[0] = new ConstructorItem("10001");
items[1] = new ConstructorItem("10002");
...
items[19] = new ConstructorItem("10003");

// Add or replace items in the Products section
constructor.createOrReplaceItems(items, "Products"); // (limit of 1,000 items)
```

To update existing item(s), you will need to provide an array of `ConstructorItem`(s) and their relevant `Autocomplete Section`.
You can pass in an "onMissing" enum to tell the system how to handle updating items that don't exist (possible values are "FAIL" | "IGNORE" | "CREATE").

```java
ConstructorItem item = new ConstructorItem("10001");
item.setSuggestedScore((float) 1337.00);
ConstructorItem[] items = { item };

// Update item(s) from the Products section
constructor.updateItems(items, "Products", false, null, CatalogRequest.OnMissing.IGNORE);
```

# Deleting Items

To delete existing item(s), you will need to provide an array of `ConstructorItem`(s) and their relevant `Autocomplete Section`.

```java
// Create an item
ConstructorItem item = new ConstructorItem("10001");
ConstructorItem[] items = { item };

// Delete items from the Products section
constructor.deleteItems(items, "Products"); // (limit of 1,000 items)
```

# Retrieving Items

Retrieving your items.

```java
ItemsRequest request = new ItemsRequest();
ItemsResponse response = constructor.retrieveItems(request);
```

You can also specify certain ids to retrieve.

```java
ItemsRequest request = new ItemsRequest();
request.setIds(Arrays.asList("10001", "10002"));
ItemsResponse response = constructor.retrieveItems(request);
```

Retrieving your items as a JSON string.

```java
ItemsRequest request = new ItemsRequest();
String response = constructor.retrieveItemsAsJson(request);
JSONObject jsonObj = new JSONObject(response);
JSONArray itemsArray = jsonObj.getJSONArray("items");
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

// Add a variations map to request specific variation attributes as an array or object (optional)
VariationsMap variationsMap = new VariationsMap();
variationsMap.setdType(VariationsMap.Dtypes.array);
variationsMap.addGroupByRule("variation", "data.variation_id");
variationsMap.addValueRule("size", VariationsMap.AggregationTypes.first, "data.facets.size");
request.setVariationsMap(variationsMap);

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

// Add the following paramaters to request for hidden fields or facets
request.getHiddenFields().add("hidden_price_field");
request.getHiddenFacets().add("hidden_brand_facet");

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Add a variations map to request specific variation attributes as an array or object (optional)
VariationsMap variationsMap = new VariationsMap();
variationsMap.setdType(VariationsMap.Dtypes.array);
variationsMap.addGroupByRule("variation", "data.variation_id");
variationsMap.addValueRule("size", VariationsMap.AggregationTypes.first, "data.facets.size");
request.setVariationsMap(variationsMap);

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

## Retrieving a breakdown of number of search results per algorithm

To determine number of results coming from token matching algorithm vs. cognitive embeddings algorithm, you can use the `ResultSources` object under the response.

```java
// Request results as an object
SearchResponse response = constructor.search(request, userInfo);
Integer resultsFromTokenMatching = response.getResponse().getResultSources().getTokenMatch().getCount();
Integer resultsFromCognitiveEmbeddings = response.getResponse().getResultSources().getEmbeddingsMatch().getCount();
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

// Add the following paramaters to request for hidden fields or facets
request.getHiddenFields().add("hidden_price_field");
request.getHiddenFacets().add("hidden_brand_facet");

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Add a variations map to request specific variation attributes as an array or object (optional)
VariationsMap variationsMap = new VariationsMap();
variationsMap.setdType(VariationsMap.Dtypes.array);
variationsMap.addGroupByRule("variation", "data.variation_id");
variationsMap.addValueRule("size", VariationsMap.AggregationTypes.first, "data.facets.size");
request.setVariationsMap(variationsMap);

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

# Retrieving Browse Results for Item ID's

To retrieve browse results for a supplied list of item ID's, you will need to create a `BrowseItemsRequest`. When creating the `BrowseItemsRequest` the `ids` parameter will be a list of item ID's. In this request, you can also specify the number of results you want per page, the page you want, sorting instructions, and also filter the results by category or facets. If the results are for a specific user, you can also create a `UserInfo` object, which will allow you to retrieve personalized results.

```java
// Create a BrowseItemsRequest with the filter name and filter value to request results for
BrowseItemsRequest request = new BrowseItemsRequest(Arrays.asList("t-shirt-xxl"));

// Add in additional parameters
request.setResultsPerPage(5);
request.setPage(1);
request.setGroupId("625");r
request.setSortBy("Price");
request.setSortAscending(true);
request.getFacets().put("Brand", Arrays.asList("Jif"))

// Add the following paramaters to request for hidden fields or facets
request.getHiddenFields().add("hidden_price_field");
request.getHiddenFacets().add("hidden_brand_facet");

// Create a UserInfo object with the session and unique device identifier (optional)
UserInfo userInfo = new UserInfo(5, "device-id-1123123");
userInfo.setUserSegments(Arrays.asList("Desktop", "Chrome"));

// Request results as an object
BrowseResponse response = constructor.browseItems(request, userInfo);

// Request results as a JSON string
String response = constructor.browseItemsAsJSON(request, userInfo);
```

If you'd like to retrieve browse items results asynchronously, the above code can be modified slightly to utilize a callback methodology:

```java
constructor.browseItems(request, userInfo, new BrowseCallback() {
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

// Add a variations map to request specific variation attributes as an array or object (optional)
VariationsMap variationsMap = new VariationsMap();
variationsMap.setdType(VariationsMap.Dtypes.array);
variationsMap.addGroupByRule("variation", "data.variation_id");
variationsMap.addValueRule("size", VariationsMap.AggregationTypes.first, "data.facets.size");
request.setVariationsMap(variationsMap);

// Request results as an object
RecommendationsResponse response = constructor.recommendations(request, userInfo);

// Request results as a JSON string
String response = constructor.recommendationsAsJSON(request, userInfo);
```

# Retrieving All Tasks

To retrieve all tasks, you will need to create a `AllTasksRequest`. In this request you can also specify the page and number of results per page. The page and number of results per page will default to 1 and 20 respectively.

```java
// Create a AllTasksRequest to request results for
AllTasksRequest request = new AllTasksRequest();

// Add in additional parameters
request.setPage(2);
request.setResultsPerPage(10);

//Request all tasks as an object
AllTasksResponse response = constructor.allTasks(request);

//Request all tasks as a JSON string
String response = constructor.allTasksAsJSON(request);
```

# Retrieving Task with task_id

To retrieve a specific task with a task_id, you will need to create a `TaskRequest`.

```java
// Create a TaskRequest with the task_id to retrieve
TaskRequest request = new TaskRequest("12345");

//Request task as an object
Task response = constructor.task(request);

//Request task as a JSON string
String response = constructor.taskAsJSON(request);
```

# Testing

Download the repository and run the following commands from `./constructorio-client`

```bash
mvn clean               # clean target directory
mvn install             # installs dependencies
mvn test                # run tests
mvn spotless:check      # check lint
mvn spotless:apply      # run lint
mvn jacoco:report       # writes code coverage reports to ./target/site/jacoco
mvn javadoc:javadoc     # generate docs
```

# Environment Variables

```bash
TEST_REQUEST_API_KEY    # autocomplete_key for the Client Library Integration Request (CRABS) Tests
TEST_CATALOG_API_KEY    # autocomplete_key for the Client Library Integration Catalog Tests
TEST_API_TOKEN          # API token for the tests
```
