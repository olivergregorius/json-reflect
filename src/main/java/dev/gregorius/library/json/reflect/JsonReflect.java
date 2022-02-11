package dev.gregorius.library.json.reflect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.gregorius.library.json.reflect.model.ApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class JsonReflect {

    private static final String JSON_NULL = "null";
    private static final String NO_REQUEST_BODY_LOGMSG = "No request body";
    private static final String NO_RESPONSE_BODY_LOGMSG = "No response body";

    private JsonReflect() {
    }

    private static RestTemplate restTemplate = buildRestTemplate(StringUtils.EMPTY);

    /**
     * Sets the base URL for endpoints/resources to be tested.
     *
     * @param baseUrl the base URL, usually the hostname of the test system
     */
    public static void setBaseUrl(final String baseUrl) {
        restTemplate = buildRestTemplate(baseUrl);
    }

    /**
     * Defines the endpoint to be called.
     *
     * @param path the endpoint path
     * @return {@link ApiRequest instance} carrying all request information
     */
    public static ApiRequest endpoint(final String path) {
        return new ApiRequest(restTemplate, path);
    }

    /**
     * Alias method for {@link #endpoint(String)}.
     *
     * @param path the resource path
     * @return {@link ApiRequest instance} carrying all request information
     */
    public static ApiRequest resource(final String path) {
        return endpoint(path);
    }

    private static RestTemplate buildRestTemplate(final String baseUrl) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
            .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .interceptors(new LoggingInterceptor())
            .errorHandler(new NoopResponseErrorHandler())
            .messageConverters(new StringHttpMessageConverter(UTF_8));

        if (StringUtils.isNotEmpty(baseUrl)) {
            restTemplateBuilder = restTemplateBuilder.rootUri(baseUrl);
        }

        return restTemplateBuilder.build();
    }

    private static class LoggingInterceptor implements ClientHttpRequestInterceptor {

        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        @Override
        public ClientHttpResponse intercept(final HttpRequest request, final byte[] requestBody, final ClientHttpRequestExecution execution) throws IOException {
            String responseBody = StringUtils.EMPTY;

            final ClientHttpResponse response = execution.execute(request, requestBody);
            try {
                final String requestBodyJson = GSON.toJson(JsonParser.parseString(new String(requestBody, UTF_8)));
                if (StringUtils.isNotEmpty(requestBodyJson) && !StringUtils.equals(requestBodyJson, JSON_NULL)) {
                    log.info("Request body:\n{}", requestBodyJson);
                } else {
                    log.info(NO_REQUEST_BODY_LOGMSG);
                }

                // Call response.getStatusCode() first to ensure that in case an error was returned the errorStream is populated with the error data and to avoid an
                // IOException being thrown when calling response.getBody() (https://stackoverflow.com/a/47689313/10355617)
                log.info("Response status code: {}", response.getStatusCode());

                final InputStreamReader responseReader = new InputStreamReader(response.getBody(), UTF_8);
                responseBody = new BufferedReader(responseReader).lines().collect(Collectors.joining("\n"));
                final String responseBodyJson = GSON.toJson(JsonParser.parseString(responseBody));
                if (StringUtils.isNotEmpty(responseBodyJson) && !StringUtils.equals(responseBodyJson, JSON_NULL)) {
                    log.info("Response body:\n{}", responseBodyJson);
                } else {
                    log.info(NO_RESPONSE_BODY_LOGMSG);
                }
            } catch (final JsonSyntaxException e) {
                log.error("Error during logging of request/response body. May not be valid Json.", e);
                final String requestBodyString = new String(requestBody, UTF_8);
                if (StringUtils.isNotEmpty(requestBodyString) && !StringUtils.equals(requestBodyString, JSON_NULL)) {
                    log.info("Request body:\n{}", requestBodyString);
                } else {
                    log.info(NO_REQUEST_BODY_LOGMSG);
                }
                if (StringUtils.isNotEmpty(responseBody) && !StringUtils.equals(responseBody, JSON_NULL)) {
                    log.info("Response body:\n{}", responseBody);
                } else {
                    log.info(NO_RESPONSE_BODY_LOGMSG);
                }
            }

            return response;
        }
    }

    private static class NoopResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(final ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(final ClientHttpResponse response) throws IOException {
            // Nothing to do
        }
    }
}
