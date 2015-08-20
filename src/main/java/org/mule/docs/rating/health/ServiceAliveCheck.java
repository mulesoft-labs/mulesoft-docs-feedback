package org.mule.docs.rating.health;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.codahale.metrics.health.HealthCheck;
import org.mule.docs.rating.StorageWriter;
import org.mule.docs.rating.configuration.AwsConfiguration;

public class ServiceAliveCheck extends HealthCheck {
    private AwsConfiguration config;

    public ServiceAliveCheck(AwsConfiguration config) {
        this.config = config;
    }


    @Override
    protected Result check() throws Exception {
        boolean success = false;
        StorageWriter writer = new StorageWriter(this.config);
        Table table = writer.initializeTable("ratings");
        if(table != null) {
            success = true;
        }

        if (success) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Can't connect to storage service.");
        }
    }
}