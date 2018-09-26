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

# Simple Example
Searching for peanut butter
```java
SearchRequest request = new SearchRequest("peanut butter");
request.setResultsPerPage(5);
request.getFacets().put("Brand", Arrays.asList("Jif"))
SearchResponse response = constructor.search(request, null);
```
