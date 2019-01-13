package com.nimble.sloth.dispatcher.func.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimble.sloth.dispatcher.func.exceptions.CommunicationFailed;
import com.nimble.sloth.dispatcher.func.exceptions.EssentialCommunicationFailed;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.apache.commons.logging.LogFactory.getLog;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@SuppressWarnings("WeakerAccess")
@Component
public class PropertiesClient {

    private static final String ROUTER_ADDRESS = "https://the-nimble-sloth-router.herokuapp.com";
    private static final String AUTHENTICATION_HEADER_NAME = "X-Auth-Token";

    private final Log log = getLog(PropertiesClient.class);
    private final RestTemplate template = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public ApplicationInfo getAppInfo(
            final String appId,
            final String sessionId) {
        final String url = String.format("%s/apps/%s", ROUTER_ADDRESS, appId);
        final String errorMessage = String.format("Couldn't load %s's profile", appId);

        return ofNullable(authenticatedGet(sessionId, url, ApplicationInfo.class))
                .filter(this::isSuccessfulRequest)
                .map(HttpEntity::getBody)
                .orElseThrow(() -> new CommunicationFailed(errorMessage));
    }

    public List<ServerProperty> loadProperties(
            final String appId,
            final String sessionId) {
        final String url = String.format("%s/properties/%s", ROUTER_ADDRESS, appId);

        return ofNullable(authenticatedGet(sessionId, url, ServerProperty[].class))
                .filter(this::isSuccessfulRequest)
                .map(HttpEntity::getBody)
                .map(Arrays::asList)
                .orElseThrow(() -> new EssentialCommunicationFailed("Couldn't load application properties"));
    }

    private <T> ResponseEntity<T> authenticatedGet(
            final String sessionId,
            final String url,
            final Class<T> responseClass) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHENTICATION_HEADER_NAME, sessionId);
        final HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        return template.exchange(url, GET, entity, responseClass);
    }

    private boolean isSuccessfulRequest(final ResponseEntity<?> result) {
        return result.getStatusCode().is2xxSuccessful();
    }

    public String login(final String appId, final String appToken) {
        final LoginRequest loginRequest = new LoginRequest(appId, appToken);
        try {
            final LoginResponse response = callLogin(loginRequest);
            return response.getSessionId();
        } catch (Exception e) {
            throw new EssentialCommunicationFailed(e);
        }
    }

    private LoginResponse callLogin(final LoginRequest loginRequest) throws IOException {
        final String loginUrl = String.format("%s/login", ROUTER_ADDRESS);
        final ResponseEntity<String> response = template.postForEntity(loginUrl, loginRequest, String.class);
        final String body = response.getBody();
        if (response.getStatusCode().equals(UNAUTHORIZED)) {
            log.error("Authentication to router failed");
            throw new EssentialCommunicationFailed("Login to router failed");
        } else if (!isSuccessfulRequest(response) || body == null) {
            final String message = String.format("Can't access the router response: %s", body);
            log.error("Router response is unknown");
            throw new EssentialCommunicationFailed(message);
        } else {
            return mapper.readValue(body, LoginResponse.class);
        }
    }
}
