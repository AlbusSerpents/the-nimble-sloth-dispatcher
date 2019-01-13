package com.nimble.sloth.dispatcher.func.orders.warehouse;

import com.nimble.sloth.dispatcher.func.exceptions.MissingConfiguration;
import com.nimble.sloth.dispatcher.func.properties.PropertiesKey;
import com.nimble.sloth.dispatcher.func.properties.PropertiesService;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.WAREHOUSE_TOKEN;
import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.WAREHOUSE_URL;
import static java.util.Collections.emptyList;
import static org.apache.commons.logging.LogFactory.getLog;

@Service
public class WarehouseService {

    private final WarehouseClient client;
    private final PropertiesService properties;
    private final Log log = getLog(WarehouseService.class);

    public WarehouseService(
            final WarehouseClient client,
            final PropertiesService properties) {
        this.client = client;
        this.properties = properties;
    }

    public List<String> getWarehouseContents() {
        try {
            final String warehouseUrl = getProperty(WAREHOUSE_URL);
            final String warehouseToken = getProperty(WAREHOUSE_TOKEN);
            return client.getWarehouseContents(warehouseUrl, warehouseToken);
        } catch (Exception e) {
            log.error(e);
            return emptyList();
        }
    }

    private String getProperty(final PropertiesKey warehouseKey) {
        return properties
                .getProperty(warehouseKey)
                .orElseThrow(() -> new MissingConfiguration(warehouseKey));
    }

}
