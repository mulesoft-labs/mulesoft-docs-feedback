package org.mule.docs.rating.configuration;

import io.dropwizard.Configuration;
import org.mule.docs.rating.configuration.AwsConfiguration;

import javax.validation.Valid;

public class RatingConfiguration extends Configuration {
    @Valid
    private AwsConfiguration config;

    public AwsConfiguration getConfig() {
        return config;
    }

    public void setConfiguration(AwsConfiguration config) {
        this.config = config;
    }
}