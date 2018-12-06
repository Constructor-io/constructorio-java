[![Release](https://jitpack.io/v/Constructor-io/constructorio-java.svg)](https://jitpack.io/#Constructor-io/constructorio-java) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/Constructor-io/constructorio-java/blob/master/LICENSE)

# Constructor-IO Java Client
[Constructor.io](http://constructor.io/) provides search as a service that optimizes results using artificial intelligence (including natural language processing, re-ranking to optimize for conversions, and user personalization).

# Documentation
For the most up-to-date documentation for this library, please visit our [API Documentation](https://docs.constructor.io/rest-api.html?java).

# Installation
1. Follow the directions at [Jitpack.io](https://jitpack.io/#Constructor-io/constructorio-java/v4.9.2) to add the client to your project.
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

# Retrieving Results

To retrieve autocomplete results, you will need to create an `AutocompleteRequest`. You can specify the number of results you want per `Autocomplete Section`.

```java
// Create an AutocompleteRequest with the term to request results for
AutocompleteRequest request = new AutocompleteRequest("rain coat");

// Define the number of results to show per section
Map<String, Integer> resultsPerSection = new HashMap<String, Integer>();
resultsPerSection.put("Products", Integer.valueOf(6));
resultsPerSection.put("Search Suggestions", Integer.valueOf(8));
request.setResultsPerSection(resultsPerSection);

// Identify the user session and user id to show results for (optional)
UserInfo userInfo = new UserInfo(5, "user-id-1123123");

// Request results as an object
AutocompleteResponse response = constructor.autocomplete(request, userInfo);

// Request results as a JSON string
String response = constructor.autocompleteAsJSON(request, userInfo);
```

To retrieve search results, you will need to create a `SearchRequest`. In the `SearchRequest` you can specify the number of results you want per page, the page you want, and also filter the search by category or facets. 

```java
// Create a SearchRequest with the term to request results for
SearchRequest request = new SearchRequest("peanut butter");

// Add in additional parameters
request.setResultsPerPage(5);
request.setPage(1);
request.setGroupId("625");
request.getFacets().put("Brand", Arrays.asList("Jif"))

// Identify the user session and user id to show results for (optional)
UserInfo userInfo = new UserInfo(5, "user-id-1123123");

// Request results as an object
SearchResponse response = constructor.search(request, userInfo);

// Request results as a JSON string
String response = constructor.searchAsJSON(request, userInfo);
```

To retrieve search results by natural language, you will need to create a `NaturalLanguageSearchRequest`. In the `NaturalLanguageSearchRequest`. just like `SearchRequest`, you can specify the number of results you want per page and the page you want.

```java
// Create a NaturalLanguageSearchRequest with the term to request results for
NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest('cheese');

// Add in additional parameters
request.setResultsPerPage(5);
request.setPage(1);

// Identify the user session and user id to show results for (optional)
UserInfo userInfo = new UserInfo(5, "user-id-1123123");

// Request results by natural language as an object
SearchResponse response = constructor.naturalLanguageSearch(request, userInfo);

// Request results by natural language as a JSON string
String response = constructor.naturalLanguageSearchAsJSON(request, userInfo);

```

# Testing
Download the repository and run the following commands from `./constructorio-client`

```bash
mvn clean               # clean target directory
mvn test                # run tests
mvn jacoco:report       # post tests, write coverage to ./target/site/jacoco
```
