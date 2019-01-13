package com.nimble.sloth.dispatcher.func.properties;

import com.nimble.sloth.dispatcher.func.exceptions.Missing;
import org.apache.commons.logging.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.WAREHOUSE_TOKEN;
import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.WAREHOUSE_URL;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.logging.LogFactory.getLog;

@Service
public class PropertiesService {
    public static final String APP_ID = "the-nimble-sloth-dispatcher";
    public static final String APP_TOKEN = "5FqR1OvAmOzdBJ9Q7U73Vu8esVVF7Zwh";

    private static final String WAREHOUSE_ID = "the-nimble-sloth-warehouse";
    private static final long TEN_MINUTES_IN_MILLIS = 1000 * 60 * 10;

    private final Log log = getLog(PropertiesClient.class);
    private final PropertiesClient client;
    private Map<String, String> properties = new ConcurrentHashMap<>();

    public PropertiesService(final PropertiesClient client) {
        this.client = client;
    }

    @PostConstruct
    public void postConstruct() {
        loadProperties();
    }

    @Scheduled(fixedDelay = TEN_MINUTES_IN_MILLIS)
    public void reloadProperties() {
        loadProperties();
    }

    private void loadProperties() {
        final String sessionId = client.login(APP_ID, APP_TOKEN);
        final List<ServerProperty> appProperties = client.loadProperties(APP_ID, sessionId);
        final ApplicationInfo warehouseInfo = client.getAppInfo(WAREHOUSE_ID, sessionId);
        final String token = warehouseInfo.getToken();
        final String url = warehouseInfo.getUrl();

        final Map<String, String> loadedProperties = appProperties
                .stream()
                .collect(toMap(ServerProperty::getKey, ServerProperty::getValue));

        loadedProperties.put(WAREHOUSE_URL.getName(), url);
        loadedProperties.put(WAREHOUSE_TOKEN.getName(), token);

        final String message = String.format("Loaded properties: %s", loadedProperties);
        log.info(message);

        properties.putAll(loadedProperties);
    }

    public Optional<String> getProperty(final PropertiesKey key) {
        final String keyValue = key.getName();
        final String value = properties.get(keyValue);
        return ofNullable(value);
    }

    public String getRequiredProperty(final PropertiesKey key) {
        final String keyValue = key.getName();
        final String value = properties.get(keyValue);
        final String errorMessage = String.format("System property %s is missing", key);
        return ofNullable(value).orElseThrow(() -> new Missing(errorMessage));
    }
}
