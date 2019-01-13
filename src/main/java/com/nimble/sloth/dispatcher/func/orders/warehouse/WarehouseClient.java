package com.nimble.sloth.dispatcher.func.orders.warehouse;

import com.nimble.sloth.dispatcher.func.exceptions.CommunicationFailed;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.GET;

@Component
public class WarehouseClient {

    private static final String RELATIVE_URL = "оrders";
    private static final String AUTHENTICATION_HEADER = "X-Auth-Token";

    private final RestTemplate template = new RestTemplate();

    public List<String> getWarehouseContents(
            final String baseUrl, final String token) {
        final String url = String.format("%s/%s", baseUrl, RELATIVE_URL);
        final HttpEntity<Object> entity = authenticationEntity(token);
        final ResponseEntity<String[]> response = template.exchange(url, GET, entity, String[].class);
        final String[] ids = extractBody(response);
        return asList(ids);
    }

    private HttpEntity<Object> authenticationEntity(final String token) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHENTICATION_HEADER, token);
        return new HttpEntity<>(null, headers);
    }

    private String[] extractBody(final ResponseEntity<String[]> entity) {
        final HttpStatus code = entity.getStatusCode();
        if (!code.is2xxSuccessful()) {
            throw new CommunicationFailed("Couldn't access the warehouse");
        } else if (entity.getBody() == null) {
            throw new CommunicationFailed("Warehouse returned empty response");
        } else {
            return entity.getBody();
        }
    }
}
