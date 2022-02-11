package dev.gregorius.library.json.reflect.model;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class ApiClient {

    private final ApiRequest request;

    /**
     * Performs a GET request against the defined request URL.
     *
     * @return A new {@link ApiResponse} instance containing the request response
     */
    public ApiResponse get() {
        return execute(HttpMethod.GET);
    }

    /**
     * Performs a POST request against the defined request URL.
     *
     * @return A new {@link ApiResponse} instance containing the request response
     */
    public ApiResponse post() {
        return execute(HttpMethod.POST);
    }

    /**
     * Performs a PUT request against the defined request URL.
     *
     * @return A new {@link ApiResponse} instance containing the request response
     */
    public ApiResponse put() {
        return execute(HttpMethod.PUT);
    }

    /**
     * Performs a DELETE request against the defined request URL.
     *
     * @return A new {@link ApiResponse} instance containing the request response
     */
    public ApiResponse delete() {
        return execute(HttpMethod.DELETE);
    }

    /**
     * Performs a PATCH request against the defined request URL.
     *
     * @return A new {@link ApiResponse} instance containing the request response
     */
    public ApiResponse patch() {
        return execute(HttpMethod.PATCH);
    }

    private ApiResponse execute(final HttpMethod httpMethod) {
        final HttpHeaders requestHeaders = new HttpHeaders(request.getHeaders());

        if (request.getContentType() != null) {
            requestHeaders.add(HttpHeaders.CONTENT_TYPE, request.getContentType());
        }

        final HttpEntity<String> requestEntity = new HttpEntity<>(request.getBody(), requestHeaders);

        return new ApiResponse(request.getRestTemplate().exchange(request.getPathWithQueryParameters(), httpMethod, requestEntity, String.class));
    }
}
