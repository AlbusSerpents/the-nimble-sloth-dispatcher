package com.nimble.sloth.dispatcher.func.orders.warehouse;

import com.nimble.sloth.dispatcher.func.exceptions.CommunicationFailed;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.springframework.http.HttpMethod.GET;

@Component
public class WarehouseClient {

    private static final String RELATIVE_URL = "orders";
    private static final String AUTHENTICATION_HEADER = "X-Auth-Token";

    private final RestTemplate template = new RestTemplate();

    public List<String> getWarehouseContents(
            final String baseUrl,
            final String token) {
        final String url = String.format("%s/%s", baseUrl, RELATIVE_URL);
        final HttpEntity<Object> entity = authenticationEntity(token);
        final ResponseEntity<WarehouseResponse[]> response = template.exchange(url, GET, entity, WarehouseResponse[].class);
        final WarehouseResponse[] ids = extractBody(response);

        return of(ids)
                .map(WarehouseResponse::get_id)
                .collect(toList());
    }

    private HttpEntity<Object> authenticationEntity(final String token) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHENTICATION_HEADER, token);
        return new HttpEntity<>(null, headers);
    }

    private WarehouseResponse[] extractBody(final ResponseEntity<WarehouseResponse[]> entity) {
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
