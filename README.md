# JSON REFlecT

[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/olivergregorius/json-reflect/Gradle%20Build?label=Gradle%20Build&logo=github)](https://github.com/olivergregorius/json-reflect/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=olivergregorius_json-reflect&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=olivergregorius_json-reflect)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=olivergregorius_json-reflect&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=olivergregorius_json-reflect)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=olivergregorius_json-reflect&metric=coverage)](https://sonarcloud.io/summary/new_code?id=olivergregorius_json-reflect)
[![GitHub](https://img.shields.io/github/license/olivergregorius/json-reflect?label=License)](https://github.com/olivergregorius/json-reflect/blob/HEAD/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.gregorius.library/json-reflect?label=Maven%20Central)](https://search.maven.org/artifact/dev.gregorius.library/json-reflect)

## Introduction

JSON REFlecT stands for "JSON REST Fluent economic Testing" and is exactly that: A library for testing RESTful APIs providing JSON request- and response-bodies.
The test code is written following the fluent programming style which improves readability, extensibility and maintainability - gaining you more efficiency in
testing your APIs.

## Features

- Fluent programming style
- Improved readability
- JSON fuzzy matching
- JSON array matching ignoring the order of elements
- Endpoint authentication/authorization (Basic Auth, Bearer Token, API Token)
- Response body field extraction

## Getting started

### Test preparation

The first step is to define the URL of the server serving the endpoints to be tested. This is achieved by calling the static
method `JsonReflect.setBaseUrl(String baseUrl)`. The `baseUrl`-parameter might be of any common HTTP-URL-format: `scheme://host.domain[:port]`. Setting the base
URL is only necessary once before executing all tests if not multiple servers should be tested.

### Define endpoint to be tested

A test-case is initiated by calling the static method `JsonReflect.endpoint(String path)` with the endpoint path to be called as the only argument. If the
endpoint `https://example.org:8080/get-resource` should be tested this is done by

```
JsonReflect.setBaseUrl("https://example.org:8080");
JsonReflect.endpoint("/get-resource");
```

Note: A method `JsonReflect.resource(String path)` exists which is an alias for `JsonReflect.endpoint(String path)`.

### Create API request

An API request is built using fluent programming style. After [defining the endpoint to be tested](#define-endpoint-to-be-tested) the API request is built by
calling one or more methods defining different parameters of the API request to be sent. Please note that some methods exists as syntactic sugar for improved
readability:

- `given()` - e.g. mark the beginning of the API request building sequence
- `and()`
- `build()` - e.g. mark the end of the API request building sequence

#### Headers

- `header(String key, String value)` - Setting a header with `key` and `value`

##### Content-Type header

- `contentType(String contentType)` - Setting the Content-Type header to the passed `contentType`-value. If no Content-Type header is set explicitly it defaults
  to "text/plain".
- `noContentType()` - Unsetting the Content-Type header explicitly

##### Authorization header

- `authentication().basicAuth(String username, String password)` - Setting the Authorization header for basic authentication
- `authentication().bearerTokenAuth(String bearerToken)` - Setting the Authorization header for bearer-token authentication
- `authentication().apiTokenAuth(String apiToken)` - Setting the Authorization header for API-token authentication

#### Query parameters

- `parameter(String key, String value)` - Setting a query parameter with `key` and `value`

#### Request body

- `body(String body)` - Setting the request body to the value of `body`

### Send request

The sequence for sending the [API request](#create-api-request) is initiated by calling the method `when()` followed by the HTTP method to be used for the call:

- `get()` - Sending a GET-request.
- `post()` - Sending a POST-request.
- `put()` - Sending a PUT-request.
- `delete()` - Sending a DELETE-request.
- `patch()` - Sending a PATCH-request.

### Handle API response

After sending the request the received response can be handled. Please note that some methods exists as syntactic sugar for improved readability:

- `then()` - e.g. mark the beginning of the API response handling sequence
- `and()`

#### Check the HTTP response code

By calling `httpStatusCodeIs(Integer expectedStatusCode)` the HTTP response code can be tested against an expected value.

#### Extract the response body

Calling the method `getResponseBody()` extracts the response body out of the response for [further handling](#handling-response-body).

### Handling response body

#### Extract response body as String

The response body can be extracted as String by calling `getAsString()`.

#### Response body assertion

Equality of the whole response body can be checked by calling the method `isEqualTo(String expectedResponseBody)` passing the expected response body as String.
Fuzzy matching, as you might know it from other testing frameworks, can be used here. The order of elements in an array does not matter.

#### Extract fields from the response body

Single fields of the response body can be extracted by calling `valueOf(final String jsonPath)` passing the [JSON-path expression](https://jsonpath.com/) of the
desired value. The extracted value can be

- checked against an expected value by calling `isEqualTo(String expectedValue)`.
- extracted as `JsonElement`-instance by calling `getValue()`.
- assigned to a `JsonValueContainer`-instance for further usage (e.g. as input in another test) by calling `assignTo(JsonValueContainer jsonValueContainer)`.

## Examples

### Testcase 1 - Exact response body matching

```java
class JsonReflectTest {

    @BeforeAll
    void beforeAll() {
        JsonReflect.setBaseUrl("https://testserver.company.com:8080");
    }

    @Test
    void testGet() {
        JsonReflect.endpoint("/vehicles/cars")
            .given()
            .authentication().basicAuth("testuser", "testpass")
            .parameter("brand", "VW")
            .when()
            .get()
            .then()
            .httpStatusCodeIs(200)
            .and()
            .getResponseBody()
            .isEqualTo("""
                {
                  "data": [
                    {
                      "id": 1,
                      "attributes": {
                        "brand": "VW",
                        "model": "Passat"
                      }
                    },
                    {
                      "id": 2,
                      "attributes": {
                        "brand": "VW",
                        "model": "Golf"
                      }
                    }
                  ]
                }
                """);
    }
}
```

The above test is a simple test for a GET-endpoint. The endpoint "https://testserver.company.com:8080/vehicles/cars" is called using Basic Auth as testuser. One
parameter "brand" is set to the value "VW", resulting in the following URL to be called: "https://testserver.company.com:8080/vehicles/cars?brand=VW". The
status code is asserted to be 200, furthermore the response body is checked against the expected one to match it exactly - neglecting the order of the elements
in the array.

### Testcase 2 - Fuzzy matching of response body

```java
class JsonReflectTest {

    private JsonValueContainer newCarIdValueContainer = new JsonValueContainer();

    @BeforeAll
    void beforeAll() {
        JsonReflect.setBaseUrl("https://testserver.company.com:8080");
    }

    @Test
    void testPost() {
        JsonReflect.endpoint("/vehicles/cars")
            .given()
            .authentication().basicAuth("testuser", "testpass")
            .and()
            .body("""
                {
                  "data": {
                    "attributes": {
                      "brand": "VW",
                      "model": "Tiguan"
                    }
                  }
                }
                """)
            .when()
            .post()
            .then()
            .httpStatusCodeIs(201)
            .and()
            .getResponseBody()
            .isEqualTo("""
                {
                  "data": {
                    "id": #integer,
                    "attributes": {
                      "brand": "VW",
                      "model": "Tiguan"
                    }
                  }
                }
                """)
            .valueOf(".data.id").assignTo(newCarIdValueContainer);
    }
}
```

The POST-endpoint "https://testserver.company.com:8080/vehicles/cars" is tested in this case. It is called using Basic Auth as testuser, providing a request
body for creation of a new car instance. The status should be 201 (created) in this case. Additionally, fuzzy matching is used for assertion of the response
body, because the resource id may not be predictable in this case. The resource id is assigned to a `JsonValueContainer` for being used in subsequent test cases
for example.