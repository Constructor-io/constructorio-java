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
A `ConstructorItem` contains all the information about a product. Any data that doesn't pertain to a specific field can be placed in the metadata field. The following is an example of creating a `ConstructorItem` and setting the different fields.

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
To add or update an individual item, you will need to provide a `ConstructorItem` and the relevant `Autocomplete Section` it belongs to.

```java
// Add an item to the Products section
constructor.addItem(item, "Products");

// Add or Update an item in the Products section
constructor.addOrUpdateItem(item, "Products");
```

## Adding or Updating a Batch of Items
Similarly with adding or updating a batch of items, you will need to provide an array of `ConstructorItem`s and their relevant `Autocomplete Section`.
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
## Autocomplete Results

To request for autocomplete results, you will need to create an `AutocompleteRequest`. You can specify the number of results you want per `Autocomplete Section`.

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

## Search Results
To request for search results, you will need to create a `SearchRequest`. In the `SearchRequest` you can: set the number of results you want per page, set the page you would like to request for, and filter the search by category (or group id) and facets. 

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