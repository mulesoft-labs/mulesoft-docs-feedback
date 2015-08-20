package org.mule.docs.rating;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.mule.docs.rating.configuration.RatingConfiguration;
import org.mule.docs.rating.health.ServiceAliveCheck;
import org.mule.docs.rating.resources.RatingResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import java.util.EnumSet;

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
        final RatingResource resource = new RatingResource(configuration.getConfig());
        environment.jersey().register(resource);
        environment.healthChecks().register("storage connection", new ServiceAliveCheck(configuration.getConfig()));
        configureCors(environment);
    }

    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}