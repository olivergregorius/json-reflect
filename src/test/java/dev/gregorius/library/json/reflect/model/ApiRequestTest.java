package dev.gregorius.library.json.reflect.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.gregorius.library.json.reflect.JsonReflect;
import dev.gregorius.library.json.reflect.setup.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

class ApiRequestTest extends BaseTest {

    @Test
    void given_headersSet_when_performingApiCall_then_requestContainsHeaders() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .header("header1", "value1")
            .header("header2", "value2")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("header1").getAsString()).isEqualTo("value1");
        assertThat(actualJson.get("header2").getAsString()).isEqualTo("value2");
    }

    @Test
    void given_headersNotSet_when_performingApiCall_then_requestDoesNotContainHeaders() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("header1")).isNull();
        assertThat(actualJson.get("header2")).isNull();
    }

    @Test
    void given_parametersSet_when_performingApiCall_then_requestContainsParameters() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-parameters")
            .given()
            .parameter("param1", "value1")
            .parameter("param2", "value2")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("param1").getAsString()).isEqualTo("value1");
        assertThat(actualJson.get("param2").getAsString()).isEqualTo("value2");
    }

    @Test
    void given_parametersNotSet_when_performingApiCall_then_requestDoesNotContainParameters() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-parameters")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("param1")).isNull();
        assertThat(actualJson.get("param2")).isNull();
    }

    @Test
    void given_contentTypeSet_when_performingApiCall_then_requestContainsContentTypeHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .contentType("testContentType")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get(HttpHeaders.CONTENT_TYPE.toLowerCase()).getAsString()).isEqualTo("testContentType");
    }

    @Test
    void given_contentTypeNotSet_when_performingApiCall_then_requestContainsDefaultContentTypeHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get(HttpHeaders.CONTENT_TYPE.toLowerCase()).getAsString()).isEqualTo("text/plain");
    }

    @Test
    void given_contentTypeExplicitlyOmitted_when_performingApiCall_then_requestDoesNotContainContentTypeHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .noContentType()
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get(HttpHeaders.CONTENT_TYPE.toLowerCase())).isNull();
    }

    @Test
    void given_bodySet_when_performingApiCall_then_requestContainsBody() {
        final String requestBody = """
            {
                "data": {
                    "key1": "value1",
                    "key2": "value2"
                }
            }
            """;
        final String responseBody = JsonReflect.endpoint("/post-reflecting-body")
            .given()
            .body(requestBody)
            .when()
            .post()
            .then()
            .responseBody()
            .getAsString();

        assertThat(responseBody).isEqualTo(requestBody);
    }

    @Test
    void given_bodyNotSet_when_performingApiCall_then_requestDoesNotContainBody() {
        final String responseBody = JsonReflect.endpoint("/post-reflecting-body")
            .when()
            .post()
            .then()
            .responseBody()
            .getAsString();

        assertThat(responseBody).isNull();
    }

    @Test
    void given_parametersSet_when_getPathWithQueryParameters_then_queryParametersAppendedToPath() {
        final String requestUrl = JsonReflect.endpoint("/post-reflecting-body")
            .given()
            .parameter("key1", "value1")
            .parameter("key2", "value2")
            .getPathWithQueryParameters();

        assertThat(requestUrl).isEqualTo("/post-reflecting-body?key1=value1&key2=value2");
    }

    @Test
    void given_noParameters_when_getPathWithQueryParameters_then_pathOnly() {
        final String requestUrl = JsonReflect.endpoint("/post-reflecting-body")
            .getPathWithQueryParameters();

        assertThat(requestUrl).isEqualTo("/post-reflecting-body");
    }

    @Test
    void given_basicAuthSet_when_performingApiCall_then_requestContainsAuthorizationHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .authentication().basicAuth("testuser", "testpass")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("authorization").getAsString()).isEqualTo("Basic dGVzdHVzZXI6dGVzdHBhc3M=");
    }

    @Test
    void given_bearerTokenAuthSet_when_performingApiCall_then_requestContainsAuthorizationHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .authentication().bearerTokenAuth("bearerToken")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("authorization").getAsString()).isEqualTo("Bearer bearerToken");
    }

    @Test
    void given_apiTokenAuthSet_when_performingApiCall_then_requestContainsAuthorizationHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .authentication().apiTokenAuth("apiToken")
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("authorization").getAsString()).isEqualTo("Apikey apiToken");
    }

    @Test
    void given_noAuthSet_when_performingApiCall_then_requestContainsNoAuthorizationHeader() {
        final String responseBody = JsonReflect.endpoint("/get-reflecting-headers")
            .given()
            .when()
            .get()
            .then()
            .responseBody()
            .getAsString();

        final JsonObject actualJson = JsonParser.parseString(responseBody).getAsJsonObject();

        assertThat(actualJson.get("authorization")).isNull();
    }
}
