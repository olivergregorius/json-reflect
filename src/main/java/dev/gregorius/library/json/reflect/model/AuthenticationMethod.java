package dev.gregorius.library.json.reflect.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class AuthenticationMethod {

    private final ApiRequest apiRequest;

    /**
     * Adds an authentication header for basic auth to the request.
     *
     * @param username the username
     * @param password the password
     * @return the {@link ApiRequest} instance used for the request
     */
    public ApiRequest basicAuth(final String username, final String password) {
        final String credentialsString = String.format("%s:%s", username, password);
        final String base64EncodedCredentials = Base64.getEncoder().encodeToString(credentialsString.getBytes(StandardCharsets.UTF_8));

        addAuthorizationHeader("Basic", base64EncodedCredentials);

        return apiRequest;
    }

    /**
     * Adds an authentication header for bearer token auth to the request.
     *
     * @param bearerToken the bearer token
     * @return the {@link ApiRequest} instance used for the request
     */
    public ApiRequest bearerTokenAuth(final String bearerToken) {
        addAuthorizationHeader("Bearer", bearerToken);

        return apiRequest;
    }

    /**
     * Adds an authentication header for api token auth to the request.
     *
     * @param apiToken the api token
     * @return the {@link ApiRequest} instance used for the request
     */
    public ApiRequest apiTokenAuth(final String apiToken) {
        addAuthorizationHeader("Apikey", apiToken);

        return apiRequest;
    }

    private void addAuthorizationHeader(final String authType, final String credentials) {
        apiRequest.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", authType, credentials));
    }
}
