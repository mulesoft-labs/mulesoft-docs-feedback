package org.mule.docs.rating.resources;

import org.mule.docs.rating.StorageWriter;
import org.mule.docs.rating.configuration.AwsConfiguration;
import org.mule.docs.rating.core.Rating;
import org.mule.docs.rating.core.RawRating;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;


@Path("/rate")
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {

    private final AwsConfiguration conf;

    public RatingResource(AwsConfiguration conf) {
        this.conf = conf;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveRating(RawRating rawRating, @Context HttpServletRequest request) {
        Rating rating = new Rating(
                rawRating.getId(),
                rawRating.getComment(),
                getIpFromAws(request),
                rawRating.getUrl(),
                new Date(),
                rawRating.getRating()
        );
        writeRating(rating);
        return "Successfully created rating.";
    }

    private String getIpFromAws(HttpServletRequest request) {
        String ip = "0.0.0.0";
        try {
            ip = request.getHeader("X-Forwarded-For").split(",")[0];
        } catch (Exception ignored){}
        return ip;
    }

    private void writeRating(Rating rating) {
        StorageWriter writer = new StorageWriter(this.conf);
        writer.writeRatingToStorage(rating, conf.getTableName());
        writer.closeClient();
    }
}