package dev.gregorius.library.json.reflect.model;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Getter(AccessLevel.PACKAGE)
public class ApiRequest {

    private final RestTemplate restTemplate;
    private final String path;
    private final MultiValueMap<String, String> headers;
    private final Map<String, String> parameters;
    private String contentType = MediaType.TEXT_PLAIN.toString();
    private String body;

    public ApiRequest(final RestTemplate restTemplate, final String path) {
        this.restTemplate = restTemplate;
        this.path = path;
        this.headers = CollectionUtils.toMultiValueMap(new HashMap<>());
        this.parameters = new HashMap<>();
    }

    /**
     * Syntactic sugar for improving readability - initiating the "given"-part.
     *
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest given() {
        return this;
    }

    /**
     * Syntactic sugar for improving readability.
     *
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest and() {
        return this;
    }

    /**
     * Syntactic sugar for improving readability.
     *
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest build() {
        return this;
    }

    /**
     * Adds a header to the request.
     *
     * @param key   the header key
     * @param value the header value
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest header(final String key, final String value) {
        this.headers.add(key, value);

        return this;
    }

    /**
     * Adds a request parameter.
     *
     * @param key   the request parameter key
     * @param value the request parameter value
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest parameter(final String key, final String value) {
        this.parameters.put(key, value);

        return this;
    }

    /**
     * Defines the value of the Content-Type header.
     * <p>
     * If no method setting the Content-Type header is called the default value is "text/plain".
     *
     * @param contentType the value of the Content-Type header
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest contentType(final String contentType) {
        this.contentType = contentType;

        return this;
    }

    /**
     * Explicitly omits the Content-Type header.
     * <p>
     * If no method setting the Content-Type header is called the default value is "text/plain".
     *
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest noContentType() {
        this.contentType = null;

        return this;
    }

    /**
     * Sets the request body.
     *
     * @param body the value of the request body
     * @return this {@link ApiRequest} instance
     */
    public ApiRequest body(final String body) {
        this.body = body;

        return this;
    }

    /**
     * Generates the full request path with appended query parameters.
     *
     * @return the request path with all request parameters as {@link String}
     */
    String getPathWithQueryParameters() {
        if (CollectionUtils.isEmpty(parameters)) {
            return path;
        }

        final StringJoiner requestPathBuilder = new StringJoiner("&", String.format("%s?", path), StringUtils.EMPTY);
        parameters.forEach((key, val) -> requestPathBuilder.add(String.format("%s=%s", key, val)));

        return requestPathBuilder.toString();
    }

    /**
     * Marks the request as ready for transmission - initiating the "when"-part.
     *
     * @return a new {@link ApiClient} instance for performing the API call
     */
    public ApiClient when() {
        return new ApiClient(this);
    }
}
