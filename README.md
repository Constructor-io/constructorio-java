# Contructor-IO Java Client

A Java REST client for the Constructor.io API. [Constructor.io](http://constructor.io/) provides search as a service that learns customer intent and optimizes results using artifical intelligence (including natural language processing, re-ranking to optimize for conversions, and user personalization).

# Dependencies

* [Unirest](https://github.com/Kong/unirest-java)
* [Gson](https://github.com/google/gson)
* [JSON](https://github.com/stleary/JSON-java)

# Test Dependencies

* [Maven](https://github.com/apache/maven)
* [JUnit4](https://github.com/junit-team/junit4)

# Building and Test

> NOTE: Maven commands run from ./constructorio-client/

```bash
mvn clean               # clean target directory
mvn test                # run tests
mvn jacoco:report       # post tests, write coverage to ./target/site/jacoco
```

# Documentation

For the most up-to-date documentation for this library, please visit our [Java documentation page](https://docs.constructor.io/rest-api.html?java).
