package org.mule.docs.rating;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mule.docs.rating.resources.RatingResource;

public class RatingApplication extends Application<RatingConfiguration> {
    public static void main(String[] args) throws Exception {
        new RatingApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<RatingConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(RatingConfiguration configuration,
                    Environment environment) {
        final RatingResource resource = new RatingResource();
        environment.jersey().register(resource);
    }

}