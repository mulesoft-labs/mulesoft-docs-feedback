package org.mule.docs.rating.health;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.codahale.metrics.health.HealthCheck;
import org.mule.docs.rating.StorageWriter;

public class ServiceAliveCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        Table table = StorageWriter.initializeTable("ratings");
        if (table != null) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Can't connect to storage service.");
        }
    }
}