[![Release](https://jitpack.io/v/Constructor-io/constructorio-java.svg)](https://jitpack.io/#Constructor-io/constructorio-java) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/hyperium/hyper/master/LICENSE)

# Constructor-IO Java Client
A Java REST client for the [Constructor.io API](https://docs.constructor.io/rest-api.html?java).

[Constructor.io](http://constructor.io/) provides search as a service that optimizes results using artificial intelligence (including natural language processing, re-ranking to optimize for conversions, and user personalization).

# Documentation
For the most up-to-date documentation for this library, please visit our [API Documentation](https://docs.constructor.io/rest-api.html?java).

# Installation
Please follow the directions at [Jitpack.io](https://jitpack.io/#Constructor-io/constructorio-java) to add the client to your project.

# Testing
Download the repository and run the following commands from `./constructorio-client`

```bash
mvn clean               # clean target directory
mvn test                # run tests
mvn jacoco:report       # post tests, write coverage to ./target/site/jacoco
```
